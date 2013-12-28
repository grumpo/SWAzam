package at.ac.tuwien.swa.SWAzam.Server.Model;

import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;

public interface AccountService {
	
	public String changePassword(User user, String passwordOld, String passwordNew);

}
