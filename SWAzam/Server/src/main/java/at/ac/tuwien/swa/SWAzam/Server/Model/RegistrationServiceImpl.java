package at.ac.tuwien.swa.SWAzam.Server.Model;


import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;

public class RegistrationServiceImpl implements RegistrationService {

	UserDataStorage uds;
	
	public RegistrationServiceImpl() {
		uds = new UserDataStorageImpl();
	}
	
	@Override
	public String register(User user) {
		// TODO Auto-generated method stub
		System.out.println(user.toString());
		return uds.addUser(user);		
	}

}
