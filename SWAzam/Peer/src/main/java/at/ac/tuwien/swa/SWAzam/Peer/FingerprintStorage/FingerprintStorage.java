package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface FingerprintStorage {
    boolean contains(Fingerprint fingerprint);

    String getTitleOf(Fingerprint fingerprint);
}
