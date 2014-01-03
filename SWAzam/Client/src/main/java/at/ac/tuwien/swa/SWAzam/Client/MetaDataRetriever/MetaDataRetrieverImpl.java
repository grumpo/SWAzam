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
    private final Client2PeerConnectorFactory conFac;
    final static int VERIFICATIONTIMEOUT = 2000;
    final static int FINGERPRINTTIMEOUT = 100000;

    PeerStorage ps;

    @Inject
    public MetaDataRetrieverImpl(@Assisted Connection con, Client2PeerConnectorFactory connectorFactory){
        ps = new PeerStorage(con);
        conFac = connectorFactory;
    }

    @Override
    public FingerprintResult getFingerprintResult(Fingerprint fp, User user){
        FingerprintResult fpr;
        Client2PeerConnector con;
        IdentifyFingerprintThread ift;
        Thread t;
        log.info("Starting to contact peers and ask for FingerprintResults!");

        //TODO: PeerManagement, WebServiceCall
        for(Peer p : ps.getPeers()){
            ift = new IdentifyFingerprintThread(conFac.create(p.getClientWebServiceUrl()), fp, user);

            t = new Thread(ift);
            t.start();

            try {
                t.join(FINGERPRINTTIMEOUT);
            } catch (InterruptedException e) {
                log.info("Interrupted exception while waiting on a thread!");
            }

            if(!t.isAlive()){
                if(ift.isUnreachable()){
                    ps.failurePeer(p);
                }
                else{
                    log.info("NO TIMEOUT: Fetching result from IdentifyFingerprintThread!!!");
                    fpr = ift.getResult();

                    if(fpr != null){
                        ps.updatePeers(fpr.getHops());
                    }

                    return fpr;
                }
            }
            else{
                log.info("TIMEOUT while waiting for a response!!!");
            }
        }

        return null;
    }

    @Override
    public int getRegisteredPeersAmount(){
        return ps.getPeers().size();
    }

    @Override
    public User verifyUser(User u) {
        Client2PeerConnector con;
        User result = null;
        UserInformation ui = null;
        Thread t;
        VerifyUserThread vut;

        //TODO: NO TIMEOUT NECESSARY: ONLY 1 PEER AND SERVER INVOLVED

        for(Peer p : ps.getPeers()){
            vut = new VerifyUserThread(conFac.create(p.getClientWebServiceUrl()), u);

            t = new Thread(vut);
            t.start();

            try {
                t.join(VERIFICATIONTIMEOUT);
            } catch (InterruptedException e) {
                log.info("Interrupted exception while waiting on a thread!");
            }

            if(!t.isAlive()){
                if(vut.isUnreachable()){
                    ps.failurePeer(p);
                }
                else{
                    result = vut.getResult();
                    return result;
                }

            }
        }

        return result;
    }
}
