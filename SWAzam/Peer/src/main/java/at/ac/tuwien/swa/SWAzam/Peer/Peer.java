package at.ac.tuwien.swa.SWAzam.Peer;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.Peer2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.PeerWebService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class Peer {

    private final static Logger log = Logger.getLogger(Peer.class.getName());

    @Inject
    private ClientWebService clientWebService;
    @Inject
    private PeerWebService peerWebService;
    @Inject
    private Peer2PeerConnector peer2PeerConnector;

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new PeerModule());
        injector.getInstance(Peer.class).run();
    }

    public void run() {
        log.info("Peer has been started an is running now...");

        startServices();

        // test request on self
        Fingerprint fingerprint = generateTestFingerprint();
        FingerprintResult fingerprintResult = peer2PeerConnector.identifyMP3Fingerprint(fingerprint, "user", new ArrayList<String>() {});

        log.info("Fingerprint was identified to be: " + fingerprintResult.getResult());
    }

    private Fingerprint generateTestFingerprint() {
        FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        return fingerprintSystem.fingerprint(fakeMp3);
    }

    private void startServices() {
        clientWebService.run(9000);
        peerWebService.run(9000);
    }
}
