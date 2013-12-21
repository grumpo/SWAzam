package at.ac.tuwien.swa.SWAzam.Client;

import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.UserInformation;
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
    private Client2PeerConnector connector;

    public static void main(String[] argv) throws IOException {
        Injector injector = Guice.createInjector(new ClientModule());
        injector.getInstance(Client.class).run();
    }

    public void run() {
    	Controller c = new Controller();
        // TODO: test request
        UserInformation userInformation = connector.getUserInformation("user1", "passwd");
    }

}
