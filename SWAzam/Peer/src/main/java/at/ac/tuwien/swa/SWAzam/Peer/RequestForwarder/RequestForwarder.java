package at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import java.util.List;
import java.util.UUID;

public interface RequestForwarder {
    void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops, UUID uuid) throws RequestForwarderException;
}
