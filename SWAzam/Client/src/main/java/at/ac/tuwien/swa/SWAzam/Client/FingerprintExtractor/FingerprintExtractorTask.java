package at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Controller.Controller;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;

/**
 * Created by markus on 20.12.13.
 */
public class FingerprintExtractorTask extends SwingWorker<Fingerprint, Object> {
    AudioInputStream inputStream;
    Fingerprint fp;

    public FingerprintExtractorTask(AudioInputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    protected Fingerprint doInBackground() throws Exception {
        setProgress(0);
        fp = FingerprintExtractor.extractFingerPrint(inputStream);
        setProgress(100);
        return fp;
    }

    public Fingerprint getResult(){
        return fp;
    }
}
