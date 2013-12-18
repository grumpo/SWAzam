package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class MP3IdentifierImpl implements MP3Identifier {
    @Override
    public boolean contains(Fingerprint fingerprint) {
        return true;
    }

    @Override
    public String identify(Fingerprint fingerprint) {
        return "Some good piece of music: " + fingerprint.getShiftDuration();
    }
}
