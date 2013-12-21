package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.UserInformation;
import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.PeerStorage.PeerStorage;

import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class MetaDataRetriever {
    private final static Logger log = Logger.getLogger(MetaDataRetriever.class.getName());

    PeerStorage ps;
    Client2PeerConnector connector;

    public MetaDataRetriever(Connection con){
        ps = new PeerStorage(con);
        connector = new Client2PeerSoapConnector();
    }

    public FingerprintResult getFingerprintResult(Fingerprint fp, User user){
        log.info("Starting to contact peers and ask for FingerprintResults!");

        //TODO: PeerManagement, WebServiceCall
        for(Peer p : ps.getPeers()){
            log.info("Connecting to peer " + p);
            //connector.identifyMP3Fingerprint(p.getUrl(), fp, user);

            //Connect to first Peer, if no response before timeout -> failure, try next peer
            //if response: add new peers to Database or update failure

            //FingerprintResult fpr = connector.identifyMP3Fingerprint(fp, user.getUsername(), user.getPassword());

            //ps.updatePeers(fpr.getHops());

            //return fpr;
        }

        return null;
    }

    public int getRegisteredPeersAmount(){
        return ps.getPeers().size();
    }

    public User verifyUser(User u) {
        User result = null;
        UserInformation ui = connector.getUserInformation(u.getUsername(), u.getPassword());

        if(ui != null){
            result = new User(u.getUsername(), u.getPassword(), ui.getCredits());
        }

        return result;
    }
}
