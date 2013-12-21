package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoapService;
import com.google.gson.Gson;

import java.util.logging.Logger;

public class Client2PeerSoapConnector implements Client2PeerConnector {

    private final static Logger log = Logger.getLogger(Client2PeerSoapConnector.class.getName());

    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) {
        log.info("Checking fingerprint: " + fingerprint.getShiftDuration());
        ClientWebServiceSoap peerWebServicePort = getClientWebService();
        String fingerprintJson = serialize(fingerprint);
        at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.FingerprintResult result =
                peerWebServicePort.identifyMP3Fingerprint(fingerprintJson, user, password);
        FingerprintResult fingerprintResult = new FingerprintResult();
        fingerprintResult.setResult(result.getResult());
        fingerprintResult.setHops(result.getHops());
        return fingerprintResult;
    }

    @Override
    public UserInformation getUserInformation(String user, String password) {
        log.info("Checking user: " + user);
        at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.UserInformation userInformation =
                getClientWebService().getUserInformation(user, password);
        if (userInformation == null) return null;
        return new UserInformation(userInformation.getUsername(), userInformation.getCredits());
    }

    private String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

    private ClientWebServiceSoap getClientWebService() {
        return new ClientWebServiceSoapService().getClientWebServiceSoapPort();
    }
}
