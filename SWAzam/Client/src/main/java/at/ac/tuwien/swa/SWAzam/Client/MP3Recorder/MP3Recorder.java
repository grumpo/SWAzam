package at.ac.tuwien.swa.SWAzam.Client.MP3Recorder;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

/**
 * Created by markus on 17.12.13.
 */
public class MP3Recorder {
    Thread recorder;
    MicRecorder micRecorder;

    public MP3Recorder(){
    }

    public void startRecordingFromMic(){
        micRecorder = new MicRecorder();
        recorder = new Thread(micRecorder);
        recorder.start();
    }

    public void stopRecordingFromMic(){
        if(micRecorder != null){
            micRecorder.setStop(true);

            try {
                recorder.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public AudioInputStream getMp3FromMic(){
        if(micRecorder != null){
            return micRecorder.getResult();
        }

        return null;
    }

    public AudioInputStream getMp3FromFile(String filename){
        File audioFile = new File(filename);

        try {
            return AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
