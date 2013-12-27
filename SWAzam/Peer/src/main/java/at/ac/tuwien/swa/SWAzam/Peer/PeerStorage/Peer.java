package at.ac.tuwien.swa.SWAzam.Peer.PeerStorage;

/**
 * Created by grumpo on 12/23/13.
 */
public class Peer {
    String url;
    int failure;

    public Peer(String url, int failure){
        this.url = url;
        this.failure = failure;
    }

    public String getUrl(){
        return this.url;
    }

    public int getFailure(){
        return this.failure;
    }

    public void failure(){
        this.failure--;
    }

    @Override
    public String toString(){
        return "Peer with address " + this.url + ": " + this.failure + " remaining failures before deletion!";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peer peer = (Peer) o;

        if (url != null ? !url.equals(peer.url) : peer.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
