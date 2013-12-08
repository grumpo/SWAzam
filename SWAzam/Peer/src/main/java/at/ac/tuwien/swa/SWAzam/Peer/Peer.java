package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.PeerWebServiceClient.*;
import at.ac.tuwien.swa.SWAzam.Peer.PeerWebServiceClient.PeerWebService;

public class Peer {

    public static void main(String[] argv) {
        System.out.println("Peer has been started an is running now...");

        startServices();

        // test request on self
        PeerWebServiceService peerWebServiceService = new PeerWebServiceService();
        PeerWebService peerWebServicePort = peerWebServiceService.getPeerWebServicePort();
        String fingerPrintIdentificationResult = peerWebServicePort.identifyMP3Fingerprint("CallingPeer");

        System.out.println("Fingerprint was identified to be: " + fingerPrintIdentificationResult);

    }

    private static void startServices() {
        ClientWebService.main(new String[]{});
        at.ac.tuwien.swa.SWAzam.Peer.PeerWebService.main(new String[]{});
    }
}
