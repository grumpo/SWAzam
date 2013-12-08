package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Peer.PeerWebServiceClient.PeerWebServiceService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.logging.Logger;

public class Peer {

    private final static Logger log = Logger.getLogger(Peer.class.getName());

    @Inject
    private ClientWebService clientWebService;
    @Inject
    private PeerWebService peerWebService;

    public static void main(String[] argv) {
        Injector injector = Guice.createInjector(new PeerModule());
        injector.getInstance(Peer.class).run();
    }

    public void run() {
        log.info("Peer has been started an is running now...");

        startServices();

        // test request on self
        PeerWebServiceService peerWebServiceService = new PeerWebServiceService();
        at.ac.tuwien.swa.SWAzam.Peer.PeerWebServiceClient.PeerWebService peerWebServicePort = peerWebServiceService.getPeerWebServicePort();
        String fingerPrintIdentificationResult = peerWebServicePort.identifyMP3Fingerprint("CallingPeer");

        log.info("Fingerprint was identified to be: " + fingerPrintIdentificationResult);
    }

    private void startServices() {
        clientWebService.run(9000);
        peerWebService.main(new String[]{});
    }
}
