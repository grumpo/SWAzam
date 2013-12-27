package at.ac.tuwien.swa.SWAzam.Server.Controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import at.ac.tuwien.swa.SWAzam.Server.Model.LoginModel;
import at.ac.tuwien.swa.SWAzam.Server.Model.LoginService;
import at.ac.tuwien.swa.SWAzam.Server.Model.LoginServiceImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;



@ManagedBean(name="userBean")
@SessionScoped
public class UserController implements Serializable {  
  
	private static final long serialVersionUID = 1L;
	
	private String username ="";  
    private String password;  
    private boolean loggedIn;
    private User user;
    
    private LoginService loginService;
    
    private LoginModel loginModel;
    
    public UserController() {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String validateLogin() { 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
    	loginModel = new LoginModel(username, password);
    	user = loginService.validate(loginModel);
    	    	    	
		if(user != null)		
			return "overview.xhtml?faces-redirect=true";
		else 
	        facesContext.addMessage("userBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password is incorrect!", ""));
		
		return "";
	}  
}  
