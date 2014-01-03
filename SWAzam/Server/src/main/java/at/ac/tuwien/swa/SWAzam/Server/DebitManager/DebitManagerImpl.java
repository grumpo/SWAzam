package at.ac.tuwien.swa.SWAzam.Server.DebitManager;

import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;

import com.google.inject.Inject;

public class DebitManagerImpl implements DebitManager {
	
	@Inject
    private UserDataStorage userStorage;

	public void addCoins(User user, FingerprintResult result) {
		userStorage.addCoins(user, result);
		
	}

	public void removeCoins(User user, FingerprintResult result) {
		userStorage.reduceCoins(user, result);
		
	}

	@Override
	public void requestIssued(User user, String request_id) {
		userStorage.addRequestEntry(user, request_id);
	}

}
