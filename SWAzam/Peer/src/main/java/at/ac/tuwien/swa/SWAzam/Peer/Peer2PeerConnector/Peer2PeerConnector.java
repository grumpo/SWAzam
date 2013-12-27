package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;

import java.util.List;

public interface Peer2PeerConnector {
    void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) throws UnableToConnectToPeer;
    void identificationResolved(FingerprintResult fingerprintResult) throws UnableToConnectToPeer;
}
