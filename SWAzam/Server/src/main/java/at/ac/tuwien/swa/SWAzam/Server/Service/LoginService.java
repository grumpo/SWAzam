package at.ac.tuwien.swa.SWAzam.Server.Service;

import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;

public interface LoginService {
	public boolean validate(LoginModel loginModel);
}
