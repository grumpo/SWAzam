package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor.FingerPrintExtractor;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.LoginFrame;

import at.ac.tuwien.swa.SWAzam.Client.MP3Recorder.MP3Recorder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        MP3Recorder recorder = new MP3Recorder();
        recorder.getMp3FromMic();

        Fingerprint fp1 = FingerPrintExtractor.extractFingerPrint(recorder.getMp3FromFile("/Users/markus/Desktop/tail toddle.mp3"));

        System.out.println("Extracted from File!");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Start Recording: ");
        br.readLine();

        recorder.startRecordingFromMic();

        System.out.println("Stop Recording: ");
        br.readLine();
        recorder.stopRecordingFromMic();

        Fingerprint fp2 = FingerPrintExtractor.extractFingerPrint(recorder.getMp3FromMic());

        System.out.println("Extracted from Mic");
        System.out.println("MATCH: " + fp1.match(fp2));
        System.out.println("MATCH: " + fp2.match(fp1));
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
