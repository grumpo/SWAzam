package at.ac.tuwien.swa.SWAzam.Server.Peer2ServerConnector;

import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
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
	
	@WebMethod
    public boolean addCoins (User user, FingerprintResult result) {
        return debitManager.addCoins(user, result);
    }
	
	@WebMethod
    public boolean reduceCoins (User user, FingerprintResult result) {
		return debitManager.removeCoins(user, result);
    }

	// initiate a recognition request - should be called directly after receiving the request from the client, before searching for music matches
	@WebMethod
    public void requestIssued (User user, String request_id) {
        debitManager.requestIssued(user, request_id);
    }
	

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
