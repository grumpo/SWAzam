package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient.ClientWebServiceService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The client console app.
 * Created by grumpo on 12/7/13.
 */
public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getName());

    public static void main(String[] argv) throws IOException {
        log.info("Client has been started an is running now...");

        // test identification
        FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        Fingerprint fingerprint = fingerprintSystem.fingerprint(fakeMp3);
        String fingerPrintIdentificationResult = identify(fingerprint);

        log.info("Fingerprint was identified to be: " + fingerPrintIdentificationResult);
    }

    private static String identify(Fingerprint fingerprint) {
        // this goes in the connector
        ClientWebService peerWebServicePort = getClientWebService();

        String fingerprintJson = serialize(fingerprint);
        log.info("Checking fingerprint: " + fingerprintJson);
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
