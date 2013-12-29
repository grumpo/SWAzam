package at.ac.tuwien.swa.SWAzam.Peer.RequestHandler;

import at.ac.tuwien.swa.SWAzam.Peer.Common.FingerprintResult;

import java.util.concurrent.ArrayBlockingQueue;

public class ResultListener {
    private ArrayBlockingQueue<FingerprintResult> resultQueue = new ArrayBlockingQueue<>(1);

    public void setResult(FingerprintResult result) {
        resultQueue.add(result);
    }

    public FingerprintResult waitForResult() throws InterruptedException {
        return resultQueue.take();
    }
}
