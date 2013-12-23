package at.ac.tuwien.swa.SWAzam.Peer.PeerStorage;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by grumpo on 12/23/13.
 */
public class PeerStorageImpl implements PeerStorage {
    private final static Logger log = Logger.getLogger(PeerStorageImpl.class.getName());
    static final int MAX_FAILURE = 5;

    Set<Peer> peers;
    Connection con;

    public PeerStorageImpl(){
        this.peers = new HashSet<Peer>();
        //TODO: this should be injected
        try {
            this.con = DriverManager.getConnection("jdbc:hsqldb:file:" + this.getClass().getResource("/Database").getFile() + "/localdb", "SA", "");
        } catch (SQLException e) {
            log.warning("PeerStorage could not connect to database. Forwarding of requests will not be done.");
        }

        readPeersFromDatabase();
    }

    private void readPeersFromDatabase() {
        PreparedStatement pstmt;
        ResultSet rs;

        peers.clear();

        try{
            log.info("Fetching stored peers from Database!");
            pstmt = con.prepareStatement("SELECT URL, FAILURE FROM PEERS WHERE FAILURE < ?");
            pstmt.setInt(1, MAX_FAILURE);

            rs = pstmt.executeQuery();

            while(rs.next()){
                peers.add(new Peer(rs.getString("URL"), rs.getInt("FAILURE")));
            }

            System.out.println(peers.size());
        }
        catch(SQLException e){
            //TODO: Remove StackTrace
            e.printStackTrace();
        }
    }

    public void updatePeers(List<String> peerList){
        PreparedStatement pstmt;
        ResultSet rs;
        String statement = "DELETE FROM PEERS WHERE LCASE(URL) IN (";

        log.info("Updating stored peers!");

        for(int i = 0; i < peerList.size(); i++){
            statement += "?";

            if(i + 1 < peerList.size()){
                statement += ", ";
            }
        }

        statement += ")";

        try{
            pstmt = con.prepareStatement(statement);

            for(int i = 0; i < peerList.size(); i++){
                pstmt.setString(i + 1, peerList.get(i).toLowerCase());
            }

            log.info("Remove all used peers from Database!");
            pstmt.execute();

            for(String s : peerList){
                pstmt = con.prepareStatement("INSERT INTO PEERS (URL, FAILURE) VALUES (?, ?)");
                pstmt.setString(1, s);
                pstmt.setInt(2, 0);

                pstmt.addBatch();
            }

            log.info("Insert all used peers into Database with failure 0!");
            pstmt.executeBatch();

            readPeersFromDatabase();
        }
        catch(SQLException e){
            //TODO: Remove StackTrace
            e.printStackTrace();
        }
    }

    public void addPeer(Peer p){
        this.peers.add(p);
    }

    public void removePeer(Peer p){
        this.peers.remove(p);
    }

    public Set<Peer> getPeers(){
        return this.peers;
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