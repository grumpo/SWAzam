package at.ac.tuwien.swa.SWAzam.Server.Model;

import java.util.List;

import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;



public class TableServiceImpl implements TableService {

	UserDataStorage uds;
	
	public TableServiceImpl() {
		uds = new UserDataStorageImpl();
	}
	
	@Override
	public List<CoinLog> getCoinLog() {
		return uds.getLog();
	}

	@Override
	public List<RecognitionRequest> getRequestHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoinLog> getCoinLogforUser(User user) {
		return uds.getLogForUser(user);
	}

}
