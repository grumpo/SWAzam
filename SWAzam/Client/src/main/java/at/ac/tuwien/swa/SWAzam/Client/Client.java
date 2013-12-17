package at.ac.tuwien.swa.SWAzam.Client;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor.FingerPrintExtractor;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.LoginFrame;

import at.ac.tuwien.swa.SWAzam.Client.MP3Recorder.IRecorder;
import at.ac.tuwien.swa.SWAzam.Client.MP3Recorder.MP3Recorder;
import at.ac.tuwien.swa.SWAzam.Client.MP3Recorder.MicRecorder;
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

    public static void main(String[] argv) throws IOException {
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(Client.class).run();
    }

    public void run() {
    	Controller c = new Controller();
        c.run();
    	
        //log.info("Client has been started an is running now...");
    }

}
