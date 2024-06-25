package artcreator.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        } else {
            String filename = file.getName().toLowerCase();
            return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png");
        }
    }

    @Override
    public String getDescription() {
        return "Image Files (*.jpg, *.jpeg, *.png)";
    }
}
