package artcreator.gui.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import artcreator.creator.port.Creator;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.port.Subject;

public class CreateTemplateController extends Controller{
    public CreateTemplateController(CreatorFrame view, Subject subject, Creator model) {
        super(view, subject, model);
        System.out.println("CreateTemplateController");
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        System.out.println("TemplateCreation successfully started");
        System.out.println("Ouh noo, there is no implementation for that, how sad!");
        JOptionPane.showMessageDialog(this.myView, "This hasn't actually been implemented yet. Try again in a few months, maybe...");

    }
}
