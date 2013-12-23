package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

/**
 * Created by grumpo on 12/23/13.
 */
public interface FingerprintStorageFactory {
    FingerprintStorage createStorageDirectory(String directoryPath);
}
