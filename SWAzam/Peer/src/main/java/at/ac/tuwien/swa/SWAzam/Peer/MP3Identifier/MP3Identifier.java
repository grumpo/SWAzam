package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;

public interface MP3Identifier {
    boolean contains(Fingerprint fingerprint);
    AudioInformation identify(Fingerprint fingerprint);
}
