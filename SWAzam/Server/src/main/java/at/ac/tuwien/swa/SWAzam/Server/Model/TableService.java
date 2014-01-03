package at.ac.tuwien.swa.SWAzam.Server.Model;

import java.util.List;

import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;

public interface TableService {
	
	public List<CoinLog> getCoinLog();
	public List<CoinLog> getCoinLogforUser(User user);
	public List<RecognitionRequest> getRequestHistory(User user);
	public int getCurrentCoinsForUser(User user);

}
