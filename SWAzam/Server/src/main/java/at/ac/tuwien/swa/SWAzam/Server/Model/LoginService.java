package at.ac.tuwien.swa.SWAzam.Server.Model;

import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


public interface LoginService {
	public User validate(LoginModel loginModel);
}
