package at.ac.tuwien.swa.SWAzam.Server.Common;

import java.util.List;
import java.util.UUID;

public class FingerprintResult {
    private String result;
    private List<String> hops;
    private String requestID;

    public FingerprintResult() {
    }

    public FingerprintResult(String result, List<String> hops, UUID requestID) {
        this.result = result;
        this.hops = hops;
        this.requestID = requestID.toString();
    }

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

    public UUID getRequestID() {
        return UUID.fromString(requestID);
    }
    
    public String getRequestIDString() {
        return requestID;
    }


    public void setRequestID(UUID requestID) {
        this.requestID = requestID.toString();
    }
}
