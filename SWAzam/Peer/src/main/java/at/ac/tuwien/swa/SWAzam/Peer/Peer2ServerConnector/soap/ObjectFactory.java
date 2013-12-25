
package at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap package. 
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

    private final static QName _ReduceCoins_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "reduceCoins");
    private final static QName _AddCoins_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "addCoins");
    private final static QName _ValidateUser_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "validateUser");
    private final static QName _AddCoinsResponse_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "addCoinsResponse");
    private final static QName _ReduceCoinsResponse_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "reduceCoinsResponse");
    private final static QName _ValidateUserResponse_QNAME = new QName("http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", "validateUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateUser }
     * 
     */
    public ValidateUser createValidateUser() {
        return new ValidateUser();
    }

    /**
     * Create an instance of {@link AddCoins }
     * 
     */
    public AddCoins createAddCoins() {
        return new AddCoins();
    }

    /**
     * Create an instance of {@link AddCoinsResponse }
     * 
     */
    public AddCoinsResponse createAddCoinsResponse() {
        return new AddCoinsResponse();
    }

    /**
     * Create an instance of {@link ReduceCoins }
     * 
     */
    public ReduceCoins createReduceCoins() {
        return new ReduceCoins();
    }

    /**
     * Create an instance of {@link ValidateUserResponse }
     * 
     */
    public ValidateUserResponse createValidateUserResponse() {
        return new ValidateUserResponse();
    }

    /**
     * Create an instance of {@link ReduceCoinsResponse }
     * 
     */
    public ReduceCoinsResponse createReduceCoinsResponse() {
        return new ReduceCoinsResponse();
    }

    /**
     * Create an instance of {@link UserInformation }
     * 
     */
    public UserInformation createUserInformation() {
        return new UserInformation();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReduceCoins }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "reduceCoins")
    public JAXBElement<ReduceCoins> createReduceCoins(ReduceCoins value) {
        return new JAXBElement<ReduceCoins>(_ReduceCoins_QNAME, ReduceCoins.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCoins }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "addCoins")
    public JAXBElement<AddCoins> createAddCoins(AddCoins value) {
        return new JAXBElement<AddCoins>(_AddCoins_QNAME, AddCoins.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "validateUser")
    public JAXBElement<ValidateUser> createValidateUser(ValidateUser value) {
        return new JAXBElement<ValidateUser>(_ValidateUser_QNAME, ValidateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCoinsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "addCoinsResponse")
    public JAXBElement<AddCoinsResponse> createAddCoinsResponse(AddCoinsResponse value) {
        return new JAXBElement<AddCoinsResponse>(_AddCoinsResponse_QNAME, AddCoinsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReduceCoinsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "reduceCoinsResponse")
    public JAXBElement<ReduceCoinsResponse> createReduceCoinsResponse(ReduceCoinsResponse value) {
        return new JAXBElement<ReduceCoinsResponse>(_ReduceCoinsResponse_QNAME, ReduceCoinsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Peer2ServerConnector.Server.SWAzam.swa.tuwien.ac.at/", name = "validateUserResponse")
    public JAXBElement<ValidateUserResponse> createValidateUserResponse(ValidateUserResponse value) {
        return new JAXBElement<ValidateUserResponse>(_ValidateUserResponse_QNAME, ValidateUserResponse.class, null, value);
    }

}
