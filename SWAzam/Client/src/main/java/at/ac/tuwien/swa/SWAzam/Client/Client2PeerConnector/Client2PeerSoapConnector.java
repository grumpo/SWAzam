package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebService;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceService;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Client2PeerSoapConnector implements Client2PeerConnector {

    private final static Logger log = Logger.getLogger(Client2PeerSoapConnector.class.getName());

    @Override
    public String identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) {
        ClientWebService peerWebServicePort = getClientWebService();
        String fingerprintJson = serialize(fingerprint);
        log.info("Checking fingerprint: " + fingerprintJson);
        return peerWebServicePort.identifyMP3Fingerprint(
                fingerprintJson);
    }

    private String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

    private ClientWebService getClientWebService() {
        return new ClientWebServiceService().getClientWebServicePort();
    }
}
