package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebServiceSoap;
import com.google.inject.AbstractModule;

public class PeerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClientWebService.class).to(ClientWebServiceSoap.class);
        bind(PeerWebService.class).to(PeerWebServiceSoap.class);
        bind(Peer2PeerConnector.class).to(Peer2PeerSoapConnector.class);
    }
}
