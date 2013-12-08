package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The client console app.
 * Created by grumpo on 12/7/13.
 */
public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getName());

    private String user = "user";
    private String pass = "pass";

    public static void main(String[] argv) throws IOException {
        new Client().run();
    }

    private void run() {
        log.info("Client has been started an is running now...");

        // test identification
        FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        Fingerprint fingerprint = fingerprintSystem.fingerprint(fakeMp3);
        String fingerPrintIdentificationResult = identify(fingerprint);
        log.info("Fingerprint was identified to be: " + fingerPrintIdentificationResult);
    }

    private String identify(Fingerprint fingerprint) {
        return new Client2PeerSoapConnector().identifyMP3Fingerprint(fingerprint, user, pass);
    }

}
