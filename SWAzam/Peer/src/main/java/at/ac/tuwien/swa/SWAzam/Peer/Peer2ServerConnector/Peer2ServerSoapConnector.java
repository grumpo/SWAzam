package at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.PeerWebServiceSoapService;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class Peer2ServerSoapConnector implements Peer2ServerConnector {

    private final static Logger log = Logger.getLogger(Peer2ServerSoapConnector.class.getName());
    private final String serverWSDLLocation;

    @Inject
    public Peer2ServerSoapConnector(@Assisted String serverWSDLLocation) {
        this.serverWSDLLocation = serverWSDLLocation;
    }

    @Override
    public UserInformation validateUser(String user, String password) throws UnableToConnectToServerException {
        at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.UserInformation userInformation = getValidateUserResult(user, password);
        return new UserInformation(userInformation.getUsername(), userInformation.getCredits());
    }

    private at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.UserInformation getValidateUserResult(String user, String password) throws UnableToConnectToServerException {
        try {
            PeerWebServiceSoap peerWebServicePort =
                    new PeerWebServiceSoapService(toUrl(serverWSDLLocation)).getPeerWebServiceSoapPort();
            return peerWebServicePort.validateUser(user, password);
        } catch (WebServiceException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

    private URL toUrl(String url) throws UnableToConnectToServerException {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

}
