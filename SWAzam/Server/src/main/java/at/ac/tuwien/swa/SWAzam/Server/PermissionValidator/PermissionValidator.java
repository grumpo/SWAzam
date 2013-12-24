package at.ac.tuwien.swa.SWAzam.Server.PermissionValidator;

import at.ac.tuwien.swa.SWAzam.Server.Common.UserInformation;

public interface PermissionValidator {

	public UserInformation validateUser(String user, String password);

}
