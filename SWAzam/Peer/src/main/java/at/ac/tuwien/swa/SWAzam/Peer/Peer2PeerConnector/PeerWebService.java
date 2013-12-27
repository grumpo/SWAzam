package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;

public interface PeerWebService {
    void run(int port, RequestHandler requestHandler);
    void stop();
}
