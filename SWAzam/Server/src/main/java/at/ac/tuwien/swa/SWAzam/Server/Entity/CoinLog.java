package at.ac.tuwien.swa.SWAzam.Server.Entity;

import java.util.Date;

public class CoinLog {

	private int id;
	private String username;
	private int request_id;
	private int coins_old;
	private int coins_new;
	private boolean increased;
	private Date date;
	
	public CoinLog() {
		
	}
	
	public CoinLog(int id, String username, int request_id, int coins_old,
			int coins_new, boolean increased, Date date) {
		super();
		this.id = id;
		this.username = username;
		this.request_id = request_id;
		this.coins_old = coins_old;
		this.coins_new = coins_new;
		this.increased = increased;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public int getCoins_old() {
		return coins_old;
	}
	public void setCoins_old(int coins_old) {
		this.coins_old = coins_old;
	}
	public int getCoins_new() {
		return coins_new;
	}
	public void setCoins_new(int coins_new) {
		this.coins_new = coins_new;
	}
	public boolean isIncreased() {
		return increased;
	}
	public void setIncreased(boolean increased) {
		this.increased = increased;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "CoinLog [id=" + id + ", username=" + username + ", request_id="
				+ request_id + ", coins_old=" + coins_old + ", coins_new="
				+ coins_new + ", increased=" + increased + ", date=" + date
				+ "]";
	}

}