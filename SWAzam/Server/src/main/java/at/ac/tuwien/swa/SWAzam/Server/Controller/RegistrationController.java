package at.ac.tuwien.swa.SWAzam.Server.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.swa.SWAzam.Server.Model.RegistrationService;
import at.ac.tuwien.swa.SWAzam.Server.Model.RegistrationServiceImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


@ManagedBean(name="registrationBean")
@SessionScoped
public class RegistrationController {
	
	String username = "";
	String password = "";
	String password2;
	
	private RegistrationService registrationService;
	
	public RegistrationController() {
		registrationService = new RegistrationServiceImpl();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String register() {	
		FacesContext facesContext = FacesContext.getCurrentInstance();	
    	
    	if (username.equals("")) {
    		facesContext.addMessage("registrationBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username can not be empty!", ""));
    		return "";
    	}	
    	
    	if (password.equals("")) {
    		facesContext.addMessage("registrationBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password can not be empty!", ""));
    		return "";
    	}
    	
		//TODO check if username already exists
		if (password.equals(password2)) {
			facesContext.addMessage("registrationBean", new FacesMessage(registrationService.register(new User(username, password, 0))));
			
			// reset fields
			username = "";
			password = "";
			password2 = "";
		}
		else
			facesContext.addMessage("registrationBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords don't match!", ""));
		
		return "";
	}

}
