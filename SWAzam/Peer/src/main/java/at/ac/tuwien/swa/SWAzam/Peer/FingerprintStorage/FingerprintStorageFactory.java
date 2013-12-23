package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

/**
 * Created by grumpo on 12/23/13.
 */
public class FingerprintStorageFactory {

    public FingerprintStorage CreateStorageDirectory(String direcoryPath){
        return new FingerprintStorageDirectory(direcoryPath);
    }
}
