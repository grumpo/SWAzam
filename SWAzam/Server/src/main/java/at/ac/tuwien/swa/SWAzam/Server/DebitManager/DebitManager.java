package at.ac.tuwien.swa.SWAzam.Server.DebitManager;

import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


public interface DebitManager {
	
	public boolean addCoins(User user, FingerprintResult result);
	public boolean removeCoins(User user, FingerprintResult result);
	public void requestIssued(User user, String request_id);

}
