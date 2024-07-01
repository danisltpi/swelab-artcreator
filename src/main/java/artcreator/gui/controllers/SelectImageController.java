package artcreator.gui.controllers;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import artcreator.creator.port.Creator;
import artcreator.gui.CreatorFrame;
import artcreator.gui.filefilters.ImageFileFilter;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class SelectImageController extends Controller {

    public SelectImageController(CreatorFrame view, Subject subject, Creator model) {
        super(view, subject, model);
        System.out.println("SelectImageController");
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ImageFileFilter());
			int response = fileChooser.showOpenDialog(this.myView);
            System.out.println("SelectImageController: actionPerformed");
            File file;
			if (response == JFileChooser.APPROVE_OPTION) {
                this.myModel.setState(State.S.CREATE_TEMPLATE);
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("Selected file: " + file.getAbsolutePath());
                this.myModel.importImage(file);
                this.myView.updateOriginalPicturePreview();
                this.myModel.createPreviewWithTemplate(this.myView.getTemplateType());
                this.myView.setPathTextField(file.getAbsolutePath());
                this.myView.update(State.S.START_PROCESS);
			}

    }
}
