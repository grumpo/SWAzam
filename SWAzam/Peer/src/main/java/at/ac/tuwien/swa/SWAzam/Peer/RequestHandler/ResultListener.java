package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;

import java.util.concurrent.ArrayBlockingQueue;

public class ResultListener {
    private ArrayBlockingQueue<FingerprintResult> resultQueue = new ArrayBlockingQueue<>(1);
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResult(FingerprintResult result) {
        resultQueue.add(result);
    }

    public FingerprintResult waitForResult() throws InterruptedException {
        return resultQueue.take();
    }
}
