package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;


import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

public interface Client2PeerConnector {
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password);
    public FingerprintResult identifyMP3Fingerprint(String url, Fingerprint fingerprint, User user);
}
