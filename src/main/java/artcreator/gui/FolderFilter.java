package artcreator.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FolderFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Only Directories";
    }
}
