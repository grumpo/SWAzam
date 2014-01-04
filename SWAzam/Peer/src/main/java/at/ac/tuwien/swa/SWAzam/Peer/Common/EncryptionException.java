package at.ac.tuwien.swa.SWAzam.Peer.Common;

import java.security.NoSuchAlgorithmException;

public class EncryptionException extends RuntimeException {
    public EncryptionException(NoSuchAlgorithmException e) {
        super(e);
    }
}
