package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.PeerWebServiceSoapService;
import com.google.gson.Gson;

import java.util.List;
import java.util.logging.Logger;

public class Peer2PeerSoapConnector implements Peer2PeerConnector {

    private final static Logger log = Logger.getLogger(Peer2PeerSoapConnector.class.getName());

    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, List<String> hops) {
        log.info("Forwarding fingerprint request from user: " + user);
        PeerWebServiceSoap peerWebServicePort = new PeerWebServiceSoapService().getPeerWebServiceSoapPort();

        at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap.FingerprintResult result =
                peerWebServicePort.identifyMP3Fingerprint(new Gson().toJson(fingerprint), user, hops);

        FingerprintResult fingerprintResult = new FingerprintResult();
        fingerprintResult.setResult(result.getResult());
        fingerprintResult.setHops(result.getHops());
        return fingerprintResult;
    }
}
