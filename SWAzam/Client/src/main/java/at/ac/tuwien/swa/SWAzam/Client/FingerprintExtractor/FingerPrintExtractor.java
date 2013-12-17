package at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor;

import javax.sound.sampled.AudioInputStream;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import ac.at.tuwien.infosys.swa.audio.util.Converter;

import java.io.IOException;

/**
 * Created by markus on 17.12.13.
 */
public class FingerprintExtractor {
    public static Fingerprint extractFingerPrint(AudioInputStream inStream){
        if(inStream != null){
            try {
                return FingerprintSystem.fingerprint(inStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                System.out.println("Too short!");
            } catch (NegativeArraySizeException e){
                System.out.println("Too short: empty stream!");
            }
        }
        else{
            System.out.println("Empty stream!");
        }

        return null;
    }
}
