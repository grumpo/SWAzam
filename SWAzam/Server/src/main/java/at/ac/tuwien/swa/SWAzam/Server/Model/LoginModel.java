package at.ac.tuwien.swa.SWAzam.Server.Model;

public class LoginModel {
	 
    private String userName;
    private String password;
     
    public LoginModel() {
    	
    }
 
    public LoginModel(String username, String password) {
		this.userName = username;
		this.password = password;
	}
    
    
	public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
 
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
