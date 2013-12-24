package at.ac.tuwien.swa.SWAzam.Server.DebitManager;

import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;

import com.google.inject.Inject;

public class DebitManagerImpl implements DebitManager {
	
	@Inject
    private UserDataStorage userStorage;

	public void addCoins(User user) {
		userStorage.addCoins(user);
		
	}

	public void removeCoins(User user) {
		userStorage.reduceCoins(user);
		
	}

}
