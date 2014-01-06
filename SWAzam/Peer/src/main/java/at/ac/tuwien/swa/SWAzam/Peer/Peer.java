package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Common.Encryption;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorageFactory;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3IdentifierFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.UnableToConnectToServerException;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.PeerStorageFactory;
import at.ac.tuwien.swa.SWAzam.Peer.PeerStorage.StorageException;
import at.ac.tuwien.swa.SWAzam.Peer.RequestForwarder.RequestForwarderFactory;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandlerFactory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer {

    private final static Logger log = Logger.getLogger(Peer.class.getName());
    public static final String PEER_WEB_SERVICE_WSDL_LOCATION = "/PeerWebService?wsdl";

    @Inject
    private ClientWebService clientWebService;
    @Inject
    private PeerWebService peerWebService;
    @Inject
    private Peer2PeerConnectorFactory peer2PeerConnectorFactory;
    @Inject
    private RequestHandlerFactory requestHandlerFactory;
    @Inject
    private MP3IdentifierFactory mp3IdentifierFactory;
    @Inject
    private FingerprintStorageFactory fingerprintStorageFactory;
    @Inject
    private Peer2ServerConnectorFactory peer2ServerConnectorFactory;
    @Inject
    private PeerStorageFactory peerStorageFactory;
    @Inject
    private RequestForwarderFactory requestForwarderFactory;

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new PeerModule());
        String msg = "Usage: Please pass \n" +
                "- the path to the dir that contains the MP3 files (storagePath) \n" +
                "- the port for the web services \n" +
                "- the address of the server \n" +
                "- the path to the HSQLDB (dbPath) \n" +
                "- the username of the peer owner \n" +
                "- the password of the peer owner \n" +
                "as argument.";

        if (argv.length < 6) {
            log.log(Level.SEVERE, msg);
            return;
        }
        String storagePath = argv[0];
        if (storagePath.isEmpty()) {
            log.log(Level.SEVERE, "Storage path is missing! " + msg);
            return;
        }
        if (!new File(storagePath).exists()) {
            log.log(Level.SEVERE, "Storage path does not exist! " + storagePath + "\n" + msg);
            return;
        }
        Integer port;
        try {
            port = Integer.valueOf(argv[1]);
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Port is missing or malformed! " + msg);
            return;
        }
        String serverAddress = argv[2];
        String dbPath = argv[3];
        String username = argv[4];
        String password = argv[5];

        final Peer peer = injector.getInstance(Peer.class);
        try {
            peer.validate(serverAddress, username, password);
        } catch (InvalidArgumentException | UnableToConnectToServerException e) {
            log.severe(e.getMessage() + "\n" + msg);
            return;
        }
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        try {
            peer.run(storagePath, port, serverAddress, dbPath, username, password);
            log.info("Peer is up, press <enter> to quit.");
        } catch (StorageException e) {
            log.severe("Error reading peer database! Please pass a dbPath that represents a valid peer database! \n\n" + msg);
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    System.in.read();
                    log.info("Stopping peer...");
                    peer.stop();
                } catch (IOException e) {
                    log.severe("Unable to read key:" + e.getMessage());
                }
            }
        }.run();
    }

    private void validate(String serverAddress, String username, String password) throws UnableToConnectToServerException, InvalidArgumentException {
        Peer2ServerConnector peer2ServerConnector = peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION);
        UserInformation userInformation = peer2ServerConnector.validateUser(username, Encryption.encrypt(password));
        if (userInformation == null) throw new InvalidArgumentException("Invalid username or password for user: " + username);
    }

    public void stop() {
        stopServices();
    }

    private void stopServices() {
        clientWebService.stop();
        peerWebService.stop();
    }

    public void run(String storagePath, Integer port, String serverAddress, String dbPath, String username, String password) {
        log.info("Starting peer...");

        startServices(port, createRequestHandler(storagePath, serverAddress, dbPath, username, password));
    }

    private void startServices(Integer port, RequestHandler requestHandler) {
        clientWebService.run(port, requestHandler);
        peerWebService.run(port, requestHandler);
    }

    private RequestHandler createRequestHandler(String storagePath, String serverAddress, String dbPath, String username, String password) {
        return requestHandlerFactory.create(
                mp3IdentifierFactory.create(fingerprintStorageFactory.createStorageDirectory(storagePath)),
                peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION),
                requestForwarderFactory.create(peerStorageFactory.create(dbPath)),
                username, password
                );
    }
}
