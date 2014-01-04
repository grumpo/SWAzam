package at.ac.tuwien.swa.SWAzam.Peer.Common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public static String encrypt(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plain.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        }
    }
}
