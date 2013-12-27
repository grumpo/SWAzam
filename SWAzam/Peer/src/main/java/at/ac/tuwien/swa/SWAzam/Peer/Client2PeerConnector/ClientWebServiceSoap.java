package at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import com.google.gson.Gson;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Provides a web service for the client.
 * Created by grumpo on 12/7/13.
 */
@WebService()
public class ClientWebServiceSoap implements ClientWebService {

    private final static Logger log = Logger.getLogger(ClientWebServiceSoap.class.getName());

    private RequestHandler requestHandler;
    private String peerUrl;
    private Endpoint endpoint;

    @WebMethod
    @SuppressWarnings("unused")
    public FingerprintResult identifyMP3Fingerprint(String fingerprintJson, String user, String password) {
        log.info("Handling fingerprint identification request from: " + user);
        Fingerprint fingerprint = new Gson().fromJson(fingerprintJson, Fingerprint.class);
        return requestHandler.identifyMP3Fingerprint(fingerprint, user, Arrays.asList(peerUrl));
    }

    @WebMethod
    @SuppressWarnings("unused")
    public UserInformation getUserInformation(String user, String password) {
        log.info("Handling user information request from: " + user);
        return requestHandler.getUserInformation(user, password);
    }

    @Override
    @WebMethod(exclude=true)
    public void run(int port, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        peerUrl = String.format("http://localhost:%d", port);
        String address = peerUrl + "/ClientWebService";
        endpoint = Endpoint.publish(address, this);
        log.info("ClientWebService listens on: " + address);

    }

    @Override
    public void stop() {
        log.info("Stopping service...");
        endpoint.stop();
    }
}
