package at.ac.tuwien.swa.SWAzam.Server.DebitManager;

import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;

import com.google.inject.Inject;

public class DebitManagerImpl implements DebitManager {
	
	@Inject
    private UserDataStorage userStorage;

	public boolean addCoins(User user, FingerprintResult result) {
		return userStorage.addCoins(user, result);
		
	}

	public boolean removeCoins(User user, FingerprintResult result) {
		return userStorage.reduceCoins(user, result);
		
	}

	@Override
	public void requestIssued(User user, String request_id) {
		userStorage.addRequestEntry(user, request_id);
	}

}
