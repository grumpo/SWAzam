package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import com.google.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

public class RequestForwarderImpl implements RequestForwarder {

    private final static Logger log = Logger.getLogger(RequestForwarderImpl.class.getName());

    @Inject
    // TODO: get list from peerStorage
    private Peer2PeerConnectorFactory peer2PeerConnectorFactory;


    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        try {
            return peer2PeerConnectorFactory.create("http://localhost:9000/PeerWebService?wsdl").identifyMP3Fingerprint(fingerprint, user, hops);
        } catch (UnableToConnectToPeer e) {
            log.info("Peer seems down: " + e.getMessage());
            // TODO: try next
            return null;
        }
    }

}
