package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

public interface MetaDataRetriever {
    FingerprintResult getFingerprintResult(Fingerprint fp, User user);

    int getRegisteredPeersAmount();

    User verifyUser(User u);
}
