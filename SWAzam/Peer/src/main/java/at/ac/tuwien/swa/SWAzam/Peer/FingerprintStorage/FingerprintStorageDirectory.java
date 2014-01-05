package at.ac.tuwien.swa.SWAzam.Peer.FingerprintStorage;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import at.ac.tuwien.swa.SWAzam.Peer.Common.AudioInformation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

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

                    return getAudioInformation(file);
                }
            }
            return null;

    }

    private AudioInformation getAudioInformation(File file) {
        AudioInformation audioInformation = new AudioInformation();
        try {
            audioInformation = getAudioInformationByID3(file);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            log.severe("Unable to read id3 tags!");
        }
        if (audioInformation.getTitle() == null) {
            audioInformation.setTitle(getTitle(file));
        }
        if (audioInformation.getArtist() == null) {
            audioInformation.setArtist("unknown");
        }

        return audioInformation;
    }

    private AudioInformation getAudioInformationByID3(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        AudioFile f = AudioFileIO.read(file);
        Tag tag = f.getTag();
        return new AudioInformation(tag.getFirst(FieldKey.TITLE), tag.getFirst(FieldKey.ARTIST));
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
