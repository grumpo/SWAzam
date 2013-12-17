package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.File;
import java.io.IOException;
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

        c.loadMp3(new File("/Users/markus/Desktop/test.mp3"));
    	
        //log.info("Client has been started an is running now...");
    }

}
