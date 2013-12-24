package at.ac.tuwien.swa.SWAzam.Server.PermissionValidator;

import com.google.inject.Inject;
import at.ac.tuwien.swa.SWAzam.Server.Common.UserInformation;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;


public class PermissionValidatorImpl implements PermissionValidator {

	@Inject
    private UserDataStorage userStorage;
	
	public UserInformation validateUser(String user, String password) {
		User validatedUser = userStorage.getUser(user, password);
		
		if (validatedUser != null)
			return new UserInformation(validatedUser.getUsername(), validatedUser.getCoins());
		
		return null;
    }

}
