package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.gui.LoginFrame;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The client console app.
 * Created by grumpo on 12/7/13.
 */
public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getName());

    @Inject
    private Client2PeerConnector client2PeerConnector;

    private String user = "user";
    private String pass = "pass";

    public static void main(String[] argv) throws IOException {
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(Client.class).run();
    }

    public void run() {
    	
    	new LoginFrame();
    	
    	
        log.info("Client has been started an is running now...");

        // test identification
        /*FingerprintSystem fingerprintSystem = new FingerprintSystem(44f);
        byte[] fakeMp3 = new byte[23000];
        new Random().nextBytes(fakeMp3);
        Fingerprint fingerprint = fingerprintSystem.fingerprint(fakeMp3);
        FingerprintResult fingerPrintIdentificationResult = identify(fingerprint);
        log.info("Fingerprint was identified to be: " + fingerPrintIdentificationResult.getResult());*/
    }

    private FingerprintResult identify(Fingerprint fingerprint) {
        return client2PeerConnector.identifyMP3Fingerprint(fingerprint, user, pass);
    }

}
