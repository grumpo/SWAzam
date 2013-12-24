package at.ac.tuwien.swa.SWAzam.Server;



import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManager;
import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManagerFactory;
import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManagerImpl;
import at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidator;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidatorFactory;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidatorImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PeerWebService.class).to(PeerWebServiceSoap.class);
        bind(UserDataStorage.class).to(UserDataStorageImpl.class);
        
        install(new FactoryModuleBuilder()
        .implement(PermissionValidator.class, PermissionValidatorImpl.class)
        .build(PermissionValidatorFactory.class));
        
        install(new FactoryModuleBuilder()
        .implement(DebitManager.class, DebitManagerImpl.class)
        .build(DebitManagerFactory.class));
    }
}
