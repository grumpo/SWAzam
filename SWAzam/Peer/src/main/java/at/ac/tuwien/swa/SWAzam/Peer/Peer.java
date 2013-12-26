package at.ac.tuwien.swa.SWAzam.Peer;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorageFactory;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3IdentifierFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.Peer2ServerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.UnableToConnectToServerException;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandlerFactory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
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

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new PeerModule());
        String msg = "Please pass: \n- the storagePath that contains the MP3 files \n- the port for the web services \n- the Address of the server \nas argument.";

        if (argv.length < 3) {
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
        injector.getInstance(Peer.class).run(storagePath, port, serverAddress);
    }

    public void run(String storagePath, Integer port, String serverAddress) {
        log.info("Peer has been started an is running now...");

        startServices(port, createRequestHandler(storagePath, serverAddress));

        // TODO: remove those or move to ITs
        // test request on self
        testRequestToPeerForwardRequest(port);
        // test request to server
        testRequestToServerUserValidation(serverAddress);
    }

    private void testRequestToPeerForwardRequest(Integer port) {
        Fingerprint fingerprint = generateTestFingerprint();
        FingerprintResult fingerprintResult = null;
        try {
            fingerprintResult = peer2PeerConnectorFactory.create(String.format("http://localhost:%d/PeerWebService?wsdl", port)).
                    identifyMP3Fingerprint(fingerprint, "user", new ArrayList<String>() {
                    });
        } catch (UnableToConnectToPeer e) {
            log.info("Peer is down: " + e.getMessage());
            // TODO: try next
        }
        if (fingerprintResult != null) {
            log.info("Fingerprint was identified to be: " + fingerprintResult.getResult());
        } else {
            log.info("Fingerprint could not be identified.");
        }
    }

    private void testRequestToServerUserValidation(String serverAddress) {
        Peer2ServerConnector peer2ServerConnector = peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION);
        try {
            UserInformation info = peer2ServerConnector.validateUser("Manu", "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
            if (info == null) log.severe("user Manu not found");
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

    private RequestHandler createRequestHandler(String storagePath, String serverAddress) {
        return requestHandlerFactory.create(
                mp3IdentifierFactory.create(fingerprintStorageFactory.createStorageDirectory(storagePath)),
                peer2ServerConnectorFactory.create(serverAddress + PEER_WEB_SERVICE_WSDL_LOCATION));
    }
}
