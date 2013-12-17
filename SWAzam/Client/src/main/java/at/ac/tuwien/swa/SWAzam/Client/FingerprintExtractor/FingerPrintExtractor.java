package at.ac.tuwien.swa.SWAzam.Client.FingerprintExtractor;

import javax.sound.sampled.AudioInputStream;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import ac.at.tuwien.infosys.swa.audio.util.Converter;

import java.io.IOException;

/**
 * Created by markus on 17.12.13.
 */
public class FingerPrintExtractor {
    public static Fingerprint extractFingerPrint(AudioInputStream inStream){
        Converter conv = new Converter(inStream);
        Converter converted = conv.toPCM();

        try {
            return FingerprintSystem.fingerprint(converted.getAudioInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
