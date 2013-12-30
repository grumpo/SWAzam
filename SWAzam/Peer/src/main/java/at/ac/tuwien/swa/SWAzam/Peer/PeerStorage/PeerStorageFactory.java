package at.ac.tuwien.swa.SWAzam.Peer.PeerStorage;

public interface PeerStorageFactory {
    PeerStorage create(String dbPath);
}
