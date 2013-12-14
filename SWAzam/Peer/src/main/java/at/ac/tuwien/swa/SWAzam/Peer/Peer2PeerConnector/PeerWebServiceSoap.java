package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import com.google.gson.Gson;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;
import java.util.logging.Logger;

@WebService()
public class PeerWebServiceSoap implements PeerWebService {

    private final static Logger log = Logger.getLogger(PeerWebService.class.getName());

    @WebMethod
    public FingerprintResult IdentifyMP3Fingerprint(String fingerprintJson, String user, List<String> hops) {
        log.info("Handling fingerprint identification request from: " + user);
        Fingerprint fingerprint = new Gson().fromJson(fingerprintJson, Fingerprint.class);
        FingerprintResult result = new FingerprintResult();
        result.setResult("Some good piece of music: " + fingerprint.getShiftDuration());
        // TODO: identify fingerprint
        return result;
    }

    @WebMethod(exclude=true)
    public void run(int port) {
        String address = String.format("http://localhost:%d/PeerWebService", port);
        Endpoint.publish(address, this);
        log.info("PeerWebService listens on: " + address);
    }
}
