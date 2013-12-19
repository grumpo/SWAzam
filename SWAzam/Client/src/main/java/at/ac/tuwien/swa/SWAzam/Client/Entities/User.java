package at.ac.tuwien.swa.SWAzam.Client.Entities;

/**
 * Created by markus on 17.12.13.
 */
public class User {
    String username;
    String password;
    int coins;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int coins){
        this(username, password);
        this.coins = coins;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public int getCoins(){
        return this.coins;
    }

    public void setCoins(int coins){
        this.coins = coins;
    }

    @Override
    public String toString(){
        return username + ": " + password + " (" + coins + ")";
    }
}
