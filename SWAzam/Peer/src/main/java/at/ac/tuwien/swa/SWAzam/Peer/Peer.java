package at.ac.tuwien.swa.SWAzam.Peer;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorageFactory;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3IdentifierFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
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
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
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
                "as argument.";

        if (argv.length < 4) {
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

        final Peer peer = injector.getInstance(Peer.class);
        try {
            peer.run(storagePath, port, serverAddress, dbPath);
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

    public void stop() {
        stopServices();
    }

    private void stopServices() {
        clientWebService.stop();
        peerWebService.stop();
    }

    public void run(String storagePath, Integer port, String serverAddress, String dbPath) {
        log.info("Starting peer...");

        startServices(port, createRequestHandler(storagePath, serverAddress, dbPath));

        // TODO: remove those or move to ITs
        // test request on self
        //testRequestToPeerForwardRequest(port);
        // test request to server
        testRequestToServerUserValidation(serverAddress);
    }

    private void testRequestToPeerForwardRequest(Integer port) {
        Fingerprint fingerprint = generateTestFingerprint();
        try {
            peer2PeerConnectorFactory.create(String.format("http://localhost:%d/PeerWebService?wsdl", port)).
                    identifyMP3Fingerprint(fingerprint, "user", new ArrayList<String>() {}, UUID.randomUUID());
        } catch (UnableToConnectToPeer e) {
            log.info("Peer is down: " + e.getMessage());
        }
    }

    private void testRequestToServerUserValidation(String serverAddress) {
        Peer2ServerConnector peer2ServerConnector = peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION);
        try {
            UserInformation info = peer2ServerConnector.validateUser("John", "fd53ef835b15485572a6e82cf470dcb41fd218ae5751ab7531c956a2a6bcd3c7");
            if (info == null) log.severe("user John not found");
            else log.info(info.getUsername() + ": " + info.getCredits());
        } catch (UnableToConnectToServerException e) {
            log.severe(e.getMessage());
        }
    }

    private Fingerprint generateTestFingerprint() {
        FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        return fingerprintSystem.fingerprint(fakeMp3);
    }

    private void startServices(Integer port, RequestHandler requestHandler) {
        clientWebService.run(port, requestHandler);
        peerWebService.run(port, requestHandler);
    }

    private RequestHandler createRequestHandler(String storagePath, String serverAddress, String dbPath) {
        return requestHandlerFactory.create(
                mp3IdentifierFactory.create(fingerprintStorageFactory.createStorageDirectory(storagePath)),
                peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION),
                requestForwarderFactory.create(peerStorageFactory.create(dbPath)));
    }
}
