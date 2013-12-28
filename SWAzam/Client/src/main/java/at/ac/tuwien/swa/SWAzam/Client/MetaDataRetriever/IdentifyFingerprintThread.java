package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.PeerUnreachableException;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;

import java.util.logging.Logger;

/**
 * Created by markus on 28.12.13.
 */
public class IdentifyFingerprintThread implements Runnable{
    private final static Logger log = Logger.getLogger(IdentifyFingerprintThread.class.getName());
    Client2PeerConnector con;
    Fingerprint fp;
    User u;
    FingerprintResult fpr;
    boolean unreachable;

    public IdentifyFingerprintThread(Client2PeerConnector con, Fingerprint fp, User u){
        this.con = con;
        this.fp = fp;
        this.u = u;
    }


    @Override
    public void run() {
        fpr = null;
        unreachable = false;

        try {
            fpr = con.identifyMP3Fingerprint(fp, u.getUsername(), u.getPassword());
        } catch (PeerUnreachableException e) {
            unreachable = true;
            log.info("Peer unreachable!");
        }
    }

    public FingerprintResult getResult(){
        return fpr;
    }

    public boolean isUnreachable(){
        return unreachable;
    }
}
