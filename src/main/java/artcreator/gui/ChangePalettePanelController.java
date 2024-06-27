package artcreator.gui;

import artcreator.creator.port.Creator;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ChangePalettePanelController extends Controller{

    public ChangePalettePanelController(CreatorFrame view, Subject subject, Creator model) {
        super(view, subject, model);
    }
    public synchronized void actionPerformed(ActionEvent e) {
        this.myView.updateColorPalette();
    }
}
