package at.ac.tuwien.swa.SWAzam.Server.Model;

import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;

public class AccountServiceImpl implements AccountService {

	UserDataStorage uds;
	
	public AccountServiceImpl() {
		uds = new UserDataStorageImpl();
	}
	
	@Override
	public String changePassword(User user, String passwordOld, String passwordNew) {
		return uds.changePassword(user, passwordOld, passwordNew);
	}

}
