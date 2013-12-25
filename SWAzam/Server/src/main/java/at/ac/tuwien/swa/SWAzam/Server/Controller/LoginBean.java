package at.ac.tuwien.swa.SWAzam.Server.Controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;
import at.ac.tuwien.swa.SWAzam.Server.Service.LoginService;
import at.ac.tuwien.swa.SWAzam.Server.Service.LoginServiceImpl;


@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {  
  
	private static final long serialVersionUID = 1L;
	private String username;  
    private String password;  
    private boolean loggedIn;
    
    private LoginService loginService;
    
    private LoginModel loginModel;
    
    public LoginBean() {
    	loginService = new LoginServiceImpl();
    	loggedIn = false;
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


	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn() {
		this.loggedIn = true;
	}


	public String validateLogin() { 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
    	loginModel = new LoginModel(username, password);

		if(loginService.validate(loginModel))
			return "overview.xhtml?faces-redirect=true";
		else
	        facesContext.addMessage("loginBean", new FacesMessage("Username or password is incorrect"));
		
		return null;
	}  
}  
