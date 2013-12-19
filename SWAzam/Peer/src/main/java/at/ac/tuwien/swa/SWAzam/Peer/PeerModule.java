package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorage;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorageInMemory;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3Identifier;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3IdentifierImpl;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarder;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarderImpl;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandlerImpl;
import com.google.inject.AbstractModule;

public class PeerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClientWebService.class).to(ClientWebServiceSoap.class);
        bind(PeerWebService.class).to(PeerWebServiceSoap.class);
        bind(Peer2PeerConnector.class).to(Peer2PeerSoapConnector.class);
        bind(RequestHandler.class).to(RequestHandlerImpl.class);
        bind(MP3Identifier.class).to(MP3IdentifierImpl.class);
        bind(RequestForwarder.class).to(RequestForwarderImpl.class);
        bind(FingerprintStorage.class).to(FingerprintStorageInMemory.class);
    }
}
