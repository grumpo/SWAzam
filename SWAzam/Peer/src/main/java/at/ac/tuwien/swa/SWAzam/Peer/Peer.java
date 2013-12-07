package at.ac.tuwien.swa.SWAzam.Peer;

import at.ac.tuwien.swa.SWAzam.Peer.PerrWebServiceClient.*;
import at.ac.tuwien.swa.SWAzam.Peer.PerrWebServiceClient.PeerWebService;

public class Peer {

    public static void main(String[] argv) {
        System.out.println("Peer has been started an is runing now...");

        PeerWebServiceService peerWebServiceService = new PeerWebServiceService();
        PeerWebService peerWebServicePort = peerWebServiceService.getPeerWebServicePort();
        String fingerPrintIdentificationResult = peerWebServicePort.identifyMP3Fingerprint("CallingPeer");

        System.out.println("Fingerpring was identified to be: " + fingerPrintIdentificationResult);

    }
}
