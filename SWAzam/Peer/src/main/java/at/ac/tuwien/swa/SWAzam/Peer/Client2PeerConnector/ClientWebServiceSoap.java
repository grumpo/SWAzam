package at.ac.tuwien.swa.SWAzam.Peer.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import com.google.gson.Gson;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

/**
 * Provides a web service for the client.
 * Created by grumpo on 12/7/13.
 */
@WebService()
public class ClientWebServiceSoap implements ClientWebService {

    private final static Logger log = Logger.getLogger(ClientWebServiceSoap.class.getName());

    @WebMethod
    @SuppressWarnings("unused")
    public String identifyMP3Fingerprint(String fingerprintJson, String user, String password) {
        log.info("Handling fingerprint identification request from: " + user);
        Fingerprint fingerprint = new Gson().fromJson(fingerprintJson, Fingerprint.class);
        return "Some good piece of music: " + fingerprint.getShiftDuration();
    }

    @Override
    @WebMethod(exclude=true)
    public void run(int port) {
        String address = String.format("http://localhost:%d/ClientWebService", port);
        Endpoint.publish(address, this);
        log.info("ClientWebService listens on: " + address);
    }
}
