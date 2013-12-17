package at.ac.tuwien.swa.SWAzam.Client.MP3Recorder;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by markus on 17.12.13.
 */
public class MicRecorder implements Runnable {
    private AudioInputStream result;
    boolean stopped;

    @Override
    public void run() {
        stopped = false;

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, (16/8) * 2, 44100.0f, true);
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if(!AudioSystem.isLineSupported(info)){
            System.err.println("Error with line!");
        }
        else{
            try {
                line = (TargetDataLine)AudioSystem.getLine(info);
                line.open(format, line.getBufferSize());

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data = new byte[(line.getBufferSize()/8) * format.getFrameSize()];

                line.start();

                int numBytesRead;

                System.err.println("Starting Recording");

                while((numBytesRead = line.read(data, 0, (line.getBufferSize()/8) * format.getFrameSize())) != -1 && !stopped){
                    out.write(data, 0, numBytesRead);
                    System.err.print(".");
                }

                System.err.println("Ended Recording");

                line.stop();
                line.close();

                ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
                result = new AudioInputStream(bais, format, out.toByteArray().length / format.getFrameSize());
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public AudioInputStream getResult(){
        return this.result;
    }

    public void setStop(boolean stop){
        this.stopped = stop;
    }
}
