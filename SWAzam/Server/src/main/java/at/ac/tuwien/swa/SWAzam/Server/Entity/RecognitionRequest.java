package at.ac.tuwien.swa.SWAzam.Server.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecognitionRequest {
	
	private int id;
	private String request_id;
	private String username;
	private Date date;
	private String song;
	private String peerURL;
	private String result;
	
	public RecognitionRequest () {
		
	}
	

	public RecognitionRequest(int id, String request_id, String username, Date date, String song, String peerURL, String result) {
		super();
		this.id = id;
		this.request_id = request_id;
		this.username = username;
		this.date = date;
		this.song = song;
		this.peerURL = peerURL;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getRequest_id() {
		return request_id;
	}
	
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getPeerURL() {
		return peerURL;
	}

	public void setPeerURL(String peerURL) {
		this.peerURL = peerURL;
	}	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRowColor() {
	    if(result.equals("Song successfully identified."))
	    	return "green";
	    if(result.equals("Song could not be identified.")) 
	    	return "red";
	    
	    return "orange";

	}

	public String getFormattedDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
	
	public String getFormattedTime() {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

}
