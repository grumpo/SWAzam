package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The client console app.
 * Created by grumpo on 12/7/13.
 */
public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getName());

    @Inject
    private Controller controller;

    public static void main(String[] argv) throws IOException {
        String dbpath = "";

        Injector injector = Guice.createInjector(new ClientModule());

        if(argv.length == 0){
            dbpath = "jdbc:hsqldb:file:" + Client.class.getResource("/Database").getFile() + "/localdb";
        }
        else if(argv.length == 1){
            dbpath = "jdbc:hsqldb:file:" + argv[0];
        }
        else{
            System.exit(1);
        }

        //TODO: PASS DBPATH TO CLIENT
        log.info("Database-String: " + dbpath);
        injector.getInstance(Client.class).run();
    }

    public void run() {
    	controller.showComponents();
    }

}
