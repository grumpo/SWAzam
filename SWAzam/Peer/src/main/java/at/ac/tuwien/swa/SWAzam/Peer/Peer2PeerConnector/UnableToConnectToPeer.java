package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

public class UnableToConnectToPeer extends Exception {
    public UnableToConnectToPeer(String message) {
        super(message);
    }

    public UnableToConnectToPeer(Exception e) {
        super(e);
    }
}
