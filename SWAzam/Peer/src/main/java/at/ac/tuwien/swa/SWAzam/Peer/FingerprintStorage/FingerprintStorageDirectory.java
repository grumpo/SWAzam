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
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by grumpo on 12/23/13.
 */
public class FingerprintStorageDirectory implements FingerprintStorage{
    private final static Logger log = Logger.getLogger(FingerprintStorageDirectory.class.getName());

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
        try {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.toLowerCase().endsWith(".mp3");
                }
            };
            for (File file : storage.listFiles(filter)) {
                AudioInputStream stream = getAudioStream(file);
                Fingerprint storedFingerprint = getFingerprint(stream);
                stream.close();
                double matchValue1 = storedFingerprint.match(fingerprint);
                double matchValue2 = fingerprint.match(storedFingerprint);
                boolean match = matchValue1 >= 0 || matchValue2 >= 0;
                log.info("MatchValues: " + matchValue1 + " " + matchValue2);
                if(match){
                    log.info("FingerprintStorage contains a matching track!");
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
            for (File file : storage.listFiles()) {
                Fingerprint storedFingerprint;
                try {
                    storedFingerprint = getFingerprint(getAudioStream(file));
                    getAudioStream(file).close();
                } catch (UnsupportedAudioFileException | IOException e) {
                    log.severe("Unable to load mp3: " + e.getMessage());
                    continue;
                }

                double matchValue1 = storedFingerprint.match(fingerprint);
                double matchValue2 = fingerprint.match(storedFingerprint);
                boolean match = matchValue1 >= 0 || matchValue2 >= 0;
                log.info("MatchValues: " + matchValue1 + " " + matchValue2);
                if(match){
                    log.info("Found matching track " + getTitle(file));
                    return new AudioInformation(getTitle(file), "TODO-Artist"); // TODO: extract ID3tags
                }
            }
            return null;

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
