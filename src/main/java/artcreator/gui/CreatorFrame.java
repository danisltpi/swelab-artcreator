package artcreator.gui;

import javax.swing.*;

import artcreator.creator.CreatorFactory;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

import java.awt.*;

public class CreatorFrame extends JFrame implements Observer{

	private Creator creator = CreatorFactory.FACTORY.creator();
	private Subject subject = StateMachineFactory.FACTORY.subject();

	private Controller controller;

	// Constants for colors
	private static Color[] colorPalette = {
			Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE
			// Color.WHITE, Color.RED
	};

	private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
	private static final Color ELEMENT_COLOR = Color.GRAY;
	private static final Color FONT_COLOR = Color.WHITE;

	// GUI Components
	private JButton selectImageButton;
	private JPanel imagePlaceholder;
	private JTextField pathTextField;
	private JComboBox<String> templateModelComboBox;
	private JPanel colorPanel;
	private JButton createTemplateButton;
	private JButton exportTemplateButton;
	private JPanel previewPanel;

	public CreatorFrame() {
		// Set Look and Feel
		setLookAndFeel();

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

		// Set the background color for the main panel
		getContentPane().setBackground(BACKGROUND_COLOR);
	}

	private void setLookAndFeel() {
		UIManager.put("Button.background", ELEMENT_COLOR);
		UIManager.put("Button.foreground", FONT_COLOR);
		UIManager.put("TextField.background", ELEMENT_COLOR);
		UIManager.put("TextField.foreground", FONT_COLOR);
		UIManager.put("ComboBox.background", ELEMENT_COLOR);
		UIManager.put("ComboBox.foreground", FONT_COLOR);
		UIManager.put("Label.foreground", FONT_COLOR);
		UIManager.put("Panel.background", BACKGROUND_COLOR);
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
		imagePlaceholder.setBackground(Color.LIGHT_GRAY);
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
		String[] templateModels = {"Rubik's Cubes", "Match Sticks"};
		templateModelComboBox = new JComboBox<>(templateModels);
		templateModelComboBox.setBounds(220, 300, 150, 30);
		add(templateModelComboBox);

		// Color Palette Colors Panel
		colorPanel = new JPanel();
		colorPanel.setBounds(220, 340, 25 * colorPalette.length, 20);
		colorPanel.setLayout(new GridLayout(1,colorPalette.length , 5, 5));

		for (Color color : colorPalette) {
			JPanel colorBox = new JPanel();
			colorBox.setBackground(color);
			colorBox.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR));
			colorPanel.add(colorBox);
		}

		add(colorPanel);

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
		previewPanel.setBackground(Color.LIGHT_GRAY);
		add(previewPanel);
	}

	@Override
	public void update(State newState) {/* modify view if necessary */}
}