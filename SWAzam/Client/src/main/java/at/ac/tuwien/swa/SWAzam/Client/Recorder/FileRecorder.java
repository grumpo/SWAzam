package at.ac.tuwien.swa.SWAzam.Client.Recorder;

import javax.sound.sampled.*;
import java.io.*;

/**
 * Created by markus on 17.12.13.
 */
public class FileRecorder implements IRecorder{
    File audioFile;

    public FileRecorder(String filename){
        this.audioFile = new File(filename);
    }

    public FileRecorder(File audioFile){
        this.audioFile = audioFile;
    }

    public AudioInputStream getStream(){
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
