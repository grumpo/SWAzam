package at.ac.tuwien.swa.SWAzam.Client.Entities;

/**
 * Created by markus on 17.12.13.
 */
public class Peer {
    String ip;
    int port;

    public Peer(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }

    @Override
    public String toString(){
        return "Peer at " + this.ip + ":" + this.port;
    }
}
