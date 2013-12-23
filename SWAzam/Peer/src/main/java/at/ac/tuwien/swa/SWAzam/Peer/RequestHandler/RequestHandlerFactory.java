package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;

public interface RequestHandlerFactory {
    RequestHandler create(MP3Identifier mp3Identifier);
}
