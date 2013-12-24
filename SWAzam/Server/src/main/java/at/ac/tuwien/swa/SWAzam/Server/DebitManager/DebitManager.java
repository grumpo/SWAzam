package at.ac.tuwien.swa.SWAzam.Server.DebitManager;

import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


public interface DebitManager {
	
	public void addCoins(User user);
	public void removeCoins(User user);

}
