package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Client.Entities.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.PeerStorage.PeerStorage;

import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class MetaDataRetriever {
    private final static Logger log = Logger.getLogger(MetaDataRetriever.class.getName());

    PeerStorage ps;
    Client2PeerConnector connector;

    public MetaDataRetriever(){
        ps = new PeerStorage();
        connector = new Client2PeerSoapConnector();
    }

    public FingerprintResult getFingerprintResult(Fingerprint fp, User user){
        log.info("Starting to contact peers and ask for FingerprintResults!");

        ps.addPeer(new Peer("http://ws.cdyne.com/"));
        user = new User("Markus", "Test");

        for(Peer p : ps.getPeers()){
            log.info("Connecting to peer");
            connector.identifyMP3Fingerprint(p.getUrl(), fp, user);
        }

        return null;
    }

    public int getRegisteredPeersAmount(){
        return ps.getPeers().size();
    }
}
