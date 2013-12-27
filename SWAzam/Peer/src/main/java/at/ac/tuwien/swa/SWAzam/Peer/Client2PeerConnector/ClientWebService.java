package at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector;

import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;

public interface ClientWebService {
    void run(int port, RequestHandler requestHandler);
    void stop();
}
