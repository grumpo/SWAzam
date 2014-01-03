package at.ac.tuwien.swa.SWAzam.Peer.Common;

import java.util.List;
import java.util.UUID;

public class FingerprintResult {
    private AudioInformation result;
    private List<String> hops;
    private String requestID;

    public FingerprintResult() {
    }

    public FingerprintResult(AudioInformation result, List<String> hops, UUID requestID) {
        this.result = result;
        this.hops = hops;
        this.requestID = requestID.toString();
    }

    public AudioInformation getResult() {
        return result;
    }

    public void setResult(AudioInformation result) {
        this.result = result;
    }

    public List<String> getHops() {
        return hops;
    }

    public void setHops(List<String> hops) {
        this.hops = hops;
    }

    public UUID getRequestID() {
        return UUID.fromString(requestID);
    }

    public void setRequestID(UUID requestID) {
        this.requestID = requestID.toString();
    }
}
