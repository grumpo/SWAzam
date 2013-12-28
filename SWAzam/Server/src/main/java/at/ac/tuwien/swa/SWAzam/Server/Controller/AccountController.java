package at.ac.tuwien.swa.SWAzam.Server.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import at.ac.tuwien.swa.SWAzam.Server.Model.AccountService;
import at.ac.tuwien.swa.SWAzam.Server.Model.AccountServiceImpl;
import at.ac.tuwien.swa.SWAzam.Server.UserDataStorage.User;


@ManagedBean(name="accountBean")
@SessionScoped
public class AccountController {
	
	private String passwordOld;
	private String passwordNew1;
	private String passwordNew2;
	
	private int numCoins;
	
	private User user;
	
	private AccountService accountService;
	
	public AccountController() {
		accountService = new AccountServiceImpl();
	}
	
	
	public String getPasswordOld() {
		return passwordOld;
	}
	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}
	public String getPasswordNew1() {
		return passwordNew1;
	}
	public void setPasswordNew1(String passwordNew1) {
		this.passwordNew1 = passwordNew1;
	}
	public String getPasswordNew2() {
		return passwordNew2;
	}
	public void setPasswordNew2(String passwordNew2) {
		this.passwordNew2 = passwordNew2;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getNumCoins() {
		return numCoins;
	}
	public void setNumCoins(int numCoins) {
		this.numCoins = numCoins;
	}

	public String changePassword() {
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		
		if (passwordOld.equals("") || passwordNew1.equals("") || passwordNew2.equals("")) {
    		facesContext.addMessage("accountBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill out all fields!", ""));
    		return "";
    	}	
    	
		//TODO check if username already exists
		if (passwordNew1.equals(passwordNew2)) {
			facesContext.addMessage("accountBean", new FacesMessage(accountService.changePassword(user, passwordOld, passwordNew1)));
			
			// reset fields
			passwordOld = "";
			passwordNew1 = "";
			passwordNew2 = "";
		}
		else
			facesContext.addMessage("accountBean", new FacesMessage(FacesMessage.SEVERITY_ERROR, "New password entries don't match!", ""));
		
		return "";
	}
	
	public String buyCoins(){
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		facesContext.addMessage("accountBean", new FacesMessage(accountService.buyCoins(user, numCoins)));
		
		return "";
	}

}
