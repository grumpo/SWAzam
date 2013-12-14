package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import java.util.List;

public class Peer2PeerSoapConnector implements Peer2PeerConnector {
    public FingerprintResult identifyMP3Fingerprint(String fingerprintJson, String user, List<String> hops) {
        return new FingerprintResult();
    }
}
