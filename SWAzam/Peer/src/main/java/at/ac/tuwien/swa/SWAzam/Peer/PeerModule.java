package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebServiceSoap;
import com.google.inject.AbstractModule;

public class PeerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClientWebService.class).to(ClientWebServiceSoap.class);
    }
}
