package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by grumpo on 12/23/13.
 */
public class FingerprintStorageDirectory implements FingerprintStorage{

    private File storage = null;

    @Inject
    public FingerprintStorageDirectory(@Assisted String direcoryPath) {
        File storage = new File(direcoryPath);
        if(!storage.exists() || !storage.isDirectory())
            throw new IllegalArgumentException("The specified directory does not exist.");

        this.storage = storage;
    }

    @Override
    public Boolean contains(Fingerprint fingerprint) {
        try{
            for (File file : storage.listFiles()) {
                AudioInputStream stream = getAudioStream(file);
                Fingerprint storedFingerprint = getFingerprint(stream);
                if(storedFingerprint.equals(fingerprint)){
                    return true;
                }
            }
            return false;
        }catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AudioInformation getAudioInformationOf(Fingerprint fingerprint) {
        try{
            for (File file : storage.listFiles()) {
                AudioInputStream stream = getAudioStream(file);
                Fingerprint storedFingerprint = getFingerprint(stream);
                if(storedFingerprint.equals(fingerprint)){
                    return new AudioInformation(getTitle(file), "TODO-Artist"); // TODO: extract ID3tags
                }
            }
            return null;
        }catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Fingerprint getFingerprint(AudioInputStream stream) throws IOException {
        return FingerprintSystem.fingerprint(stream);
    }

    private AudioInputStream getAudioStream(File file) throws IOException, UnsupportedAudioFileException {
        return AudioSystem.getAudioInputStream(file);
    }

    private String getTitle(File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }
}
