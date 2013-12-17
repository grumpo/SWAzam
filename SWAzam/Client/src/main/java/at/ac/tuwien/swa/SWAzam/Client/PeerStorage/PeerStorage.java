package at.ac.tuwien.swa.SWAzam.Client.PeerStorage;

import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by markus on 17.12.13.
 */
public class PeerStorage {
    Set<Peer> peers;

    public PeerStorage(){
        this.peers = new HashSet<>() ;
    }

    public void addPeer(Peer p){
        this.peers.add(p);
    }

    public void removePeer(Peer p){
        this.peers.remove(p);
    }

    @Override
    public String toString(){
        String result = "Current enlisted peers:\n";

        for(Peer p : peers){
            result += p + "\n";
        }

        return result;
    }
}
