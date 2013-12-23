package at.ac.tuwien.swa.SWAzam.Peer;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage.FingerprintStorageFactory;
import at.ac.tuwien.swa.SWAzam.Peer.MP3Identifier.MP3IdentifierFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnectorFactory;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.UnableToConnectToPeer;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandlerFactory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer {

    private final static Logger log = Logger.getLogger(Peer.class.getName());

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

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new PeerModule());
        String msg = "Please pass the storagePath that contains the MP3 files and the port for the web services as argument.";

        if (argv.length < 2) {
            log.log(Level.SEVERE, msg);
            return;
        }
        String storagePath = argv[0];
        if (storagePath.isEmpty()) {
            log.log(Level.SEVERE, "Storage path is missing! " + msg);
            return;
        }
        Integer port;
        try {
            port = Integer.valueOf(argv[1]);
        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Port is missing or malformed! " + msg);
            return;
        }
        injector.getInstance(Peer.class).run(storagePath, port);
    }

    public void run(String storagePath, Integer port) {
        log.info("Peer has been started an is running now...");

        startServices(port, createRequestHandler(storagePath));

        // test request on self
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

        log.info("Fingerprint was identified to be: " + fingerprintResult.getResult());
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

    private RequestHandler createRequestHandler(String storagePath) {
        return requestHandlerFactory.create(
                mp3IdentifierFactory.create(fingerprintStorageFactory.createStorageDirectory(storagePath)));
    }
}
