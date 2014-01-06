package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.util.List;
import java.util.Set;
import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;



public interface UserDataStorage {
	
	public String addUser(User user);
    public boolean removeUser(User user);
    public boolean reduceCoins(User user, FingerprintResult result);
    public boolean addCoins(User user, FingerprintResult result);
    public boolean addCoins(User user, int numCoins);
    public Set<User> getUsers();
    public List<CoinLog> getLog();
    
    // validates from hashed pw to hashed pw
	public User getUser(String user, String password);
	// validates from pw to hashed pw
	public User validate(String user, String password);
	
	public List<CoinLog> getLogForUser(User user);
	public List<RecognitionRequest> getRequestHistory(User user);
	public boolean addRequestEntry(User user, String request_id);
	public String changePassword(User user, String passwordOld, String passwordNew);
	public int getCurrentCoinsForUser(User user);
	
}
