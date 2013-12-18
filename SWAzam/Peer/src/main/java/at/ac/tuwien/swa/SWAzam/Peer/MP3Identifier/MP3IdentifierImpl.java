package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorage;
import com.google.inject.Inject;

public class MP3IdentifierImpl implements MP3Identifier {

    @Inject
    private FingerprintStorage fingerprintStorage;

    @Override
    public boolean contains(Fingerprint fingerprint) {
        return fingerprintStorage.contains(fingerprint);
    }

    @Override
    public String identify(Fingerprint fingerprint) {
        return fingerprintStorage.getTitleOf(fingerprint);
    }
}
