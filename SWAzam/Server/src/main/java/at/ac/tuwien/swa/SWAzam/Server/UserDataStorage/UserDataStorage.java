package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.util.List;
import java.util.Set;

import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;


public interface UserDataStorage {
	
	public String addUser(User user);
    public boolean removeUser(User user);
    public boolean reduceCoins(User user);
    public boolean addCoins(User user);
    public boolean addCoins(User user, int numCoins);
    public Set<User> getUsers();
    public List<CoinLog> getLog();
    
    // validates from hashed pw to hashed pw
	public User getUser(String user, String password);
	
	// validates from pw to hashed pw
	public User validate(String user, String password);
	public List<CoinLog> getLogForUser(User user);
	
}
