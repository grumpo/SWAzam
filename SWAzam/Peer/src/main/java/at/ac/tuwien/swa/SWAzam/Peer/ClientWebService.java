package at.ac.tuwien.swa.SWAzam.Peer;

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
public class ClientWebService {

    private final static Logger log = Logger.getLogger(ClientWebService.class.getName());

    @WebMethod
    @SuppressWarnings("unused")
    public String identifyMP3Fingerprint(String fingerprintJson, String user, String password) {
        log.info("Handling fingerprint identification request from: " + user);
        Fingerprint fingerprint = new Gson().fromJson(fingerprintJson, Fingerprint.class);
        return "Some good piece of music: " + fingerprint.getShiftDuration();
    }

    public static void main(String[] argv) {
        Object implementor = new ClientWebService();
        String address = "http://localhost:9000/ClientWebService";
        Endpoint.publish(address, implementor);
    }
}
