package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.Controller.ControllerFactory;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetriever;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetrieverFactory;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetrieverImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(Client2PeerConnector.class, Client2PeerSoapConnector.class)
                .build(Client2PeerConnectorFactory.class));
        install(new FactoryModuleBuilder()
                .implement(MetaDataRetriever.class, MetaDataRetrieverImpl.class)
                .build(MetaDataRetrieverFactory.class));
        install(new FactoryModuleBuilder()
                .implement(Controller.class, Controller.class)
                .build(ControllerFactory.class));
    }
}
