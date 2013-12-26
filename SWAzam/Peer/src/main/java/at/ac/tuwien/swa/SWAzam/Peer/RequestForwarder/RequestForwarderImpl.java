package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.Peer;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.PeerStorage;
import com.google.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class RequestForwarderImpl implements RequestForwarder {

    private final static Logger log = Logger.getLogger(RequestForwarderImpl.class.getName());
    public static final String PEER_WEB_SERVICE_WSDL_LOCATION = "/PeerWebService?wsdl";

    @Inject
    private PeerStorage peerStorage;

    @Inject
    private Peer2PeerConnectorFactory peer2PeerConnectorFactory;


    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        Set<Peer> peers = peerStorage.getPeers();

        if (hops.size() > 3) { // TODO: do not hardcode this
            // TODO: throw exception?
            log.info("Tried too many hops, giving up...");
            return null;
        }

        for(Peer peer : peers)
        {
            if (hops.contains(peer.getUrl())) {
                log.info("Skipping peer, already tried: " + peer.getUrl());
                continue;
            }
            try {
                String peerUrl = peer.getUrl();
                return peer2PeerConnectorFactory.create(peerUrl + PEER_WEB_SERVICE_WSDL_LOCATION).identifyMP3Fingerprint(fingerprint, user, hops);
            } catch (UnableToConnectToPeer e) {
                log.info("Peer seems down: " + e.getMessage());
                peer.failure();
            }
        }
        //TODO: really return null or throw an exception to handle the error?
        log.info("Tried all known Peers, none could resolve the fingerprint...");
        return null;
    }

}
