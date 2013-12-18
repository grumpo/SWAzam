package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;

import java.util.ArrayList;
import java.util.List;

public class RequestHandlerImpl implements RequestHandler {
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        FingerprintResult result = new FingerprintResult();
        result.setResult("Some good piece of music: " + fingerprint.getShiftDuration());
        result.setHops(new ArrayList<String>());
        return result;
    }
}
