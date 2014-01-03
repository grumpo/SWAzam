package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.UnableToConnectToServerException;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarder;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarderException;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class RequestHandlerImpl implements RequestHandler {

    private final static Logger log = Logger.getLogger(RequestHandlerImpl.class.getName());
    private static final String PEER_WEB_SERVICE_WSDL_LOCATION = "/PeerWebService?wsdl";

    private final MP3Identifier mp3Identifier;
    private final Peer2ServerConnector peer2ServerConnector;
    private ConcurrentHashMap<UUID, ResultListener> inProgress = new ConcurrentHashMap<>();

    private RequestForwarder requestForwarder;
    private final String username;
    private final String password;
    @Inject
    private Peer2PeerConnectorFactory peer2PeerConnectorFactory;

    @Inject
    public RequestHandlerImpl(
            @Assisted MP3Identifier mp3Identifier, @Assisted Peer2ServerConnector peer2ServerConnector, @Assisted RequestForwarder requestForwarder,
            @Assisted("username") String username, @Assisted("password") String password) {
        this.mp3Identifier = mp3Identifier;
        this.peer2ServerConnector = peer2ServerConnector;
        this.requestForwarder = requestForwarder;
        this.username = username;
        this.password = password;
    }

    public void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops, UUID uuid, ResultListener resultListener) {
        if (!mp3Identifier.contains(fingerprint)) {
            log.info("Unable to handle request of user: " + user);
            forwardRequest(fingerprint, user, hops, uuid, resultListener);
            return;
        }
        log.info("Resolving request of user: " + user);
        sendResult(new FingerprintResult(mp3Identifier.identify(fingerprint), hops, uuid), resultListener);
    }

    /**
     * resultListener != null: Request was issued by client. In this case store the resultListener until #identificationResult is called.
     * resultListener == null: Request was issued by peer. Just forward unless an exception occurs, in this case report.
     */
    private void forwardRequest(Fingerprint fingerprint, String user, List<String> hops, UUID uuid, ResultListener resultListener) {
        try {
            requestForwarder.identifyMP3Fingerprint(fingerprint, user, hops, uuid);
            if (resultListener != null) {
                inProgress.put(uuid, resultListener);
            }
        } catch (RequestForwarderException e) {
            log.severe(e.getMessage());
            sendResult(new FingerprintResult(null, hops, uuid), resultListener);
        }
    }

    /**
     * Either return result to the client or send to first peer.
     * @see #forwardRequest
     */
    private void sendResult(FingerprintResult result, ResultListener resultListener) {
        if (resultListener != null) {
            resultListener.setResult(result);
            return;
        }
        Peer2PeerConnector peer2PeerConnector = peer2PeerConnectorFactory.create(result.getHops().get(0) + PEER_WEB_SERVICE_WSDL_LOCATION);
        try {
            peer2PeerConnector.identificationResolved(result);
        } catch (UnableToConnectToPeer unableToConnectToPeer) {
            log.severe("Unable to reach initial peer! This is fatal. " + result.getHops().get(0)); // TODO: how to handle this?
        }
    }

    @Override
    public void identificationResult(FingerprintResult fingerprintResult) {
        ResultListener resultListener = inProgress.get(fingerprintResult.getRequestID());
        resultListener.setResult(fingerprintResult);
        try {
            peer2ServerConnector.resolvedIdentification(username, password);
            peer2ServerConnector.requestedIdentification(resultListener.getUser(), resultListener.getPassword());
        } catch (UnableToConnectToServerException e) {
            log.severe("Unable to book credits: " + e.getMessage());
        }
    }

    @Override
    public UserInformation getUserInformation(String user, String password) {
        try {
            return peer2ServerConnector.validateUser(user, password);
        } catch (UnableToConnectToServerException e) {
            log.severe("Unable to contact server: " + e.getMessage());
            // not nice, but requested by peer
            return null;
        }
    }
}
