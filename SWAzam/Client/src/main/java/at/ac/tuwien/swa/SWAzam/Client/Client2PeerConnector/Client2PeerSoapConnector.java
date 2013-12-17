package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.ClientWebServiceSoapService;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import com.google.gson.Gson;

import javax.xml.soap.*;
import java.io.IOException;
import java.util.logging.Logger;

public class Client2PeerSoapConnector implements Client2PeerConnector {

    private final static Logger log = Logger.getLogger(Client2PeerSoapConnector.class.getName());

    @Override
    public FingerprintResult identifyMP3Fingerprint(Fingerprint fingerprint, String user, String password) {
//        log.info("Checking fingerprint: " + fingerprint.getShiftDuration());
//        ClientWebServiceSoap peerWebServicePort = getClientWebService();
//        String fingerprintJson = serialize(fingerprint);
//        at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.FingerprintResult result =
//                peerWebServicePort.identifyMP3Fingerprint(fingerprintJson, user, password);
//        FingerprintResult fingerprintResult = new FingerprintResult();
//        fingerprintResult.setResult(result.getResult());
//        fingerprintResult.setHops(result.getHops());
//        return fingerprintResult;

        return null;
    }

    @Override
    public FingerprintResult identifyMP3Fingerprint(String url, Fingerprint fp, User user){
        try {
            SOAPConnectionFactory cf = SOAPConnectionFactory.newInstance();
            SOAPConnection con = cf.createConnection();

            SOAPMessage resp = con.call(createSOAPRequest(url, fp, user), url);

            con.close();
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SOAPMessage createSOAPRequest(String url, Fingerprint fp, User user){
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage msg = mf.createMessage();
            SOAPPart sp = msg.getSOAPPart();

            SOAPEnvelope env = sp.getEnvelope();

            SOAPBody sb = env.getBody();
            SOAPElement sfp = sb.addChildElement("Fingerprint");
            sfp.addTextNode(new Gson().toJson(fp));
            SOAPElement su = sb.addChildElement("User");
            su.addTextNode(new Gson().toJson(user));

            MimeHeaders headers = msg.getMimeHeaders();
            headers.addHeader("SOAPAction", url);

            msg.saveChanges();

            msg.writeTo(System.out);

            return msg;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String serialize(Fingerprint fingerprint) {
        return new Gson().toJson(fingerprint);
    }

//    private ClientWebServiceSoap getClientWebService() {
//        return new ClientWebServiceSoapService().getClientWebServiceSoapPort();
//    }
}
