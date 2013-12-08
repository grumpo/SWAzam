package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoapService;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Client2PeerSoapConnector implements Client2PeerConnector {

    private final static Logger log = Logger.getLogger(Client2PeerSoapConnector.class.getName());

    @Override
    public String identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) {
        log.info("Checking fingerprint: " + fingerprint.getShiftDuration());
        ClientWebServiceSoap peerWebServicePort = getClientWebService();
        String fingerprintJson = serialize(fingerprint);
        return peerWebServicePort.identifyMP3Fingerprint(
                fingerprintJson, user, password);
    }

    private String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

    private ClientWebServiceSoap getClientWebService() {
        return new ClientWebServiceSoapService().getClientWebServiceSoapPort();
    }
}
