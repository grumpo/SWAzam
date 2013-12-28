package at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Server.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Server.DebitManager.DebitManager;
import at.ac.tuwien.swa.SWAzam.Server.PermissionValidator.PermissionValidator;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import java.util.logging.Logger;


/*
 * WebService for the peers
 */
@WebService()
public class PeerWebServiceSoap implements PeerWebService {

    private final static Logger log = Logger.getLogger(PeerWebServiceSoap.class.getName());

    private String serverUrl;
    
    private DebitManager debitManager;
    private PermissionValidator permissionValidator;

	@WebMethod
    public UserInformation validateUser (String user, String password) {
        return permissionValidator.validateUser(user, password);
    }
	
	//TODO - add music string (filename? song title, artist?) if present and request id
	@WebMethod
    public void addCoins (User user) {
        debitManager.addCoins(user);
    }
	
	//TODO - add music string (filename? song title, artist?) if present and request id
	@WebMethod
    public void reduceCoins (User user) {
        debitManager.removeCoins(user);
    }
	
	//TODO - add method to initiate a music search, create new open recognitionrequest (vgl at.ac.tuwien.swa.SWAZAM.Server.RecognitionRequest.java)

    @WebMethod(exclude=true)
    public void run(int port, PermissionValidator pv, DebitManager dm) {
        this.debitManager = dm;
        this.permissionValidator = pv;
        serverUrl = String.format("http://localhost:%d", port);
        String address = serverUrl + "/PeerWebService";
        Endpoint.publish(address, this);
        log.info("PeerWebService listens on: " + address);
    }
}
