package artcreator.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import artcreator.creator.CreatorFactory;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer{

	private Creator creator = CreatorFactory.FACTORY.creator();
	private Subject subject = StateMachineFactory.FACTORY.subject();

	private Controller controller;

	// Constants for colors
	private static Color[] colorPalette = {
			Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE
	};


	// GUI Components
	private JButton selectImageButton;
	private JPanel imagePlaceholder;
	private JTextField pathTextField;
	private JComboBox<String> templateModelComboBox;
	private JPanel colorPalettePanel;
	private JButton createTemplateButton;
	private JButton exportTemplateButton;
	private JPanel previewPanel;

	public CreatorFrame() {

		// Initialize the JFrame
		setTitle("3D Art Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(830, 600);
		setResizable(false);
		setLayout(null);

		this.subject.attach(this);
		this.controller = new Controller(this, subject, creator);

		// Initialize components
		initializeComponents();

	}

	private void initializeComponents() {
		// Select Image Button
		selectImageButton = new JButton("Select Image");
		selectImageButton.setBounds(220, 30, 150, 30);
		selectImageButton.addActionListener(this.controller);
		add(selectImageButton);

		// Image Placeholder
		imagePlaceholder = new JPanel();
		imagePlaceholder.setBounds(50, 30, 150, 150);
		imagePlaceholder.setBackground(new Color(49,49,49,255));
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
		add(templateModelComboBox);

		// Color Palette Colors Panel
		colorPalettePanel = new JPanel();
		colorPalettePanel.setBounds(220, 340, 25 * colorPalette.length, 20);
		colorPalettePanel.setLayout(new GridLayout(1,colorPalette.length , 5, 5));

		for (Color color : colorPalette) {
			JPanel colorBox = new JPanel();
			colorBox.setBackground(color);
			colorPalettePanel.add(colorBox);
		}

		add(colorPalettePanel);

		// Create Template Button
		createTemplateButton = new JButton("Create Template");
		createTemplateButton.setBounds(50, 450, 150, 30);
		createTemplateButton.setEnabled(false); // Initially disabled
		add(createTemplateButton);

		// Export Template Button
		exportTemplateButton = new JButton("Export Template");
		exportTemplateButton.setBounds(220, 450, 150, 30);
		exportTemplateButton.setEnabled(false); // Initially disabled
		add(exportTemplateButton);

		// Preview Label
		JLabel previewLabel = new JLabel("Preview:");
		previewLabel.setBounds(450, 30, 100, 30);
		add(previewLabel);

		// Preview Panel
		previewPanel = new JPanel();
		previewPanel.setBounds(450, 70, 320, 320);
		previewPanel.setBackground(new Color(49,49,49,255));
		add(previewPanel);
	}

	@Override
	public void update(State newState) {/* modify view if necessary */}
}