package artcreator.gui.controllers;

import java.awt.event.ActionEvent;

import artcreator.creator.port.Creator;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.port.Subject;

public class ChangePalettePanelController extends Controller{

    public ChangePalettePanelController(CreatorFrame view, Subject subject, Creator model) {
        super(view, subject, model);
    }
    public synchronized void actionPerformed(ActionEvent e) {
        this.myView.updateColorPalette();
        this.myModel.createPreviewWithTemplate(this.myView.getTemplateType());
    }
}
