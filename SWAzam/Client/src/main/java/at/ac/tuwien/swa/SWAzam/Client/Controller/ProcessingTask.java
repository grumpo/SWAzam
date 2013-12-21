package at.ac.tuwien.swa.SWAzam.Client.Controller;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Client2PeerConnector.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.Entities.User;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetriever;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Created by markus on 20.12.13.
 */
public class ProcessingTask extends SwingWorker<FingerprintResult, Object> {
    private final static Logger log = Logger.getLogger(ProcessingTask.class.getName());
    MetaDataRetriever retriever;
    Fingerprint fp;
    User user;
    FingerprintResult fpr;

    public ProcessingTask(MetaDataRetriever retriever, Fingerprint fp, User user){
        this.retriever = retriever;
        this.fp = fp;
        this.user = user;
    }

    @Override
    protected FingerprintResult doInBackground() throws Exception {
        setProgress(0);
        fpr = retriever.getFingerprintResult(fp, user);
        setProgress(100);
        return fpr;
    }

    public FingerprintResult getResult(){
        return fpr;
    }
}
