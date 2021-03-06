package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import model.chromosome.exception.IllegalChromosomeException;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.MainWindow.Worker1;

public class SettingsPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private JPanel settings;
 	private JPanel buttonPanel;
 	JButton runButton;
 	JButton resetButton;
 	private StatusBarPanel status;
 	Worker1 worker;
 	
 	JTextField populationText;
 	JTextField generationText;
 	JTextField heightText;
 	JSlider crossoverSlider;
 	JSlider mutationSlider;
 	JSlider elitismSlider;
 	JPanel initialization;
 	JComboBox<String> initializationBox;
 	JPanel selection;
 	JComboBox<String> selectionBox;
 	JPanel crossoverMethodPanel;
 	JComboBox<String> crossoverBox;
 	JPanel tournamentGroups;
 	JTextField tournamentGroupsText;
 	JPanel mutationMethodPanel;
 	JComboBox<String> mutationBox;
 	JCheckBox contentBasedTerminationCheck;
 	JCheckBox rangeParametersCheck;
 	ButtonGroup bg;
 	
 	String populationTextDefault;
	String generationTextDefault;
	String heightTextDefault;
	String tournamentGroupsTextDefault;
	int crossoverSliderDefault;
	int mutationSliderDefault;
	int elitismSliderDefault;
	Object initializationBoxDefault;
	Object selectionBoxDefault;
	Object crossoverBoxDefault;
	Object mutationBoxDefault;
	boolean contentBasedTerminationCheckDefault;
	
	JTextField pomin, pomax, postep, gomin, gomax, gostep, comin, comax, costep, momin, momax, mostep, eomin, eomax, eostep;
	JRadioButton rangePopulationRadioButton, rangeGenerationRadioButton, rangeCrossRadioButton, rangeMutationRadioButton, rangeElitismRadioButton;
	Border defaultborder;

	public SettingsPanel(Controller ctrl, StatusBarPanel status, Worker1 worker) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.status = status;
		this.worker = worker;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder("Settings"));
		
		initSettings();
		this.add(settings, BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		runButton = new JButton("Run");
		runButton.setMnemonic(KeyEvent.VK_L);
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctrl.setPopulation(Integer.parseInt(populationText.getText()));
					ctrl.setGenerations(Integer.parseInt(generationText.getText()));
					ctrl.setHeight(Integer.parseInt(heightText.getText()));
					ctrl.setCrossoverPercentage(crossoverSlider.getValue());
					ctrl.setMutationPercentage(mutationSlider.getValue());
					ctrl.setElitismPercentage(elitismSlider.getValue());
					ctrl.setInitializationStrategy((String) initializationBox.getSelectedItem());
					ctrl.setSelectionParameter(tournamentGroupsText.getText());
					ctrl.setSelectionStrategy((String) selectionBox.getSelectedItem());
					ctrl.setCrossoverStrategy((String) crossoverBox.getSelectedItem());
					ctrl.setMutationStrategy((String) mutationBox.getSelectedItem());
					ctrl.setContentBasedTermination(contentBasedTerminationCheck.isSelected());
					ctrl.setRangeParameters(rangeParametersCheck.isSelected());
					if(rangeParametersCheck.isSelected())
						setRanges();
					
					worker = new Worker1();
					worker.execute();
				} catch(IllegalChromosomeException ex) {
					JOptionPane.showMessageDialog(null,
							ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					for (Component cmp : buttonPanel.getComponents()) {
						cmp.setEnabled(true);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null,
							ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonPanel.add(runButton, BorderLayout.CENTER);
		resetButton = new JButton("Reset");
		resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.setToolTipText("Set initial values");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restoreDefaults();
			}
		});
		buttonPanel.add(resetButton, BorderLayout.PAGE_END);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// maybe wrap around a JScrollPane and/or JSplitPane
		
		fillFields();
	}
	
	private void initSettings() {
		settings = new JPanel();
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		
		//---------------------------------------------
		
		JPanel population = new JPanel();
		JLabel populationLabel = new JLabel("Population Size");
		population.add(populationLabel);
		populationText = new JTextField(4);
		defaultborder = populationText.getBorder();
		populationText.setInputVerifier(new IntegerNonZeroVerifier());
		population.add(populationText);
		population.setMaximumSize(population.getPreferredSize());
		population.setMinimumSize(population.getPreferredSize());
		population.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(population);
		
		//---------------------------------------------
		
		JPanel generations = new JPanel();
		JLabel generationsLabel = new JLabel("Generations");
		generations.add(generationsLabel);
		generationText = new JTextField(4);
		generationText.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				try {
					int a = Integer.parseInt(((JTextField) input).getText());
					if (a >= 1) {
						generationText.setBorder(defaultborder);
						status.setErrors(false);
						return true;
					}
					else {
						generationText.setBorder(BorderFactory.createLineBorder(Color.red));
						status.setErrors(true);
						return false;
					}
				} catch (NumberFormatException e) {
					generationText.setBorder(BorderFactory.createLineBorder(Color.red));
					status.setErrors(true);
					return false;
				}
			}
		});
		generations.add(generationText);
		generations.setMaximumSize(generations.getPreferredSize());
		generations.setMinimumSize(generations.getPreferredSize());
		generations.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(generations);
		
		//---------------------------------------------
		
		JPanel height = new JPanel();
		JLabel heightLabel = new JLabel("Max height");
		height.add(heightLabel);
		heightText = new JTextField(4);
		heightText.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				try {
					int a = Integer.parseInt(((JTextField) input).getText());
					if (a >= 1) {
						heightText.setBorder(defaultborder);
						status.setErrors(false);
						return true;
					}
					else {
						heightText.setBorder(BorderFactory.createLineBorder(Color.red));
						status.setErrors(true);
						return false;
					}
				} catch (NumberFormatException e) {
					heightText.setBorder(BorderFactory.createLineBorder(Color.red));
					status.setErrors(true);
					return false;
				}
			}
		});
		height.add(heightText);
		height.setMaximumSize(height.getPreferredSize());
		height.setMinimumSize(height.getPreferredSize());
		height.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(height);
		
		//---------------------------------------------
		JSeparator sep1 = new JSeparator();
		sep1.setMaximumSize(new Dimension(420, 1));
		settings.add(sep1);
		//---------------------------------------------
		
		initialization = new JPanel();
		initialization.setLayout(new BoxLayout(initialization, BoxLayout.Y_AXIS));
		JPanel initializationSel = new JPanel();
		JLabel initializationLabel = new JLabel("Initialisation");
		initializationSel.add(initializationLabel);
		initializationBox = new JComboBox<String>();
		initializationSel.add(initializationBox);
		initialization.add(initializationSel);
		initialization.setMaximumSize(initialization.getPreferredSize());
		initialization.setMinimumSize(initialization.getPreferredSize());
		initialization.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(initialization);

		//---------------------------------------------
		JSeparator a = new JSeparator();
		a.setMaximumSize(new Dimension(420, 1));
		settings.add(a);
		//---------------------------------------------
		
		selection = new JPanel();
		selection.setLayout(new BoxLayout(selection, BoxLayout.Y_AXIS));
		JPanel selectionSel = new JPanel();
		JLabel selectionLabel = new JLabel("Selection");
		selectionSel.add(selectionLabel);
		selectionBox = new JComboBox<String>();
		selectionBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem() == "Tournament") {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						tournamentGroups.setVisible(true);
						selection.setMaximumSize(selection.getPreferredSize());
					}
					else if(e.getStateChange() == ItemEvent.DESELECTED) {
						tournamentGroups.setVisible(false);
						selection.setMaximumSize(selection.getPreferredSize());
					}
				}
			}
		});
		selectionSel.add(selectionBox);
		selection.add(selectionSel);
			tournamentGroups = new JPanel();
			tournamentGroups.add(new JLabel("Group size"));
			tournamentGroupsText = new JTextField(4);
			tournamentGroups.add(tournamentGroupsText);
			tournamentGroups.setVisible(false);
			selection.add(tournamentGroups);
		selection.setMaximumSize(selection.getPreferredSize());
		selection.setMinimumSize(selection.getPreferredSize());
		selection.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(selection);
		

		//---------------------------------------------
		JSeparator b = new JSeparator();
		b.setMaximumSize(new Dimension(420, 1));
		settings.add(b);
		//---------------------------------------------
		
		JPanel crossoverPanel = new JPanel();
		crossoverPanel.setLayout(new BoxLayout(crossoverPanel, BoxLayout.Y_AXIS));
		
		crossoverMethodPanel = new JPanel();
		crossoverMethodPanel.setLayout(new BoxLayout(crossoverMethodPanel, BoxLayout.Y_AXIS));
		JLabel crossoverLabel = new JLabel("Crossover");
		JPanel crossSel = new JPanel();
		crossSel.add(crossoverLabel);
		crossoverBox = new JComboBox<String>();
		crossSel.add(crossoverBox);
		crossoverMethodPanel.add(crossSel);
		crossoverMethodPanel.setMaximumSize(crossoverMethodPanel.getPreferredSize());
		crossoverMethodPanel.setMinimumSize(crossoverMethodPanel.getPreferredSize());
		crossoverMethodPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		crossoverPanel.add(crossoverMethodPanel);
		
		crossoverSlider = new JSlider(0,100);
		crossoverSlider.setMajorTickSpacing(30);
		crossoverSlider.setMinorTickSpacing(5);
		crossoverSlider.setPaintTicks(true);
		crossoverSlider.setPaintLabels(true);
		crossoverSlider.setToolTipText(crossoverSlider.getValue() + " %");
		crossoverSlider.addChangeListener(new SliderListener());
		crossoverSlider.setMaximumSize(crossoverSlider.getPreferredSize());
		crossoverSlider.setMinimumSize(crossoverSlider.getPreferredSize());
		crossoverSlider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		crossoverPanel.add(crossoverSlider);
		
		crossoverPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(crossoverPanel);
		

		//---------------------------------------------
		JSeparator c = new JSeparator();
		c.setMaximumSize(new Dimension(420, 1));
		settings.add(c);
		//---------------------------------------------
		
		JPanel mutationPanel = new JPanel();
		mutationPanel.setLayout(new BoxLayout(mutationPanel, BoxLayout.Y_AXIS));
		
		mutationMethodPanel = new JPanel();
		JLabel mutationLabel = new JLabel("Mutation");
		mutationMethodPanel.add(mutationLabel);
		mutationBox = new JComboBox<String>();
		mutationMethodPanel.add(mutationBox);
		mutationMethodPanel.setMaximumSize(mutationMethodPanel.getPreferredSize());
		mutationMethodPanel.setMinimumSize(mutationMethodPanel.getPreferredSize());
		mutationMethodPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mutationPanel.add(mutationMethodPanel);
		
		mutationSlider = new JSlider(0,100);
		mutationSlider.setMajorTickSpacing(30);
		mutationSlider.setMinorTickSpacing(5);
		mutationSlider.setPaintTicks(true);
		mutationSlider.setPaintLabels(true);
		mutationSlider.setToolTipText(mutationSlider.getValue() + " %");
		mutationSlider.addChangeListener(new SliderListener());
		mutationSlider.setMaximumSize(mutationSlider.getPreferredSize());
		mutationSlider.setMinimumSize(mutationSlider.getPreferredSize());
		mutationSlider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mutationPanel.add(mutationSlider);
		
		mutationPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(mutationPanel);
		
		//---------------------------------------------
		JSeparator d = new JSeparator();
		d.setMaximumSize(new Dimension(420, 1));
		settings.add(d);
		//---------------------------------------------
		
		JPanel elitism = new JPanel();
		elitism.setLayout(new BoxLayout(elitism, BoxLayout.Y_AXIS));
		
		JLabel elitismLabel = new JLabel("Elitism");
		elitismLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		elitism.add(elitismLabel);
		
		elitismSlider = new JSlider(0,100);
		elitismSlider.setMajorTickSpacing(30);
		elitismSlider.setMinorTickSpacing(5);
		elitismSlider.setPaintTicks(true);
		elitismSlider.setPaintLabels(true);
		elitismSlider.setToolTipText(elitismSlider.getValue() + " %");
		elitismSlider.addChangeListener(new SliderListener());
		elitismSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		elitism.add(elitismSlider);
		
		elitism.setMaximumSize(elitism.getPreferredSize());
		elitism.setMinimumSize(elitism.getPreferredSize());
		elitism.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(elitism);
		
		//---------------------------------------------
		
		JPanel contentBasedTermination = new JPanel();
		JLabel cbterm = new JLabel("Quality termin. criteria");
		contentBasedTermination.add(cbterm);
		contentBasedTerminationCheck = new JCheckBox();
		contentBasedTerminationCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					generationsLabel.setText("Relative gen");
					generations.setMaximumSize(generations.getPreferredSize());
					generations.setMinimumSize(generations.getPreferredSize());
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					generationsLabel.setText("Generations");
					generations.setMaximumSize(generations.getPreferredSize());
					generations.setMinimumSize(generations.getPreferredSize());
				}
			}
		});
		contentBasedTermination.add(contentBasedTerminationCheck);
		contentBasedTermination.setMaximumSize(contentBasedTermination.getPreferredSize());
		contentBasedTermination.setMinimumSize(contentBasedTermination.getPreferredSize());
		contentBasedTermination.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentBasedTermination.setToolTipText("Termination criteria based on the content. " + 
				"It only increases a generation when a global fitness increment is produced.");
		settings.add(contentBasedTermination);
		
		//---------------------------------------------
			JSeparator e = new JSeparator();
			e.setMaximumSize(new Dimension(420, 1));
			settings.add(e);
		//---------------------------------------------
		
		JPanel optionalPanel = new JPanel();
		optionalPanel.setLayout(new BoxLayout(optionalPanel, BoxLayout.Y_AXIS));
		JPanel optionalCheck = new JPanel();
		JPanel optionalSettings = new JPanel();
		JScrollPane scroll = new JScrollPane(optionalSettings);
		scroll.setBorder(null);
		scroll.setMinimumSize(new Dimension(0,0));
		scroll.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scroll.setVisible(false);
		optionalPanel.add(optionalCheck);
		optionalPanel.add(scroll);
		
		rangePopulationRadioButton = new JRadioButton();
		rangeGenerationRadioButton = new JRadioButton();
		rangeCrossRadioButton = new JRadioButton();
		rangeMutationRadioButton = new JRadioButton();
		rangeElitismRadioButton = new JRadioButton();
		bg = new ButtonGroup();
		bg.add(rangePopulationRadioButton);
		bg.add(rangeGenerationRadioButton);
		bg.add(rangeCrossRadioButton);
		bg.add(rangeMutationRadioButton);
		bg.add(rangeElitismRadioButton);
		
		optionalCheck.add(new JLabel("Parameter variations"));
		rangeParametersCheck = new JCheckBox();
		rangeParametersCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					scroll.setVisible(true);
					optionalPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
					optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
					optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					scroll.setVisible(false);
					optionalPanel.setBorder(null);
					optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
					optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
					
					bg.clearSelection();
					
					populationText.setEnabled(true);
					generationText.setEnabled(true);
					contentBasedTerminationCheck.setEnabled(true);
					crossoverSlider.setEnabled(true);
					mutationSlider.setEnabled(true);
					elitismSlider.setEnabled(true);
				}
			}
		});
		optionalCheck.add(rangeParametersCheck);
		optionalCheck.setMaximumSize(optionalCheck.getPreferredSize());
		optionalCheck.setMinimumSize(optionalCheck.getPreferredSize());
		optionalCheck.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JPanel popTextOpt = new JPanel();
		popTextOpt.setVisible(false);
		optionalSettings.setLayout(new BoxLayout(optionalSettings, BoxLayout.Y_AXIS));
			JPanel popOpt = new JPanel();
			popOpt.setLayout(new BoxLayout(popOpt, BoxLayout.Y_AXIS));
				JPanel go1 = new JPanel();
					JLabel go11 = new JLabel("Population size");
					go1.add(go11);
					rangePopulationRadioButton.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
						    if (e.getStateChange() == ItemEvent.SELECTED) {
						        popTextOpt.setVisible(true);
						        popOpt.setMaximumSize(popOpt.getPreferredSize());
								popOpt.setMinimumSize(popOpt.getPreferredSize());
								optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								populationText.setEnabled(false);
						    }
						    else if (e.getStateChange() == ItemEvent.DESELECTED) {
						    	popTextOpt.setVisible(false);
						    	popOpt.setMaximumSize(popOpt.getPreferredSize());
								popOpt.setMinimumSize(popOpt.getPreferredSize());
								optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								populationText.setEnabled(true);
						    }
						}
					});
					go1.add(rangePopulationRadioButton);
				popOpt.add(go1);
				
				pomin = new JTextField(4);
				pomin.setInputVerifier(new IntegerNonZeroVerifier());
				pomin.setToolTipText("min range");
				pomax = new JTextField(4);
				pomax.setInputVerifier(new IntegerNonZeroVerifier());
				pomin.setToolTipText("max range");
				postep = new JTextField(4);
				postep.setInputVerifier(new IntegerNonZeroVerifier());
				pomin.setToolTipText("step");
				popTextOpt.add(pomin);
				popTextOpt.add(pomax);
				popTextOpt.add(postep);
			popOpt.add(popTextOpt);
			popOpt.setMaximumSize(popOpt.getPreferredSize());
			popOpt.setMinimumSize(popOpt.getPreferredSize());
			popOpt.setAlignmentX(Component.RIGHT_ALIGNMENT);
			optionalSettings.add(popOpt);
		
			JPanel generOpt = new JPanel();
			JPanel generTextOpt = new JPanel();
			generTextOpt.setVisible(false);
			generOpt.setLayout(new BoxLayout(generOpt, BoxLayout.Y_AXIS));
				JPanel go2 = new JPanel();
					JLabel go21 = new JLabel("Generations");
					go2.add(go21);
					rangeGenerationRadioButton.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
						    if (e.getStateChange() == ItemEvent.SELECTED) {
						    	generTextOpt.setVisible(true);
						    	generOpt.setMaximumSize(generOpt.getPreferredSize());
						    	generOpt.setMinimumSize(generOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								generationText.setEnabled(false);
								contentBasedTerminationCheck.setEnabled(false);
						    }
						    else if (e.getStateChange() == ItemEvent.DESELECTED) {
						    	generTextOpt.setVisible(false);
						    	generOpt.setMaximumSize(generOpt.getPreferredSize());
						    	generOpt.setMinimumSize(generOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								generationText.setEnabled(true);
								contentBasedTerminationCheck.setEnabled(true);
						    }
						}
					});
					go2.add(rangeGenerationRadioButton);
				generOpt.add(go2);
				
				gomin = new JTextField(4);
				gomin.setInputVerifier(new IntegerNonZeroVerifier());
				gomin.setToolTipText("min range");
				gomax = new JTextField(4);
				gomax.setInputVerifier(new IntegerNonZeroVerifier());
				gomax.setToolTipText("max range");
				gostep = new JTextField(4);
				gostep.setInputVerifier(new IntegerNonZeroVerifier());
				gostep.setToolTipText("step");
				generTextOpt.add(gomin);
				generTextOpt.add(gomax);
				generTextOpt.add(gostep);
			generOpt.add(generTextOpt);
			generOpt.setMaximumSize(generOpt.getPreferredSize());
			generOpt.setMinimumSize(generOpt.getPreferredSize());
			generOpt.setAlignmentX(Component.RIGHT_ALIGNMENT);
			optionalSettings.add(generOpt);
			
			JPanel crossOpt = new JPanel();
			JPanel crossTextOpt = new JPanel();
			crossTextOpt.setVisible(false);
			crossOpt.setLayout(new BoxLayout(crossOpt, BoxLayout.Y_AXIS));
				JPanel co2 = new JPanel();
					JLabel co21 = new JLabel("Crossover");
					co2.add(co21);
					rangeCrossRadioButton.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
						    if (e.getStateChange() == ItemEvent.SELECTED) {
						    	crossTextOpt.setVisible(true);
						    	crossOpt.setMaximumSize(crossOpt.getPreferredSize());
						    	crossOpt.setMinimumSize(crossOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								crossoverSlider.setEnabled(false);
						    }
						    else if (e.getStateChange() == ItemEvent.DESELECTED) {
						    	crossTextOpt.setVisible(false);
						    	crossOpt.setMaximumSize(crossOpt.getPreferredSize());
						    	crossOpt.setMinimumSize(crossOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								crossoverSlider.setEnabled(true);
						    }
						}
					});
					co2.add(rangeCrossRadioButton);
				crossOpt.add(co2);
				
				comin = new JTextField(4);
				comin.setInputVerifier(new DoubleLessThanZeroVerifier());
				comin.setToolTipText("min range");
				comax = new JTextField(4);
				comax.setInputVerifier(new DoubleLessThanZeroVerifier());
				comax.setToolTipText("max range");
				costep = new JTextField(4);
				costep.setInputVerifier(new DoubleLessThanZeroVerifier());
				costep.setToolTipText("step");
				crossTextOpt.add(comin);
				crossTextOpt.add(comax);
				crossTextOpt.add(costep);
			crossOpt.add(crossTextOpt);
			crossOpt.setMaximumSize(crossOpt.getPreferredSize());
			crossOpt.setMinimumSize(crossOpt.getPreferredSize());
			crossOpt.setAlignmentX(Component.RIGHT_ALIGNMENT);
			optionalSettings.add(crossOpt);
			
			JPanel mutOpt = new JPanel();
			JPanel mutTextOpt = new JPanel();
			mutTextOpt.setVisible(false);
			mutOpt.setLayout(new BoxLayout(mutOpt, BoxLayout.Y_AXIS));
				JPanel mo2 = new JPanel();
					JLabel mo21 = new JLabel("Mutation");
					mo2.add(mo21);
					rangeMutationRadioButton.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
						    if (e.getStateChange() == ItemEvent.SELECTED) {
						    	mutTextOpt.setVisible(true);
						    	mutOpt.setMaximumSize(mutOpt.getPreferredSize());
						    	mutOpt.setMinimumSize(mutOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								mutationSlider.setEnabled(false);
						    }
						    else if (e.getStateChange() == ItemEvent.DESELECTED) {
						    	mutTextOpt.setVisible(false);
						    	mutOpt.setMaximumSize(mutOpt.getPreferredSize());
						    	mutOpt.setMinimumSize(mutOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								mutationSlider.setEnabled(true);
						    }
						}
					});
					mo2.add(rangeMutationRadioButton);
				mutOpt.add(mo2);
				
				momin = new JTextField(4);
				momin.setInputVerifier(new DoubleLessThanZeroVerifier());
				momin.setToolTipText("min range");
				momax = new JTextField(4);
				momax.setInputVerifier(new DoubleLessThanZeroVerifier());
				momax.setToolTipText("max range");
				mostep = new JTextField(4);
				mostep.setInputVerifier(new DoubleLessThanZeroVerifier());
				mostep.setToolTipText("step");
				mutTextOpt.add(momin);
				mutTextOpt.add(momax);
				mutTextOpt.add(mostep);
			mutOpt.add(mutTextOpt);
			mutOpt.setMaximumSize(mutOpt.getPreferredSize());
			mutOpt.setMinimumSize(mutOpt.getPreferredSize());
			mutOpt.setAlignmentX(Component.RIGHT_ALIGNMENT);
			optionalSettings.add(mutOpt);
			
			JPanel elitOpt = new JPanel();
			JPanel elitTextOpt = new JPanel();
			elitTextOpt.setVisible(false);
			elitOpt.setLayout(new BoxLayout(elitOpt, BoxLayout.Y_AXIS));
				JPanel eo2 = new JPanel();
					JLabel eo21 = new JLabel("Elitism");
					eo2.add(eo21);
					rangeElitismRadioButton.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
						    if (e.getStateChange() == ItemEvent.SELECTED) {
						    	elitTextOpt.setVisible(true);
						    	elitOpt.setMaximumSize(elitOpt.getPreferredSize());
						    	elitOpt.setMinimumSize(elitOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								elitismSlider.setEnabled(false);
						    }
						    else if (e.getStateChange() == ItemEvent.DESELECTED) {
						    	elitTextOpt.setVisible(false);
						    	elitOpt.setMaximumSize(elitOpt.getPreferredSize());
						    	elitOpt.setMinimumSize(elitOpt.getPreferredSize());
						    	optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
								optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
								
								elitismSlider.setEnabled(true);
						    }
						}
					});
					eo2.add(rangeElitismRadioButton);
				elitOpt.add(eo2);
				
				eomin = new JTextField(4);
				eomin.setInputVerifier(new DoubleLessThanZeroVerifier());
				eomin.setToolTipText("min range");
				eomax = new JTextField(4);
				eomax.setInputVerifier(new DoubleLessThanZeroVerifier());
				eomax.setToolTipText("max range");
				eostep = new JTextField(4);
				eostep.setInputVerifier(new DoubleLessThanZeroVerifier());
				eostep.setToolTipText("step");
				elitTextOpt.add(eomin);
				elitTextOpt.add(eomax);
				elitTextOpt.add(eostep);
			elitOpt.add(elitTextOpt);
			elitOpt.setMaximumSize(elitOpt.getPreferredSize());
			elitOpt.setMinimumSize(elitOpt.getPreferredSize());
			elitOpt.setAlignmentX(Component.RIGHT_ALIGNMENT);
			optionalSettings.add(elitOpt);
			
		optionalSettings.setMaximumSize(optionalSettings.getPreferredSize());
		optionalSettings.setMinimumSize(optionalSettings.getPreferredSize());
		optionalSettings.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(optionalPanel);
		
	}
	
	private void fillFields() {
		this.populationText.setText(String.valueOf(this.ctrl.getPopulation()));
		this.generationText.setText(String.valueOf(this.ctrl.getGenerations()));
		this.heightText.setText(String.valueOf(this.ctrl.getHeight()));
		this.tournamentGroupsText.setText(Integer.toString( this.ctrl.getTournamentSelectionGroups() ));
		this.crossoverSlider.setValue(this.ctrl.getCrossoverPercentage());
		this.mutationSlider.setValue(this.ctrl.getMutationPercentage());
		this.elitismSlider.setValue((int) (this.ctrl.getElitismPercentage()));
		for (String item : this.ctrl.getInitializationStrategyList()) {
			this.initializationBox.addItem(item);			
		}
		this.initializationBox.setSelectedItem(this.ctrl.getinitializationStrategy());
		initialization.setMaximumSize(initialization.getPreferredSize());
		initialization.setMinimumSize(initialization.getPreferredSize());
		for (String item : this.ctrl.getSelectionStrategyList()) {
			this.selectionBox.addItem(item);			
		}
		this.selectionBox.setSelectedItem(this.ctrl.getSelectionStrategy());
		selection.setMaximumSize(selection.getPreferredSize());
		selection.setMinimumSize(selection.getPreferredSize());
		for (String item : this.ctrl.getCrossoverStrategyList()) {
			this.crossoverBox.addItem(item);			
		}
		this.crossoverBox.setSelectedItem(this.ctrl.getCrossoverStrategy());
		crossoverMethodPanel.setMaximumSize(crossoverMethodPanel.getPreferredSize());
		crossoverMethodPanel.setMinimumSize(crossoverMethodPanel.getPreferredSize());
		for (String item : this.ctrl.getMutationStrategyList()) {
			this.mutationBox.addItem(item);			
		}
		this.mutationBox.setSelectedItem(this.ctrl.getMutationStrategy());
		mutationMethodPanel.setMaximumSize(mutationMethodPanel.getPreferredSize());
		mutationMethodPanel.setMinimumSize(mutationMethodPanel.getPreferredSize());
		this.contentBasedTerminationCheck.setSelected(this.ctrl.isContentBasedTermination());
		
		saveDefaults();
	}
	
	private void saveDefaults() {
		populationTextDefault = populationText.getText();
		generationTextDefault = generationText.getText();
		heightTextDefault = heightText.getText();
		tournamentGroupsTextDefault = tournamentGroupsText.getText();
		crossoverSliderDefault = crossoverSlider.getValue();
		mutationSliderDefault = mutationSlider.getValue();
		elitismSliderDefault = elitismSlider.getValue();
		initializationBoxDefault = initializationBox.getSelectedItem();
		selectionBoxDefault = selectionBox.getSelectedItem();
		crossoverBoxDefault = crossoverBox.getSelectedItem();
		mutationBoxDefault = mutationBox.getSelectedItem();
		contentBasedTerminationCheckDefault = contentBasedTerminationCheck.isSelected();
	}
	
	private void restoreDefaults() {
		populationText.setText(populationTextDefault);
		generationText.setText(generationTextDefault);
		heightText.setText(heightTextDefault);
		tournamentGroupsText.setText(tournamentGroupsTextDefault);
		crossoverSlider.setValue(crossoverSliderDefault);
		mutationSlider.setValue(mutationSliderDefault);
		elitismSlider.setValue(elitismSliderDefault);
		initializationBox.setSelectedItem(initializationBoxDefault);
		selectionBox.setSelectedItem(selectionBoxDefault);
		crossoverBox.setSelectedItem(crossoverBoxDefault);
		mutationBox.setSelectedItem(mutationBoxDefault);
		contentBasedTerminationCheck.setSelected(contentBasedTerminationCheckDefault);
	}
	
	private void setRanges() {
		if(rangePopulationRadioButton.isSelected()) {
			this.ctrl.setRanges(Double.parseDouble(pomin.getText()), Double.parseDouble(pomax.getText()), Double.parseDouble(postep.getText()));
			this.ctrl.setParamRange("population");
		}
		else if(rangeGenerationRadioButton.isSelected()) {
			this.ctrl.setRanges(Double.parseDouble(gomin.getText()), Double.parseDouble(gomax.getText()), Double.parseDouble(gostep.getText()));
			this.ctrl.setParamRange("generations");
		}
		else if(rangeCrossRadioButton.isSelected()) {
			this.ctrl.setRanges(Double.parseDouble(comin.getText()), Double.parseDouble(comax.getText()), Double.parseDouble(costep.getText()));
			this.ctrl.setParamRange("crossover");
		}
		else if(rangeMutationRadioButton.isSelected()) {
			this.ctrl.setRanges(Double.parseDouble(momin.getText()), Double.parseDouble(momax.getText()), Double.parseDouble(mostep.getText()));
			this.ctrl.setParamRange("mutation");
		}
		else if(rangeElitismRadioButton.isSelected()) {
			this.ctrl.setRanges(Double.parseDouble(eomin.getText()), Double.parseDouble(eomax.getText()), Double.parseDouble(eostep.getText()));
			this.ctrl.setParamRange("elitism");
		}
	}

	@Override
	public void onStartRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (Component cmp : buttonPanel.getComponents()) {
					cmp.setVisible(true);
					cmp.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void onEndRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (Component cmp : buttonPanel.getComponents()) {
					cmp.setEnabled(true);
				}
			}
		});
	}
	
	@Override
	public void onIncrement(int n) {
		
	}
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            int num = (int)source.getValue();
	            source.setToolTipText(String.valueOf(num) + " %");
	        }
		}
	}
	
	class IntegerNonZeroVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			JTextField field = (JTextField) input;
			try {
				int a = Integer.parseInt(((JTextField) input).getText());
				if (a >= 1) {
					field.setBorder(defaultborder);
					status.setErrors(false);
					return true;
				}
				else {
					field.setBorder(BorderFactory.createLineBorder(Color.red));
					status.setErrors(true);
					return false;
				}
			} catch (NumberFormatException e) {
				field.setBorder(BorderFactory.createLineBorder(Color.red));
				status.setErrors(true);
				return false;
			}
		}
		
	}
	
	class DoubleLessThanZeroVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			JTextField field = (JTextField) input;
			try {
				double a = Double.parseDouble(((JTextField) input).getText());
				if (a >= 0 && a <= 1) {
					field.setBorder(defaultborder);
					status.setErrors(false);
					return true;
				}
				else {
					field.setBorder(BorderFactory.createLineBorder(Color.red));
					status.setErrors(true);
					return false;
				}
			} catch (NumberFormatException e) {
				field.setBorder(BorderFactory.createLineBorder(Color.red));
				status.setErrors(true);
				return false;
			}
		}
		
	}

}
