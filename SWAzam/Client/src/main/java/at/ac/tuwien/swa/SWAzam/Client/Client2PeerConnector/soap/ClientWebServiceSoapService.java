
package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ClientWebServiceSoapService", targetNamespace = "http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", wsdlLocation = "http://localhost:9000/ClientWebService?wsdl")
public class ClientWebServiceSoapService
    extends Service
{

    private final static URL CLIENTWEBSERVICESOAPSERVICE_WSDL_LOCATION;
    private final static WebServiceException CLIENTWEBSERVICESOAPSERVICE_EXCEPTION;
    private final static QName CLIENTWEBSERVICESOAPSERVICE_QNAME = new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServiceSoapService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9000/ClientWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CLIENTWEBSERVICESOAPSERVICE_WSDL_LOCATION = url;
        CLIENTWEBSERVICESOAPSERVICE_EXCEPTION = e;
    }

    public ClientWebServiceSoapService() {
        super(__getWsdlLocation(), CLIENTWEBSERVICESOAPSERVICE_QNAME);
    }

    public ClientWebServiceSoapService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CLIENTWEBSERVICESOAPSERVICE_QNAME, features);
    }

    public ClientWebServiceSoapService(URL wsdlLocation) {
        super(wsdlLocation, CLIENTWEBSERVICESOAPSERVICE_QNAME);
    }

    public ClientWebServiceSoapService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CLIENTWEBSERVICESOAPSERVICE_QNAME, features);
    }

    public ClientWebServiceSoapService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ClientWebServiceSoapService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ClientWebServiceSoap
     */
    @WebEndpoint(name = "ClientWebServiceSoapPort")
    public ClientWebServiceSoap getClientWebServiceSoapPort() {
        return super.getPort(new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServiceSoapPort"), ClientWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ClientWebServiceSoap
     */
    @WebEndpoint(name = "ClientWebServiceSoapPort")
    public ClientWebServiceSoap getClientWebServiceSoapPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Client2PeerConnector.Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServiceSoapPort"), ClientWebServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CLIENTWEBSERVICESOAPSERVICE_EXCEPTION!= null) {
            throw CLIENTWEBSERVICESOAPSERVICE_EXCEPTION;
        }
        return CLIENTWEBSERVICESOAPSERVICE_WSDL_LOCATION;
    }

}
