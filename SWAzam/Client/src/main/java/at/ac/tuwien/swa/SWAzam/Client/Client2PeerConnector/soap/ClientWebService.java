
package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ClientWebService", targetNamespace = "http://Peer.SWAzam.swa.tuwien.ac.at/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ClientWebService {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "identifyMP3Fingerprint", targetNamespace = "http://Peer.SWAzam.swa.tuwien.ac.at/", className = "at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.IdentifyMP3Fingerprint")
    @ResponseWrapper(localName = "identifyMP3FingerprintResponse", targetNamespace = "http://Peer.SWAzam.swa.tuwien.ac.at/", className = "at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap.IdentifyMP3FingerprintResponse")
    @Action(input = "http://Peer.SWAzam.swa.tuwien.ac.at/ClientWebService/identifyMP3FingerprintRequest", output = "http://Peer.SWAzam.swa.tuwien.ac.at/ClientWebService/identifyMP3FingerprintResponse")
    public String identifyMP3Fingerprint(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

}
