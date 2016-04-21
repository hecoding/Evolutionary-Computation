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
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class SettingsPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private JPanel settings;
 	private JPanel buttonPanel;
 	JButton runButton;
 	JButton resetButton;
 	private StatusBarPanel status;
 	
 	JTextField populationText;
 	JTextField generationText;
 	JSlider crossoverSlider;
 	JSlider mutationSlider;
 	JCheckBox elitismCheck;
 	JSlider elitismSlider;
 	JPanel selection;
 	JComboBox<String> selectionBox;
 	JPanel crossoverMethodPanel;
 	JComboBox<String> crossoverBox;
 	JPanel tournamentGroups;
 	JTextField SBXGroups;
 	JPanel mutationMethodPanel;
 	JComboBox<String> mutationBox;
 	JCheckBox variableMutationCheck;
 	
 	String populationTextDefault;
	String generationTextDefault;
	int crossoverSliderDefault;
	int mutationSliderDefault;
	boolean elitismCheckDefault;
	int elitismSliderDefault;
	Object selectionBoxDefault;
	Object crossoverBoxDefault;
	Object mutationBoxDefault;
	boolean variableMutationCheckDefault;

	public SettingsPanel(Controller ctrl, StatusBarPanel status) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.status = status;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder("Ajustes"));
		
		initSettings();		
		this.add(settings, BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		runButton = new JButton("Lanzar");
		runButton.setMnemonic(KeyEvent.VK_L);
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctrl.setPopulation(Integer.parseInt(populationText.getText()));
					ctrl.setGenerations(Integer.parseInt(generationText.getText()));
					ctrl.setCrossoverPercentage(crossoverSlider.getValue());
					ctrl.setMutationPercentage(mutationSlider.getValue());
					ctrl.setElitism(elitismCheck.isSelected());
					ctrl.setElitismPercentage(elitismSlider.getValue());
					ctrl.setSelectionParameter(SBXGroups.getText());
					ctrl.setSelectionStrategy((String) selectionBox.getSelectedItem());
					ctrl.setCrossoverStrategy((String) crossoverBox.getSelectedItem());
					ctrl.setMutationStrategy((String) mutationBox.getSelectedItem());
					ctrl.run();
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
		resetButton = new JButton("Resetear");
		resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.setToolTipText("Volver a los valores iniciales");
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
		JLabel populationLabel = new JLabel("Población");
		population.add(populationLabel);
		populationText = new JTextField(4);
		final Border defaultborder = populationText.getBorder();
		populationText.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				try {
					int a = Integer.parseInt(((JTextField) input).getText());
					if (a >= 1) {
						populationText.setBorder(defaultborder);
						status.setErrors(false);
						return true;
					}
					else {
						populationText.setBorder(BorderFactory.createLineBorder(Color.red));
						status.setErrors(true);
						return false;
					}
				} catch (NumberFormatException e) {
					populationText.setBorder(BorderFactory.createLineBorder(Color.red));
					status.setErrors(true);
					return false;
				}
			}
		});
		population.add(populationText);
		population.setMaximumSize(population.getPreferredSize());
		population.setMinimumSize(population.getPreferredSize());
		population.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(population);
		
		//---------------------------------------------
		
		JPanel generations = new JPanel();
		JLabel generationsLabel = new JLabel("Generaciones");
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
		JSeparator a = new JSeparator();
		a.setMaximumSize(new Dimension(420, 1));
		settings.add(a);
		//---------------------------------------------
		
		selection = new JPanel();
		selection.setLayout(new BoxLayout(selection, BoxLayout.Y_AXIS));
		JPanel selectionSel = new JPanel();
		JLabel selectionLabel = new JLabel("Selección");
		selectionSel.add(selectionLabel);
		selectionBox = new JComboBox<String>();
		selectionBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getItem() == "Torneo") {
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
			tournamentGroups.add(new JLabel("Tamaño grupos"));
			SBXGroups = new JTextField(4);
			tournamentGroups.add(SBXGroups);
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
		JLabel crossoverLabel = new JLabel("Cruce");
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
		JLabel mutationLabel = new JLabel("Mutación");
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
		
		JPanel elitism = new JPanel(new BorderLayout());
		
		JPanel elitismMain = new JPanel();
		JLabel elitismLabel = new JLabel("Elitismo");
		elitismMain.add(elitismLabel);
		elitismSlider = new JSlider(0,100);
		elitismCheck = new JCheckBox();
		elitismCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					elitismSlider.setVisible(true);
					elitism.setMaximumSize(elitism.getPreferredSize());
					elitism.setBorder(BorderFactory.createLineBorder(Color.lightGray));
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					elitismSlider.setVisible(false);
					elitism.setMaximumSize(elitism.getPreferredSize());
					elitism.setBorder(null);
				}
			}
		});
		elitismMain.add(elitismCheck);
		elitism.add(elitismMain, BorderLayout.CENTER);
		
		elitismSlider.setMajorTickSpacing(30);
		elitismSlider.setMinorTickSpacing(5);
		elitismSlider.setPaintTicks(true);
		elitismSlider.setPaintLabels(true);
		elitismSlider.setToolTipText(elitismSlider.getValue() + " %");
		elitismSlider.addChangeListener(new SliderListener());
		elitismSlider.setVisible(false);
		elitism.add(elitismSlider, BorderLayout.PAGE_END);
		
		elitism.setMaximumSize(elitism.getPreferredSize());
		elitism.setMinimumSize(elitism.getPreferredSize());
		elitism.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(elitism);
		
		//---------------------------------------------
		
		JPanel variableMutation = new JPanel();
		JLabel varMutLabel = new JLabel("Mutación variable");
		variableMutation.add(varMutLabel);
		variableMutationCheck = new JCheckBox();
		variableMutationCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					ctrl.setVariableMutation(true);
					mutationSlider.setEnabled(false);
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					ctrl.setVariableMutation(false);
					mutationSlider.setEnabled(true);
				}
			}
		});
		variableMutation.add(variableMutationCheck);
		variableMutation.setMaximumSize(variableMutation.getPreferredSize());
		variableMutation.setMinimumSize(variableMutation.getPreferredSize());
		variableMutation.setAlignmentX(Component.RIGHT_ALIGNMENT);
		variableMutation.setToolTipText("El ratio de mutación va disminuyendo conforme avanzan las generaciones");
		settings.add(variableMutation);
		
		// misma población inicial
	}
	
	private void fillFields() {
		this.populationText.setText(String.valueOf(this.ctrl.getPopulation()));
		this.generationText.setText(String.valueOf(this.ctrl.getGenerations()));
		this.crossoverSlider.setValue(this.ctrl.getCrossoverPercentage());
		this.mutationSlider.setValue(this.ctrl.getMutationPercentage());
		this.elitismCheck.setSelected(this.ctrl.getElitism());
		this.elitismSlider.setValue((int) (this.ctrl.getElitismPercentage()));
		for (String item : this.ctrl.getSelectionStrategyList()) {
			this.selectionBox.addItem(item);			
		}
		selection.setMaximumSize(selection.getPreferredSize());
		selection.setMinimumSize(selection.getPreferredSize());
		for (String item : this.ctrl.getCrossoverStrategyList()) {
			this.crossoverBox.addItem(item);			
		}
		crossoverMethodPanel.setMaximumSize(crossoverMethodPanel.getPreferredSize());
		crossoverMethodPanel.setMinimumSize(crossoverMethodPanel.getPreferredSize());
		for (String item : this.ctrl.getMutationStrategyList()) {
			this.mutationBox.addItem(item);			
		}
		mutationMethodPanel.setMaximumSize(mutationMethodPanel.getPreferredSize());
		mutationMethodPanel.setMinimumSize(mutationMethodPanel.getPreferredSize());
		this.variableMutationCheck.setSelected(this.ctrl.getVariableMutation());
		
		saveDefaults();
	}
	
	private void saveDefaults() {
		populationTextDefault = populationText.getText();
		generationTextDefault = generationText.getText();
		crossoverSliderDefault = crossoverSlider.getValue();
		mutationSliderDefault = mutationSlider.getValue();
		elitismCheckDefault = elitismCheck.isSelected();
		elitismSliderDefault = elitismSlider.getValue();
		selectionBoxDefault = selectionBox.getSelectedItem();
		crossoverBoxDefault = crossoverBox.getSelectedItem();
		mutationBoxDefault = mutationBox.getSelectedItem();
		variableMutationCheckDefault = variableMutationCheck.isSelected();
	}
	
	private void restoreDefaults() {
		populationText.setText(populationTextDefault);
		generationText.setText(generationTextDefault);
		crossoverSlider.setValue(crossoverSliderDefault);
		mutationSlider.setValue(mutationSliderDefault);
		elitismCheck.setSelected(elitismCheckDefault);
		elitismSlider.setValue(elitismSliderDefault);
		selectionBox.setSelectedItem(selectionBoxDefault);
		crossoverBox.setSelectedItem(crossoverBoxDefault);
		mutationBox.setSelectedItem(mutationBoxDefault);
		variableMutationCheck.setSelected(variableMutationCheckDefault);
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
	
	class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            int num = (int)source.getValue();
	            source.setToolTipText(String.valueOf(num) + " %");
	        }
		}
	}

}
