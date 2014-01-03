package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import at.ac.tuwien.swa.SWAzam.Server.Common.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Server.Entity.CoinLog;
import at.ac.tuwien.swa.SWAzam.Server.Entity.RecognitionRequest;



public class UserDataStorageImpl implements UserDataStorage {

	private final static Logger log = Logger.getLogger(UserDataStorageImpl.class.getName());

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
			pstmt.setString(3, "");
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setString(6, "New User Account Created!");
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
            log.info("Validating user (client app)!");
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
	
	
	public List<CoinLog> getLog() {
		List<CoinLog> log = new ArrayList<CoinLog>();
		
		PreparedStatement pstmt;
        ResultSet rs;
        
        try{
            pstmt = con.prepareStatement("SELECT * FROM coinlog");
            rs = pstmt.executeQuery();
            
        	while (rs.next()){
        		log.add(new CoinLog(rs.getInt("id"), rs.getString("user_username"), rs.getString("request_id"), rs.getInt("coins_old"), rs.getInt("coins_new"), rs.getString("action"), rs.getTimestamp("date")));
        	}
            	
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
		
		return log;
	}
	
	public List<CoinLog> getLogForUser(User user) {
		List<CoinLog> log = new ArrayList<CoinLog>();
		
		PreparedStatement pstmt;
        ResultSet rs;
        
        try{
            pstmt = con.prepareStatement("SELECT * FROM coinlog where user_username=?");
            pstmt.setString(1, user.getUsername());
            
            rs = pstmt.executeQuery();
            
        	while (rs.next()){
        		log.add(new CoinLog(rs.getInt("id"), rs.getString("user_username"), rs.getString("request_id"), rs.getInt("coins_old"), rs.getInt("coins_new"), rs.getString("action"), rs.getTimestamp("date")));
        	}
            	
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        //sort log descending
        Collections.reverse(log);
		
		return log;
	}
	
	public User validate(String username, String password) {
    	PreparedStatement pstmt;
        ResultSet rs;

        try{
            log.info("Validating user (web gui)!");
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
	public boolean addCoins(User user, FingerprintResult result) {
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
			pstmt.setString(3, result.getRequestIDString());
			pstmt.setInt(4, old_coins);
			pstmt.setInt(5, old_coins+1);
			pstmt.setString(6, "Successful Music Recognition -> +1 Coin!");
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
						
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
			pstmt.setString(3, "");
			pstmt.setInt(4, old_coins);
			pstmt.setInt(5, old_coins+numCoins);
			pstmt.setString(6, "Bought " + numCoins + " Coins!");
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			
			pstmt.execute();
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        		
		return false;
	}


	@SuppressWarnings("resource")
	public boolean reduceCoins(User user, FingerprintResult result) {
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

        	//TODO - what if the user has 0 coins?
            log.info("Reducing coins from user!");
            pstmt = con.prepareStatement("INSERT INTO coinlog VALUES(?,?,?,?,?,?,?)");
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, result.getRequestIDString());
			pstmt.setInt(4, old_coins);
			pstmt.setInt(5, old_coins-1);
			pstmt.setString(6, "Successful Music Request -> -1 Coin!");
			pstmt.setDate(7, new Date(System.currentTimeMillis()));
			
			pstmt.execute();
			
			System.out.println("Result artist: " + result.getResult().getArtist() + " + Result title: " + result.getResult().getTitle());
			
			// update the request history UPDATE user set password=? where username=?
			if (result.getResult().getArtist() == null && result.getResult().getTitle() == null) {
				pstmt = con.prepareStatement("Update request set result=?, song=? where REQUEST_ID=?");
	        	pstmt.setString(1, "Song could not be identified.");
	        	pstmt.setString(2, "");
	        	pstmt.setString(3, result.getRequestIDString());
				
	        	pstmt.execute();
			}
			else {
				pstmt = con.prepareStatement("Update request set result=?, song=? where REQUEST_ID=?");
	        	pstmt.setString(1, "Song successfully identified.");
	        	pstmt.setString(2, result.getResult().getArtist() + " - " + result.getResult().getTitle());
	        	pstmt.setString(3, result.getRequestIDString());
				
	        	pstmt.execute();
			}
        	
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


	@Override
	public List<RecognitionRequest> getRequestHistory(User user) {
		List<RecognitionRequest> requests = new ArrayList<RecognitionRequest>();
		
		PreparedStatement pstmt;
        ResultSet rs;
        
        try{
            pstmt = con.prepareStatement("SELECT * FROM request where user_username=?");
            pstmt.setString(1, user.getUsername());
            
            rs = pstmt.executeQuery();
            
        	while (rs.next()){
        		requests.add(new RecognitionRequest(rs.getInt("ID"), rs.getString("REQUEST_ID"), rs.getString("USER_USERNAME"), rs.getTimestamp("DATE"), rs.getString("SONG"), 
        				rs.getString("PEER_URL"), rs.getString("RESULT")));
        	}
            	
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        //sort log descending
        Collections.reverse(requests);
		
		return requests;
	}
	
	@Override
	public boolean addRequestEntry(User user, String request_id) {
		PreparedStatement pstmt;
        
        try{
        	log.info("Added request entry for user " + user.getUsername() + "!");
            pstmt = con.prepareStatement("INSERT INTO request VALUES(?,?,?,?,?,?,?)");
            pstmt.setNull(1, java.sql.Types.INTEGER);
            pstmt.setString(2, request_id);
            pstmt.setString(3, user.getUsername());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, "");
            pstmt.setString(6, "");
            pstmt.setString(7, "");
            
            pstmt.execute();
            
            return true;
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }

		return false;
	}


	@Override
	public String changePassword(User user, String passwordOld, String passwordNew) {
		PreparedStatement pstmt;
        ResultSet rs;

        try{
            log.info("Validating user!");
            pstmt = con.prepareStatement("SELECT password FROM USER WHERE USERNAME=?");
            pstmt.setString(1, user.getUsername());

            rs = pstmt.executeQuery();

            if(rs.next()) {
            	if (!rs.getString("password").equals(createPasswordHash(passwordOld)))
            		return "Sorry, the current password was wrong!";

                pstmt = con.prepareStatement("UPDATE user set password=? where username=?");
                pstmt.setString(1, createPasswordHash(passwordNew));
            	pstmt.setString(2, user.getUsername());
            	
            	pstmt.execute();
            	            	
            	return "Updated password successfully!";
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return "";
	}


	@Override
	public int getCurrentCoinsForUser(User user) {
		PreparedStatement pstmt;
        ResultSet rs;
        int numCoins = -1;

        try{
            pstmt = con.prepareStatement("SELECT * FROM USER WHERE USERNAME=?");
            pstmt.setString(1, user.getUsername());

            rs = pstmt.executeQuery();

            if(rs.next()) {
            	pstmt = con.prepareStatement("SELECT coins_new FROM coinlog where USER_USERNAME=?");
            	pstmt.setString(1, rs.getString("username"));
            	
            	rs = pstmt.executeQuery();
            	            	
            	// TODO: improve
            	while (rs.next()){
            		numCoins = rs.getInt("coins_new");
            	}
            	
            	return numCoins;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return numCoins;
	}
	

}
