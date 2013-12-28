package at.ac.tuwien.swa.SWAzam.Server.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecognitionRequest {
	
	private int id;
	private String username;
	private Date date;
	private String song;
	private String peerURL;
	private boolean finished;
	private boolean success;
	
	public RecognitionRequest () {
		
	}
	

	public RecognitionRequest(int id, String username, Date date, String song, String peerURL, boolean finished, boolean success) {
		super();
		this.id = id;
		this.username = username;
		this.date = date;
		this.song = song;
		this.peerURL = peerURL;
		this.finished = finished;
		this.success = success;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public String getSearchFinished() {
		if (!finished)
			return "open";
		
		return "done";
	}
	
	public String getFoundSong() {
		if (success && finished)
			return "Song successfully identified.";
		else if (!success && finished)
			return "Song could not be identified.";
		
		return "";
	}
	
	public String getRowColor() {
	    if(success && finished)
	    	return "green";
	    if(!success && finished) 
	    	return "red";
	    if(!finished) 
	    	return "orange";
	    
	    return "";
	}

	public String getFormattedDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
	
	public String getFormattedTime() {
        return new SimpleDateFormat("HH:MM:ss").format(date);
    }

}
