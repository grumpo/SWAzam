package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import java.util.HashMap;
import java.util.Map;

public class FingerprintStorageInMemory implements FingerprintStorage {
    private Map<Fingerprint, String> fingerprints = new HashMap<>();

    @Override
    public Boolean contains(Fingerprint fingerprint) {
        // TODO: return fingerprints.containsKey(fingerprint);
        return true;
    }

    @Override
    public String getTitleOf(Fingerprint fingerprint) {
        return "Some good piece of music: " + fingerprint.getShiftDuration();
        // TODO: return fingerprints.get(fingerprint);
    }
}
