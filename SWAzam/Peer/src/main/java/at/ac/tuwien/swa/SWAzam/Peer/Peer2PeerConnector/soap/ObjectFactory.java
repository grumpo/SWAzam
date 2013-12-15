
package at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IdentifyMP3FingerprintResponse_QNAME = new QName("http://Peer2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "IdentifyMP3FingerprintResponse");
    private final static QName _IdentifyMP3Fingerprint_QNAME = new QName("http://Peer2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "IdentifyMP3Fingerprint");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.ac.tuwien.swa.SWAzam.Peer.Peer2PeerConnector.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdentifyMP3FingerprintResponse }
     * 
     */
    public IdentifyMP3FingerprintResponse createIdentifyMP3FingerprintResponse() {
        return new IdentifyMP3FingerprintResponse();
    }

    /**
     * Create an instance of {@link IdentifyMP3Fingerprint }
     * 
     */
    public IdentifyMP3Fingerprint createIdentifyMP3Fingerprint() {
        return new IdentifyMP3Fingerprint();
    }

    /**
     * Create an instance of {@link FingerprintResult }
     * 
     */
    public FingerprintResult createFingerprintResult() {
        return new FingerprintResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifyMP3FingerprintResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "IdentifyMP3FingerprintResponse")
    public JAXBElement<IdentifyMP3FingerprintResponse> createIdentifyMP3FingerprintResponse(IdentifyMP3FingerprintResponse value) {
        return new JAXBElement<IdentifyMP3FingerprintResponse>(_IdentifyMP3FingerprintResponse_QNAME, IdentifyMP3FingerprintResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifyMP3Fingerprint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "IdentifyMP3Fingerprint")
    public JAXBElement<IdentifyMP3Fingerprint> createIdentifyMP3Fingerprint(IdentifyMP3Fingerprint value) {
        return new JAXBElement<IdentifyMP3Fingerprint>(_IdentifyMP3Fingerprint_QNAME, IdentifyMP3Fingerprint.class, null, value);
    }

}
