package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.UnableToConnectToServerException;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarder;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RequestHandlerImpl implements RequestHandler {

    private final static Logger log = Logger.getLogger(RequestHandlerImpl.class.getName());

    private final MP3Identifier mp3Identifier;
    private final Peer2ServerConnector peer2ServerConnector;

    @Inject
    private RequestForwarder requestForwarder;

    @Inject
    public RequestHandlerImpl(@Assisted MP3Identifier mp3Identifier, @Assisted Peer2ServerConnector peer2ServerConnector) {
        this.mp3Identifier = mp3Identifier;
        this.peer2ServerConnector = peer2ServerConnector;
    }

    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        if (!mp3Identifier.contains(fingerprint)) {
            log.info("Unable to handle request of user: " + user);
            return requestForwarder.identifyMP3Fingerprint(fingerprint, user, hops);
        }
        log.info("Resolving request of user: " + user);
        FingerprintResult result = new FingerprintResult();
        result.setResult(mp3Identifier.identify(fingerprint));
        result.setHops(new ArrayList<String>());
        return result;
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
