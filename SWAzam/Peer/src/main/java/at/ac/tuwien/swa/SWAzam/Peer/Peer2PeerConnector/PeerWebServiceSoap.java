package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.RequestHandler.RequestHandler;
import com.google.gson.Gson;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;
import java.util.logging.Logger;

@WebService()
public class PeerWebServiceSoap implements PeerWebService {

    private final static Logger log = Logger.getLogger(PeerWebServiceSoap.class.getName());

    private RequestHandler requestHandler;
    private String peerUrl;

    @WebMethod
    public FingerprintResult IdentifyMP3Fingerprint(String fingerprintJson, String user, List<String> hops) {
        log.info("Handling fingerprint identification request from: " + user);
        Fingerprint fingerprint = new Gson().fromJson(fingerprintJson, Fingerprint.class);
        hops.add(peerUrl);
        return requestHandler.identifyMP3Fingerprint(fingerprint, user, hops);
    }

    @WebMethod(exclude=true)
    public void run(int port, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        peerUrl = String.format("http://localhost:%d", port);
        String address = peerUrl + "/PeerWebService";
        Endpoint.publish(address, this);
        log.info("PeerWebService listens on: " + address);
    }
}
