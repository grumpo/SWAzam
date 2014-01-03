
package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap package. 
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

    private final static QName _GetUserInformation_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "getUserInformation");
    private final static QName _IdentifyMP3FingerprintResponse_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "identifyMP3FingerprintResponse");
    private final static QName _GetUserInformationResponse_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "getUserInformationResponse");
    private final static QName _Stop_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "stop");
    private final static QName _IdentifyMP3Fingerprint_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "identifyMP3Fingerprint");
    private final static QName _StopResponse_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "stopResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StopResponse }
     * 
     */
    public StopResponse createStopResponse() {
        return new StopResponse();
    }

    /**
     * Create an instance of {@link IdentifyMP3Fingerprint }
     * 
     */
    public IdentifyMP3Fingerprint createIdentifyMP3Fingerprint() {
        return new IdentifyMP3Fingerprint();
    }

    /**
     * Create an instance of {@link Stop }
     * 
     */
    public Stop createStop() {
        return new Stop();
    }

    /**
     * Create an instance of {@link IdentifyMP3FingerprintResponse }
     * 
     */
    public IdentifyMP3FingerprintResponse createIdentifyMP3FingerprintResponse() {
        return new IdentifyMP3FingerprintResponse();
    }

    /**
     * Create an instance of {@link GetUserInformationResponse }
     * 
     */
    public GetUserInformationResponse createGetUserInformationResponse() {
        return new GetUserInformationResponse();
    }

    /**
     * Create an instance of {@link GetUserInformation }
     * 
     */
    public GetUserInformation createGetUserInformation() {
        return new GetUserInformation();
    }

    /**
     * Create an instance of {@link AudioInformation }
     * 
     */
    public AudioInformation createAudioInformation() {
        return new AudioInformation();
    }

    /**
     * Create an instance of {@link FingerprintResult }
     * 
     */
    public FingerprintResult createFingerprintResult() {
        return new FingerprintResult();
    }

    /**
     * Create an instance of {@link UserInformation }
     * 
     */
    public UserInformation createUserInformation() {
        return new UserInformation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "getUserInformation")
    public JAXBElement<GetUserInformation> createGetUserInformation(GetUserInformation value) {
        return new JAXBElement<GetUserInformation>(_GetUserInformation_QNAME, GetUserInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifyMP3FingerprintResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "identifyMP3FingerprintResponse")
    public JAXBElement<IdentifyMP3FingerprintResponse> createIdentifyMP3FingerprintResponse(IdentifyMP3FingerprintResponse value) {
        return new JAXBElement<IdentifyMP3FingerprintResponse>(_IdentifyMP3FingerprintResponse_QNAME, IdentifyMP3FingerprintResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "getUserInformationResponse")
    public JAXBElement<GetUserInformationResponse> createGetUserInformationResponse(GetUserInformationResponse value) {
        return new JAXBElement<GetUserInformationResponse>(_GetUserInformationResponse_QNAME, GetUserInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Stop }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "stop")
    public JAXBElement<Stop> createStop(Stop value) {
        return new JAXBElement<Stop>(_Stop_QNAME, Stop.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifyMP3Fingerprint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "identifyMP3Fingerprint")
    public JAXBElement<IdentifyMP3Fingerprint> createIdentifyMP3Fingerprint(IdentifyMP3Fingerprint value) {
        return new JAXBElement<IdentifyMP3Fingerprint>(_IdentifyMP3Fingerprint_QNAME, IdentifyMP3Fingerprint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", name = "stopResponse")
    public JAXBElement<StopResponse> createStopResponse(StopResponse value) {
        return new JAXBElement<StopResponse>(_StopResponse_QNAME, StopResponse.class, null, value);
    }

}
