package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
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
import java.util.logging.Logger;

public class Peer2PeerSoapConnector implements Peer2PeerConnector {

    private final static Logger log = Logger.getLogger(Peer2PeerSoapConnector.class.getName());
    private final String peerWSDLLocation;

    @Inject
    public Peer2PeerSoapConnector(@Assisted String peerWSDLLocation) {
        this.peerWSDLLocation = peerWSDLLocation;
    }

    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) throws UnableToConnectToPeer {
        log.info(String.format("Forwarding fingerprint request from user: %s to peer %s ",
                user, peerWSDLLocation));

        at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult result =
                getFingerprintResult(fingerprint, user, hops);

        if (result == null) return null;

        FingerprintResult fingerprintResult = new FingerprintResult();
        fingerprintResult.setResult(result.getResult());
        fingerprintResult.setHops(result.getHops());
        return fingerprintResult;
    }

    private at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult getFingerprintResult(Fingerprint fingerprint, String user, List<String> hops) throws UnableToConnectToPeer {
        try {
            PeerWebServiceSoap peerWebServicePort =
                new PeerWebServiceSoapService(toUrl(peerWSDLLocation)).getPeerWebServiceSoapPort();
            return peerWebServicePort.identifyMP3Fingerprint(new Gson().toJson(fingerprint), user, hops);
        } catch (WebServiceException e) {
            throw new UnableToConnectToPeer(e);
        }
    }

    private URL toUrl(String url) throws UnableToConnectToPeer {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnableToConnectToPeer(e.getMessage());
        }
    }
}
