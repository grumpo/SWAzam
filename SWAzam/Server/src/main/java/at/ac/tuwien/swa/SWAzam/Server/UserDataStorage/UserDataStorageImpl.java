package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;



public class UserDataStorageImpl implements UserDataStorage {

	private final static Logger log = Logger.getLogger(UserDataStorageImpl.class.getName());
    static final int MAX_FAILURE = 5;

    private Connection con;
    private Set<User> users;

    public UserDataStorageImpl(){
    	users = new HashSet<User>();
        //TODO: this should be injected
        try {
            this.con = DriverManager.getConnection("jdbc:hsqldb:file:" + this.getClass().getResource("/Database").getFile() + "/localdb", "SA", "");
        } catch (SQLException e) {
            log.warning("UserDataStorage could not connect to database.");
            e.printStackTrace();
        }
    }
    	
	
	public String addUser(User user) {
		PreparedStatement pstmt;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO user VALUES(?,?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, createPasswordHash(user.getPassword()));
			
			pstmt.execute();
			
			pstmt = con.prepareStatement("INSERT INTO coinlog VALUES(?,?,?,?,?,?,?)");
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, user.getUsername());
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setBoolean(6, false);
			pstmt.setDate(7, new Date(System.currentTimeMillis()));
			
			pstmt.execute();
			
			return "Registration was successful!";
		} catch (SQLException e) {
			return "Username already exists. Please choose another one!";
		}
	}
	

	public boolean removeUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			pstmt = con.prepareStatement("DELETE FROM user WHERE username=?");
			pstmt.setString(1, user.getUsername());
			
			pstmt.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public Set<User> getUsers() {
		PreparedStatement pstmt;
		PreparedStatement pstmt2;
        ResultSet rs;
        ResultSet rs2;
        
        users.clear();

        try{
            log.info("Fetching stored users from Database!");
            pstmt = con.prepareStatement("SELECT * FROM user");

            rs = pstmt.executeQuery();

            while(rs.next()){
            	pstmt2 = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
            	pstmt2.setString(1, rs.getString("username"));
            	
            	rs2 = pstmt2.executeQuery();
            	
            	int coins = -1;
            	
            	// TODO: improve
            	while (rs2.next()){
            		coins = rs2.getInt("coins_new");
            	}
            	
                users.add(new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), coins));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return users;
	}

	public User getUser(String username, String password) {
		PreparedStatement pstmt;
        ResultSet rs;

        try{
            log.info("Validating user!");
            pstmt = con.prepareStatement("SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if(rs.next()) {
            	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
            	pstmt.setString(1, rs.getString("username"));
            	
            	rs = pstmt.executeQuery();
            	
            	int coins = -1;
            	
            	// TODO: improve
            	while (rs.next()){
            		coins = rs.getInt("coins_new");
            	}
            	
            	return new User(username, password, coins);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
	}
	
	
	public User validate(String username, String password) {
    	PreparedStatement pstmt;
        ResultSet rs;

        try{
            log.info("Validating user!");
            pstmt = con.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            pstmt.setString(1, username);
            pstmt.setString(2, createPasswordHash(password));

            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
            	pstmt.setString(1, rs.getString("username"));
            	
            	rs = pstmt.executeQuery();
            	
            	int coins = -1;
            	
            	// TODO: improve
            	while (rs.next()){
            		coins = rs.getInt("coins_new");
            	}
            	
            	return new User(username, password, coins);
            }
            	
 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;   	        
    }
	
	
	/**
	 * if user gets an additional coin due to an resolved fp request
	 */
	public boolean addCoins(User user) {
		PreparedStatement pstmt;
		ResultSet rs;

        try{
        	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
        	pstmt.setString(1, user.getUsername());
        	
        	rs = pstmt.executeQuery();
        	
        	int old_coins = -1;
        	
        	// TODO: improve
        	while (rs.next()){
        		old_coins = rs.getInt("coins_new");
        	}

            log.info("Adding coin to user!");
            pstmt = con.prepareStatement("INSERT INTO coinlog VALUES(?,?,?,?,?,?,?)");
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, user.getUsername());
			// TODO add request id as parameter!
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setInt(3, old_coins);
			pstmt.setInt(4, old_coins+1);
			pstmt.setBoolean(5, false);
			pstmt.setDate(6, new Date(System.currentTimeMillis()));
			
			pstmt.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        		
		return false;
	}
	
	
	/**
	 * if the User buys coins
	 */
	public boolean addCoins(User user, int numCoins) {
		PreparedStatement pstmt;
		ResultSet rs;

        try{
        	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
        	pstmt.setString(1, user.getUsername());
        	
        	rs = pstmt.executeQuery();
        	
        	int old_coins = -1;
        	
        	// TODO: improve
        	while (rs.next()){
        		old_coins = rs.getInt("coins_new");
        	}

            log.info("Adding coin to user!");
            pstmt = con.prepareStatement("INSERT INTO coinlog VALUES(?,?,?,?,?,?,?)");
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, user.getUsername());
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setInt(3, old_coins);
			pstmt.setInt(4, old_coins+numCoins);
			pstmt.setBoolean(5, false);
			pstmt.setDate(6, new Date(System.currentTimeMillis()));
			
			pstmt.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        		
		return false;
	}


	public boolean reduceCoins(User user) {
		PreparedStatement pstmt;
		ResultSet rs;

        try{
        	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
        	pstmt.setString(1, user.getUsername());
        	
        	rs = pstmt.executeQuery();
        	
        	int old_coins = -1;
        	
        	// TODO: improve
        	while (rs.next()){
        		old_coins = rs.getInt("coins_new");
        	}

            log.info("Adding coin to user!");
            pstmt = con.prepareStatement("INSERT INTO coinlog VALUES(?,?,?,?,?,?)");
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, user.getUsername());
			pstmt.setInt(3, old_coins);
			pstmt.setInt(4, old_coins-1);
			pstmt.setBoolean(5, false);
			pstmt.setDate(6, new Date(System.currentTimeMillis()));
			
			pstmt.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        		
		return false;
		
	}
	
	private String createPasswordHash(String password) {
		String generatedPassword = null;
        MessageDigest md;
        byte[] bytes;
        StringBuilder sb;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            bytes = md.digest();
            sb = new StringBuilder();

            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
	}

}
