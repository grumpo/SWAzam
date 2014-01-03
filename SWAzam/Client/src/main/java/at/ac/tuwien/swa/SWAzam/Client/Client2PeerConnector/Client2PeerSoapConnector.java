package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoapService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;

public class Client2PeerSoapConnector implements Client2PeerConnector {

    private final static Logger log = Logger.getLogger(Client2PeerSoapConnector.class.getName());
    private final String peerWSDLLocation;

    @Inject
    public Client2PeerSoapConnector(@Assisted String peerWSDLLocation) {
        this.peerWSDLLocation = peerWSDLLocation;
    }

    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) throws PeerUnreachableException {
        log.info("Checking fingerprint: " + fingerprint.getShiftDuration());
        ClientWebServiceSoap peerWebServicePort = getClientWebService();
        String fingerprintJson = serialize(fingerprint);
        return convertFingerprint(peerWebServicePort.identifyMP3Fingerprint(fingerprintJson, user, password));
    }

    @Override
    public UserInformation getUserInformation(String user, String password) throws PeerUnreachableException {
        log.info("Checking user: " + user);
        at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.UserInformation userInformation =
                getClientWebService().getUserInformation(user, password);
        if (userInformation == null) return null;
        return new UserInformation(userInformation.getUsername(), userInformation.getCredits());
    }

    private FingerprintResult convertFingerprint(at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.FingerprintResult fingerprintResult) {
        if (fingerprintResult == null) return null;
        FingerprintResult result = new FingerprintResult();
        result.setResult(convertAudioInformation(fingerprintResult.getResult()));
        result.getHops().addAll(fingerprintResult.getHops());
        result.setRequestID(UUID.fromString(fingerprintResult.getRequestID()));
        return result;
    }

    private AudioInformation convertAudioInformation(at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.AudioInformation audioInformation) {
        if (audioInformation == null) return null;
        AudioInformation result = new AudioInformation();
        result.setArtist(audioInformation.getArtist());
        result.setTitle(audioInformation.getTitle());
        return result;
    }

    private String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

    private ClientWebServiceSoap getClientWebService() throws PeerUnreachableException {
        return new ClientWebServiceSoapService(toUrl(peerWSDLLocation)).getClientWebServiceSoapPort();
    }

    private URL toUrl(String url) throws PeerUnreachableException {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new PeerUnreachableException(e.getMessage());
        }
    }
}
