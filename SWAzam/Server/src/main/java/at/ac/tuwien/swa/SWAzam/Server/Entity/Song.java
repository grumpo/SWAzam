package at.ac.tuwien.swa.SWAzam.Server.Entity;

public class Song {
	
	String artist;
	String title;
	String albumname;
	int year;
	
	public Song() {
		
	}

	
	public Song(String artist, String title, String albumname, int year) {
		super();
		this.artist = artist;
		this.title = title;
		this.albumname = albumname;
		this.year = year;
	}

	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbumname() {
		return albumname;
	}
	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}


	public String getFormatted() {
		if (!artist.equals(""))
			return artist + " - " + title + " (" + albumname + " - " + year + ")";
		
		return "";
	}

	@Override
	public String toString() {
		return "MusicPiece [artist=" + artist + ", title="
				+ title + ", albumname=" + albumname + ", year=" + year + "]";
	}

}
