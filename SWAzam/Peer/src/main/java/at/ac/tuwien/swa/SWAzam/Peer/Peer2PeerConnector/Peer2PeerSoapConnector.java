package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.PeerWebServiceSoapService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Peer2PeerSoapConnector implements Peer2PeerConnector {

    private final static Logger log = Logger.getLogger(Peer2PeerSoapConnector.class.getName());
    private final String peerWSDLLocation;

    @Inject
    public Peer2PeerSoapConnector(@Assisted String peerWSDLLocation) {
        this.peerWSDLLocation = peerWSDLLocation;
    }

    public void identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops, UUID uuid) throws UnableToConnectToPeer {
        log.info(String.format("Forwarding fingerprint request from user: %s to peer %s ",
                user, peerWSDLLocation));

        try {
            PeerWebServiceSoap peerWebServicePort =
                    new PeerWebServiceSoapService(toUrl(peerWSDLLocation)).getPeerWebServiceSoapPort();
            peerWebServicePort.identifyMP3Fingerprint(new Gson().toJson(fingerprint), user, hops, uuid.toString());
        } catch (WebServiceException e) {
            throw new UnableToConnectToPeer(e);
        }
    }

    @Override
    public void identificationResolved(FingerprintResult fingerprintResult) throws UnableToConnectToPeer {
        log.info("Sending the fingerprint result back.");

        try {
            PeerWebServiceSoap peerWebServicePort =
                    new PeerWebServiceSoapService(toUrl(peerWSDLLocation)).getPeerWebServiceSoapPort();
            peerWebServicePort.identificationResult(convertFingerprint(fingerprintResult));
        } catch (WebServiceException e) {
            throw new UnableToConnectToPeer(e);
        }
    }

    private at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult convertFingerprint(FingerprintResult fingerprintResult) {
        if (fingerprintResult == null) return null;
        at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult result =
                new at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult();
        result.setResult(convertAudioInformation(fingerprintResult.getResult()));
        result.getHops().addAll(fingerprintResult.getHops());
        result.setRequestID(fingerprintResult.getRequestID().toString());
        return result;
    }

    private at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.AudioInformation convertAudioInformation(AudioInformation audioInformation) {
        if (audioInformation == null) return null;
        at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.AudioInformation result =
                new at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.AudioInformation();
        result.setArtist(audioInformation.getArtist());
        result.setTitle(audioInformation.getTitle());
        return result;
    }

    private URL toUrl(String url) throws UnableToConnectToPeer {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnableToConnectToPeer(e.getMessage());
        }
    }
}
