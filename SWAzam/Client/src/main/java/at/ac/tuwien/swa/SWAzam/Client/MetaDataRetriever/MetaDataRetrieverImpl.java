package at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.*;
import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.PeerStorage.PeerStorage;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class MetaDataRetrieverImpl implements MetaDataRetriever {
    private final static Logger log = Logger.getLogger(MetaDataRetrieverImpl.class.getName());
    private final Client2PeerConnector connector;

    PeerStorage ps;

    @Inject
    public MetaDataRetrieverImpl(@Assisted Connection con, Client2PeerConnectorFactory connectorFactory){
        ps = new PeerStorage(con);
        connector = connectorFactory.create("http://localhost:9000/ClientWebService?wsdl");
    }

    @Override
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

    @Override
    public int getRegisteredPeersAmount(){
        return ps.getPeers().size();
    }

    @Override
    public User verifyUser(User u) {
        User result = null;
        UserInformation ui = null;
        try {
            ui = connector.getUserInformation(u.getUsername(), u.getPassword());
        } catch (UnableToConnectToPeer unableToConnectToPeer) {
            // TODO: try all peers
        }

        if(ui != null){
            result = new User(u.getUsername(), u.getPassword(), ui.getCredits());
        }

        return result;
    }
}
