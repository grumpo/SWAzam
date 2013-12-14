package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import java.util.List;

public interface Peer2PeerConnector {
    FingerprintResult identifyMP3Fingerprint(String fingerprintJson, String user, List<String> hops);
}
