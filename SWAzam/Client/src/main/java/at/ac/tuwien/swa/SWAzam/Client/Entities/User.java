package at.ac.tuwien.swa.SWAzam.Client.Entities;

/**
 * Created by markus on 17.12.13.
 */
public class User {
    String username;
    String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString(){
        return username + ": " + password;
    }
}
