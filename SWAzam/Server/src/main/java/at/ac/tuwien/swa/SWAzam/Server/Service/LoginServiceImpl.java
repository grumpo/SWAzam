package at.ac.tuwien.swa.SWAzam.Server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.UserDataStorage;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDataStorage uds;
 
    public boolean validate(LoginModel loginModel) {
        return uds.validate(loginModel);
    }
 
}
