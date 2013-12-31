package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;

import java.util.List;
import java.util.UUID;

public interface RequestHandler {
    void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops, UUID uuid, ResultListener resultListener);
    void identificationResult(FingerprintResult result);
    UserInformation getUserInformation(String user, String password);
}
