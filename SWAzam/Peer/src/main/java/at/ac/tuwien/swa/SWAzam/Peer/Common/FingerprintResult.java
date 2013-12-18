package at.ac.tuwien.swa.SWAzam.Peer.Common;

import java.util.List;

public class FingerprintResult {
    private String result;
    private List<String> hops;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getHops() {
        return hops;
    }

    public void setHops(List<String> hops) {
        this.hops = hops;
    }
}
