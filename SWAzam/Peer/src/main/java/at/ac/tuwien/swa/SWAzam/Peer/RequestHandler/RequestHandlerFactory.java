package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarder;

public interface RequestHandlerFactory {
    RequestHandler create(MP3Identifier mp3Identifier, Peer2ServerConnector peer2ServerConnector, RequestForwarder requestForwarder);
}
