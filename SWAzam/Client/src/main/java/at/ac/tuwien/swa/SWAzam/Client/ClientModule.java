package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;
import com.google.inject.AbstractModule;

public class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Client2PeerConnector.class).to(Client2PeerSoapConnector.class);
    }
}
