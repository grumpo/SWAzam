package at.ac.tuwien.swa.SWAzam.Client.Util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by markus on 18.12.13.
 */
public class MusicFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String extension = f.getName().substring(f.getName().lastIndexOf('.') + 1).toLowerCase();

        if(extension.equals("mp3") || extension.equals("m4a") || extension.equals("wma") ||
                extension.equals("wav") || extension.equals("ogg")){
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Music Files";
    }
}
