package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;

import java.util.List;

public interface RequestHandler {
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops);
}
