package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.PeerUnreachableException;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.UserInformation;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

import java.util.logging.Logger;

/**
 * Created by markus on 28.12.13.
 */
public class VerifyUserThread implements Runnable {
    private final static Logger log = Logger.getLogger(VerifyUserThread.class.getName());
    Client2PeerConnector con;
    User u;
    User result;
    boolean unreachable;

    public VerifyUserThread(Client2PeerConnector con, User u){
        this.con = con;
        this.u = u;
    }

    @Override
    public void run() {
        UserInformation ui;
        result = null;
        unreachable = false;

        try {
            ui = con.getUserInformation(u.getUsername(), u.getPassword());

            if(ui != null){
                result = new User(u.getUsername(), u.getPassword(), ui.getCredits());
            }
        } catch (PeerUnreachableException e) {
            unreachable = true;
            log.info("Peer unreachable!");
        }
    }

    public User getResult(){
        return result;
    }

    public boolean isUnreachable(){
        return unreachable;
    }
}
