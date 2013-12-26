package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.util.Set;

import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;


public interface UserDataStorage {
	
	public String addUser(User user);
    public boolean removeUser(User user);
    public boolean reduceCoins(User user);
    public boolean addCoins(User user);
    public Set<User> getUsers();
	public User getUser(String user, String password);
	
	public boolean validate(LoginModel loginModel);

}
