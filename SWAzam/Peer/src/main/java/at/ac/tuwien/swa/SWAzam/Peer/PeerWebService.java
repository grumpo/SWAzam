package at.ac.tuwien.swa.SWAzam.Peer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

@WebService()
public class PeerWebService {

    private final static Logger log = Logger.getLogger(PeerWebService.class.getName());

    @WebMethod
    public String IdentifyMP3Fingerprint(String requestingPeer) {
        String result = "Handling fingerprint identification request from" + requestingPeer;
        log.info(result);
        return "Justin Bieber Crap";
    }

    public static void main(String[] argv) {
        Object implementor = new PeerWebService();
        String address = "http://localhost:9000/PeerWebService";
        Endpoint.publish(address, implementor);
    }
}
