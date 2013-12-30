package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.PeerStorage;

public interface RequestForwarderFactory {
    RequestForwarder create(PeerStorage peerStorage);
}
