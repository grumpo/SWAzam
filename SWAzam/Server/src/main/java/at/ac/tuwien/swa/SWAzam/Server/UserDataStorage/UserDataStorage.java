package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.util.Set;


public interface UserDataStorage {
	
	public String addUser(User user);
    public boolean removeUser(User user);
    public boolean reduceCoins(User user);
    public boolean addCoins(User user);
    public Set<User> getUsers();
    
    // validates from hashed pw to hashed pw
	public User getUser(String user, String password);
	
	// validates from pw to hashed pw
	public User validate(String user, String password);
	
}
