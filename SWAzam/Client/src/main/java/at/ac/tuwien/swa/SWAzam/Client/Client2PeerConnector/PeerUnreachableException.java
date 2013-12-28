package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

public class PeerUnreachableException extends Exception {
    public PeerUnreachableException(String message) {
        super(message);
    }

    public PeerUnreachableException(Exception e) {
        super(e);
    }
}
