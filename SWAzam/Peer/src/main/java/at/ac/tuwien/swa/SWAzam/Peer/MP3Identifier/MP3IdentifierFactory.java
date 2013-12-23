package at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier;

import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorage;

public interface MP3IdentifierFactory {
    MP3Identifier create(FingerprintStorage fingerprintStorage);
}
