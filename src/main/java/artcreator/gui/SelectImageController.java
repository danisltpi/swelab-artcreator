package artcreator.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

import artcreator.creator.port.Creator;
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
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {		
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("Selected file: " + file.getAbsolutePath());
			}
    }
}
