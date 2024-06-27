package artcreator.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import artcreator.creator.CreatorFactory;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer {

	private Creator creator = CreatorFactory.FACTORY.creator();
	private Subject subject = StateMachineFactory.FACTORY.subject();

	private Controller controller;
	private SelectImageController selectImageController;

	private CreateTemplateController createTemplateController;

	private ExportTemplateController exportTemplateController;
	private ChangePalettePanelController changePalettePanelController;

	// Constants for colors
	private static Color[] rubiksPalette = {
			Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE
	};
	private static Color[] matchStickPalette = {
			Color.RED, Color.orange
	};



	// GUI Components
	private JButton selectImageButton;
	private JPanel imagePlaceholder;
	private JTextField pathTextField;
	private JComboBox<String> templateModelComboBox;
	private JPanel colorPalettePanel;
	private JButton createTemplateButton;
	private JButton exportTemplateButton;
	private JLabel previewPanel;

	private String templateType;

	public CreatorFrame() {

		// Initialize the JFrame
		setTitle("3D Art Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(830, 600);
		setResizable(false);
		setLayout(null);

		this.subject.attach(this);
		this.controller = new Controller(this, subject, creator);
		this.selectImageController = new SelectImageController(this, subject, creator);
		this.createTemplateController = new CreateTemplateController(this, subject, creator);
		this.exportTemplateController = new ExportTemplateController(this, subject, creator);
		this.changePalettePanelController = new ChangePalettePanelController(this, subject, creator);
		this.templateType = "rubiks";

		// Initialize components
		initializeComponents();

	}

	private void initializeComponents() {
		// Select Image Button
		selectImageButton = new JButton("Select Image");
		selectImageButton.setBounds(220, 30, 150, 30);
		selectImageButton.addActionListener(this.selectImageController);
		add(selectImageButton);

		// Image Placeholder
		imagePlaceholder = new JPanel();
		imagePlaceholder.setBounds(50, 30, 150, 150);
		imagePlaceholder.setBackground(new Color(49, 49, 49, 255));
		add(imagePlaceholder);

		// Path TextField
		pathTextField = new JTextField("C:/ProgramData/3DArtCreator/Examples/ExampleImage.png");
		pathTextField.setBounds(50, 200, 320, 30);
		pathTextField.setEditable(false);
		add(pathTextField);

		// Template Model Label
		JLabel templateModelLabel = new JLabel("Template Model:");
		templateModelLabel.setBounds(50, 300, 150, 30);
		add(templateModelLabel);

		// Template Model ComboBox
		String[] templateModels = {"Rubiks Cubes", "Match Sticks"};
		templateModelComboBox = new JComboBox<>(templateModels);
		templateModelComboBox.setBounds(220, 300, 150, 30);
		templateModelComboBox.addActionListener(changePalettePanelController);
		add(templateModelComboBox);

		// Color Palette Colors Panel
		colorPalettePanel = new JPanel();
		updatePalettePanel(0);

		add(colorPalettePanel);

		// Create Template Button
		createTemplateButton = new JButton("Create Template");
		createTemplateButton.setBounds(50, 450, 150, 30);
		createTemplateButton.setEnabled(false); // Initially disabled
		createTemplateButton.addActionListener(this.createTemplateController);
		add(createTemplateButton);

		// Export Template Button
		exportTemplateButton = new JButton("Export Preview");
		exportTemplateButton.setBounds(220, 450, 150, 30);
		exportTemplateButton.setEnabled(false); // Initially disabled
		exportTemplateButton.addActionListener(this.exportTemplateController);
		add(exportTemplateButton);

		// Preview Label
		JLabel previewLabel = new JLabel("Preview:");
		previewLabel.setBounds(450, 30, 100, 30);
		add(previewLabel);

		// Preview
		previewPanel = new JLabel();
		previewPanel.setBounds(450, 60, 320, 320);
		previewPanel.setBackground(new Color(49, 49, 49, 255));
		add(previewPanel);
	}
	private void updatePalettePanel(int paletteIndex){
		Color[] actualPalette;
		if (paletteIndex == 0){
			actualPalette = rubiksPalette;
			this.templateType = "rubiks";
		}else{
			actualPalette = matchStickPalette;
			this.templateType = "matchsticks";
		}
		colorPalettePanel.removeAll();
		colorPalettePanel.setBounds(220, 340, 25 * actualPalette.length, 20);
		colorPalettePanel.setLayout(new GridLayout(1, actualPalette.length, 5, 5));

		for (Color color : actualPalette) {
			JPanel colorBox = new JPanel();
			colorBox.setBackground(color);
			colorPalettePanel.add(colorBox);
		}
		this.validate();
		this.repaint();
	}

	public void setPathTextField(String newPath) {
		pathTextField.setText(newPath);
	}

	public void updatePreview() {
		BufferedImage templateImage = this.creator.getTemplateImage();
		if (templateImage != null) {
			int templateHeight = templateImage.getHeight();
			int templateWidth = templateImage.getWidth();
			int longestSide = Math.max(templateWidth, templateHeight);
			int previewSize = previewPanel.getHeight();
			double factor = previewSize / longestSide;
			int newWidth = (int) (templateWidth * factor);
			int newHeight = (int) (templateHeight * factor);
			Image scaledImage = templateImage.getScaledInstance(newWidth, newHeight, 2);
			previewPanel.setIcon(new ImageIcon(scaledImage));
			previewPanel.setLocation(460, 40);
		}
	}
	private void enableOptionalButtons(){
		this.createTemplateButton.setEnabled(true);
		this.exportTemplateButton.setEnabled(true);
	}

	public void updateColorPalette(){
		int selected = this.templateModelComboBox.getSelectedIndex();
		updatePalettePanel(selected);
	}

	public String getTemplateType(){
		return this.templateType;
	}
	@Override
	public void update(State newState) {
		updatePreview();
		if (newState == State.S.TEMPLATE_CREATED) {
			enableOptionalButtons();
		}
	}
}