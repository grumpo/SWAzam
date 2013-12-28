package at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Peer.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.PeerWebServiceSoap;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.PeerWebServiceSoapService;
import at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.User;
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
        log.info("Validating user: " + user);
        at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.UserInformation userInformation = getValidateUserResult(user, password);
        return new UserInformation(userInformation.getUsername(), userInformation.getCredits());
    }

    @Override
    public boolean resolvedIdentification(String user, String password) throws UnableToConnectToServerException {
        log.info("Adding some coins: " + user);
        try {
            User userArg = new User();
            userArg.setUsername(user);
            userArg.setPassword(password);
            getPeerWebService().addCoins(userArg);
            return true; // TODO: adapt server webservice and use its result
        } catch (WebServiceException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

    @Override
    public boolean requestedIdentification(String user, String password) throws UnableToConnectToServerException {
        log.info("Removing some coins: " + user);
        try {
            User userArg = new User();
            userArg.setUsername(user);
            userArg.setPassword(password);
            getPeerWebService().reduceCoins(userArg);
            return true; // TODO: adapt server webservice and use its result
        } catch (WebServiceException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

    private at.ac.tuwien.swa.SWAzam.Peer.Peer2ServerConnector.soap.UserInformation getValidateUserResult(String user, String password) throws UnableToConnectToServerException {
        try {
            return getPeerWebService().validateUser(user, password);
        } catch (WebServiceException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

    private PeerWebServiceSoap getPeerWebService() throws UnableToConnectToServerException {
        return new PeerWebServiceSoapService(toUrl(serverWSDLLocation)).getPeerWebServiceSoapPort();
    }

    private URL toUrl(String url) throws UnableToConnectToServerException {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnableToConnectToServerException(e);
        }
    }

}
