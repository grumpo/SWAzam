package at.ac.tuwien.swa.SWAzam.Peer.PeerStorage;

import java.util.List;
import java.util.Set;

/**
 * Created by grumpo on 12/23/13.
 */
public interface PeerStorage {

    public void updatePeers(List<String> peerList);
    public void addPeer(Peer p);
    public void removePeer(Peer p);
    public Set<Peer> getPeers();
}
