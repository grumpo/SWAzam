package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;

import java.util.List;

public interface RequestHandler {
    FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops);
    UserInformation getUserInformation(String user, String password);
}
