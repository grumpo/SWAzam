package at.ac.tuwien.swa.SWAzam.Client.Controller;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.SWAzam.Client.Entities.FingerprintResult;
import at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor.FingerprintExtractor;
import at.ac.tuwien.swa.SWAzam.Client.GUIView.MainFrame;
import at.ac.tuwien.swa.SWAzam.Client.MetaDataRetriever.MetaDataRetriever;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.FileRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.IRecorder;
import at.ac.tuwien.swa.SWAzam.Client.Recorder.MicRecorder;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by markus on 17.12.13.
 */
public class Controller {
    private final static Logger log = Logger.getLogger(Controller.class.getName());

    MainFrame mframe;
    IRecorder recorder;
    MetaDataRetriever retriever;

    public Controller(){
        retriever = new MetaDataRetriever();
        mframe = new MainFrame(this);
    }

    public void loadMp3(File audioFile){
        recorder = new FileRecorder(audioFile);

        processFingerprint(FingerprintExtractor.extractFingerPrint(recorder.getStream()));
    }

    public void startRecordingFromMic(){
        recorder = new MicRecorder();
        ((MicRecorder)recorder).startRecording();
    }

    public void stopRecordingFromMic(){
        if(recorder != null){
            ((MicRecorder)recorder).stopRecording();
        }

        processFingerprint(FingerprintExtractor.extractFingerPrint(recorder.getStream()));
    }

    private void processFingerprint(Fingerprint fp){
        FingerprintResult fpr;

        if(fp != null){
            fpr = retriever.getFingerprintResult(fp, mframe.getUser());
        }
        else{
            log.log(Level.WARNING, "Fingerprint is null and cannot be processed!");
        }
    }
}
