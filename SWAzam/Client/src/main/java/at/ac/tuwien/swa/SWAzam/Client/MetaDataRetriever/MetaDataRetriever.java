package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerConnector;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.Client2PeerSoapConnector;
import at.ac.tuwien.swa.SWAzam.Client.Entities.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.PeerStorage.PeerStorage;

/**
 * Created by markus on 17.12.13.
 */
public class MetaDataRetriever {
    PeerStorage ps;
    Client2PeerConnector connector;

    public MetaDataRetriever(){
        ps = new PeerStorage();
        connector = new Client2PeerSoapConnector();
    }

    public FingerprintResult getFingerprintResult(Fingerprint fp, User user){
        for(Peer p : ps.getPeers()){
            connector.identifyMP3Fingerprint(p.getIp() + ":" + p.getPort(), fp, user);
        }

        return null;
    }
}
