package artcreator.gui.controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;

import artcreator.creator.port.Creator;
import artcreator.gui.CreatorFrame;
import artcreator.gui.filefilters.FolderFilter;
import artcreator.statemachine.port.Subject;

public class ExportTemplateController extends Controller{
    public ExportTemplateController(CreatorFrame view, Subject subject, Creator model) {
        super(view, subject, model);
        System.out.println("ExportTemplateController");
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FolderFilter());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int response = fileChooser.showOpenDialog(this.myView);
        System.out.println("SelectImageController: actionPerformed");
        File folder;
        if (response == JFileChooser.APPROVE_OPTION) {
            folder = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println("Selected folder: " + folder.getAbsolutePath());
            Path newPath = folder.toPath().resolve("preview.png");
            this.myModel.saveTemplate(newPath.toString());
        }
    }
}
