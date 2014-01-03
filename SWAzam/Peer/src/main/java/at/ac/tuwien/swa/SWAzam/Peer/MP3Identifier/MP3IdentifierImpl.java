package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorage;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class MP3IdentifierImpl implements MP3Identifier {

    private final FingerprintStorage fingerprintStorage;

    @Inject
    public MP3IdentifierImpl(@Assisted FingerprintStorage fingerprintStorage) {
        this.fingerprintStorage = fingerprintStorage;
    }

    @Override
    public boolean contains(Fingerprint fingerprint) {
        return fingerprintStorage.contains(fingerprint);
    }

    @Override
    public AudioInformation identify(Fingerprint fingerprint) {
        return fingerprintStorage.getAudioInformationOf(fingerprint);
    }
}
