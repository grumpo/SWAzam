package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;

public interface FingerprintStorage {
    Boolean contains(Fingerprint fingerprint);

    AudioInformation getAudioInformationOf(Fingerprint fingerprint);
}
