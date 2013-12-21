package at.ac.tuwien.swa.SWAzam.Client.Entities;

import java.util.Date;

/**
 * Created by markus on 21.12.13.
 */
public class StoredFingerprint {
    String artist;
    String songtitle;
    Date timestamp;

    public StoredFingerprint(String artist, String songtitle, Date timestamp){
        this.artist = artist;
        this.songtitle = songtitle;
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return timestamp + ": " + songtitle + " from " + artist;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongtitle() {
        return songtitle;
    }
}
