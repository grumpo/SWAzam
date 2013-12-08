
package at.ac.tuwien.swa.SWAzam.Client.ClientWebServiceClient;

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
@WebServiceClient(name = "ClientWebServiceService", targetNamespace = "http://Peer.SWAzam.swa.tuwien.ac.at/", wsdlLocation = "http://localhost:9000/ClientWebService?wsdl")
public class ClientWebServiceService
    extends Service
{

    private final static URL CLIENTWEBSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException CLIENTWEBSERVICESERVICE_EXCEPTION;
    private final static QName CLIENTWEBSERVICESERVICE_QNAME = new QName("http://Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9000/ClientWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CLIENTWEBSERVICESERVICE_WSDL_LOCATION = url;
        CLIENTWEBSERVICESERVICE_EXCEPTION = e;
    }

    public ClientWebServiceService() {
        super(__getWsdlLocation(), CLIENTWEBSERVICESERVICE_QNAME);
    }

    public ClientWebServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CLIENTWEBSERVICESERVICE_QNAME, features);
    }

    public ClientWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, CLIENTWEBSERVICESERVICE_QNAME);
    }

    public ClientWebServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CLIENTWEBSERVICESERVICE_QNAME, features);
    }

    public ClientWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ClientWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ClientWebService
     */
    @WebEndpoint(name = "ClientWebServicePort")
    public ClientWebService getClientWebServicePort() {
        return super.getPort(new QName("http://Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServicePort"), ClientWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ClientWebService
     */
    @WebEndpoint(name = "ClientWebServicePort")
    public ClientWebService getClientWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://Peer.SWAzam.swa.tuwien.ac.at/", "ClientWebServicePort"), ClientWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CLIENTWEBSERVICESERVICE_EXCEPTION!= null) {
            throw CLIENTWEBSERVICESERVICE_EXCEPTION;
        }
        return CLIENTWEBSERVICESERVICE_WSDL_LOCATION;
    }

}