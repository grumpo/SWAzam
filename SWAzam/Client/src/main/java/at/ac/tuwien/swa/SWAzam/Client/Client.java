package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebServiceService;

/**
 * Created by grumpo on 12/7/13.
 */
public class Client {
    public static void main(String[] argv) {
        System.out.println("Client has been started an is runing now...");

        ClientWebServiceService clientWebServiceService = new ClientWebServiceService();
        ClientWebService peerWebServicePort = clientWebServiceService.getClientWebServicePort();
        String fingerPrintIdentificationResult = peerWebServicePort.identifyMP3Fingerprint("CallingClient");

        System.out.println("Fingerprint was identified to be: " + fingerPrintIdentificationResult);

    }
}
