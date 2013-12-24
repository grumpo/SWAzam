package at.ac.tuwien.swa.SWAzam.Server.UserDataStorage;

import java.sql.Connection;
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
    
    
	
	
	
	public boolean addUser(User user) {
		PreparedStatement pstmt;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO user VALUES(?,?,?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getCoins());
			
			pstmt.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
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
        ResultSet rs;
        
        users.clear();

        try{
            log.info("Fetching stored users from Database!");
            pstmt = con.prepareStatement("SELECT * FROM USER");

            rs = pstmt.executeQuery();

            while(rs.next()){
                users.add(new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("COINS")));
            }
            
            /*
            for (User user : users) {
            	System.out.println(user.toString());
            }*/
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return users;
	}

	public User getUser(String user, String password) {
		PreparedStatement pstmt;
        ResultSet rs;

        try{
            log.info("Validating user!");
            pstmt = con.prepareStatement("SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?");
            pstmt.setString(1, user);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if(rs.next())
                return new User(rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getInt("COINS"));
 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
	}
	
	public boolean addCoins(User user) {
		PreparedStatement pstmt;
		ResultSet rs;

        try{
        	int currentCoins = -1;
            log.info("Adding coin to user!");
            pstmt = con.prepareStatement("SELECT * FROM USER WHERE USERNAME=?");
            pstmt.setString(1, user.getUsername());
            
            rs = pstmt.executeQuery();

            if(rs.next()) {
                currentCoins = rs.getInt("COINS");
            }
                        
            pstmt = con.prepareStatement("UPDATE user set coins=? WHERE username=?");
            pstmt.setInt(1, currentCoins+1);
            pstmt.setString(2, user.getUsername());

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
        	int currentCoins = -1;
            log.info("Adding coin to user!");
            pstmt = con.prepareStatement("SELECT * FROM USER WHERE USERNAME=?");
            pstmt.setString(1, user.getUsername());
            
            rs = pstmt.executeQuery();

            if(rs.next()) {
                currentCoins = rs.getInt("COINS");
            }
                        
            pstmt = con.prepareStatement("UPDATE user set coins=? WHERE username=?");
            pstmt.setInt(1, currentCoins-1);
            pstmt.setString(2, user.getUsername());

            pstmt.execute(); 
            
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        		
		return false;
		
		}

}
