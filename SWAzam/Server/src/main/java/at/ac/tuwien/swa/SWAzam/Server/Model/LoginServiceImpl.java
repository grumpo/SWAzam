package at.ac.tuwien.swa.SWAzam.Server.Model;


import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorageImpl;



public class LoginServiceImpl implements LoginService {
    UserDataStorage uds;
    
    public LoginServiceImpl() {
    	uds = new UserDataStorageImpl();
    }
 
    public User validate(LoginModel loginModel) {
        return uds.validate(loginModel.getUserName(), loginModel.getPassword());
    }
 
}
