package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebServiceService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

/**
 * Created by grumpo on 12/7/13.
 */
public class Client {
    public static void main(String[] argv) throws IOException {
        System.out.println("Client has been started an is runing now...");

        // test identification, needs "blah.mp3" in the project root dir.
        FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        Fingerprint fingerprint = fingerprintSystem.fingerprint(fakeMp3);
        String fingerPrintIdentificationResult = identify(fingerprint);

        System.out.println("Fingerprint was identified to be: " + fingerPrintIdentificationResult);
    }

    private static String identify(Fingerprint fingerprint) {
        // this goes in the connector
        ClientWebService peerWebServicePort = getClientWebService();

        String fingerprintJson = serialize(fingerprint);
        System.out.println("Checking fingerprint: " + fingerprintJson);
        return peerWebServicePort.identifyMP3Fingerprint(
                fingerprintJson);
    }

    private static String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

    private static ClientWebService getClientWebService() {
        return new ClientWebServiceService().getClientWebServicePort();
    }
}
