package at.ac.tuwien.swa.SWAzam.Client.PeerStorage;

import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;
import at.ac.tuwien.swa.SWAzam.Client.Entities.Peer;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class PeerStorage {
    private final static Logger log = Logger.getLogger(PeerStorage.class.getName());
    static final int MAX_FAILURE = 5;

    Set<Peer> peers;
    Connection con;

    public PeerStorage(Connection con){
        this.peers = new HashSet<Peer>();
        this.con = con;

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
