package at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector;

public class AudioInformation {
    private String title;
    private String artist;

    public AudioInformation() {
    }

    public AudioInformation(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
