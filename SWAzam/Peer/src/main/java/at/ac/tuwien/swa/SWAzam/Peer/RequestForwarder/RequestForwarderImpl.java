package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnector;
import com.google.inject.Inject;

import java.util.List;

public class RequestForwarderImpl implements RequestForwarder {

    @Inject
    Peer2PeerConnector peer2PeerConnector; // TODO: get list from peerStorage

    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        return peer2PeerConnector.identifyMP3Fingerprint(fingerprint, user, hops);
    }

}
