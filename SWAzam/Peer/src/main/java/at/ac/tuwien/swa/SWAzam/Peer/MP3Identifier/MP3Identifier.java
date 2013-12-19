package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface MP3Identifier {
    boolean contains(Fingerprint fingerprint);

    String identify(Fingerprint fingerprint);
}
