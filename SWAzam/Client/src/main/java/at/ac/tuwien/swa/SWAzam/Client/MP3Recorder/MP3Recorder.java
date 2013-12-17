package at.ac.tuwien.swa.SWAzam.Client.MP3Recorder;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

/**
 * Created by markus on 17.12.13.
 */
public class MP3Recorder implements IRecorder{
    String filename;

    public MP3Recorder(String filename){
        this.filename = filename;
    }

    public AudioInputStream getStream(){
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
