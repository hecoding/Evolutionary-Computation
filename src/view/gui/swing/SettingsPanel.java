package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
 	JPanel crossover;
 	JComboBox<String> crossoverBox;
 	JPanel mutation;
 	JComboBox<String> mutationBox;
 	
 	String populationTextDefault;
	String generationTextDefault;
	int crossoverSliderDefault;
	int mutationSliderDefault;
	boolean elitismCheckDefault;
	int elitismSliderDefault;
	Object selectionBoxDefault;
	Object crossoverBoxDefault;
	Object mutationBoxDefault;

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
					ctrl.setSelectionStrategy((String) selectionBox.getSelectedItem());
					ctrl.setCrossoverStrategy((String) crossoverBox.getSelectedItem());
					//ctrl.setMutationStrategy((String) mutationBox.getSelectedItem());
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
		
		JPanel crossoverPerc = new JPanel(new BorderLayout());
		JLabel crossoverPercLabel = new JLabel("Cruce");
		crossoverPerc.add(crossoverPercLabel, BorderLayout.PAGE_START);
		crossoverSlider = new JSlider(0,100);
		crossoverSlider.setMajorTickSpacing(30);
		crossoverSlider.setMinorTickSpacing(5);
		crossoverSlider.setPaintTicks(true);
		crossoverSlider.setPaintLabels(true);
		crossoverSlider.setToolTipText(crossoverSlider.getValue() + " %");
		crossoverSlider.addChangeListener(new SliderListener());
		crossoverPerc.add(crossoverSlider, BorderLayout.CENTER);
		crossoverPerc.setMaximumSize(crossoverPerc.getPreferredSize());
		crossoverPerc.setMinimumSize(crossoverPerc.getPreferredSize());
		crossoverPerc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(crossoverPerc);
		
		//---------------------------------------------
		
		JPanel mutationPerc = new JPanel(new BorderLayout());
		JLabel mutationPercLabel = new JLabel("Mutación");
		mutationPerc.add(mutationPercLabel, BorderLayout.PAGE_START);
		mutationSlider = new JSlider(0,100);
		mutationSlider.setMajorTickSpacing(30);
		mutationSlider.setMinorTickSpacing(5);
		mutationSlider.setPaintTicks(true);
		mutationSlider.setPaintLabels(true);
		mutationSlider.setToolTipText(mutationSlider.getValue() + " %");
		mutationSlider.addChangeListener(new SliderListener());
		mutationPerc.add(mutationSlider, BorderLayout.CENTER);
		mutationPerc.setMaximumSize(mutationPerc.getPreferredSize());
		mutationPerc.setMinimumSize(mutationPerc.getPreferredSize());
		mutationPerc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(mutationPerc);
		
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
		
		selection = new JPanel();
		JLabel selectionLabel = new JLabel("Selección");
		selection.add(selectionLabel);
		selectionBox = new JComboBox<String>();
		selection.add(selectionBox);
		selection.setMaximumSize(selection.getPreferredSize());
		selection.setMinimumSize(selection.getPreferredSize());
		selection.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(selection);
		
		//---------------------------------------------
		
		crossover = new JPanel();
		JLabel crossoverLabel = new JLabel("Cruce");
		crossover.add(crossoverLabel);
		crossoverBox = new JComboBox<String>();
		crossover.add(crossoverBox);
		crossover.setMaximumSize(crossover.getPreferredSize());
		crossover.setMinimumSize(crossover.getPreferredSize());
		crossover.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(crossover);
		
		//---------------------------------------------
		
		mutation = new JPanel();
		JLabel mutationLabel = new JLabel("Mutación");
		mutation.add(mutationLabel);
		mutationBox = new JComboBox<String>();
		mutation.add(mutationBox);
		mutation.setMaximumSize(mutation.getPreferredSize());
		mutation.setMinimumSize(mutation.getPreferredSize());
		mutation.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(mutation);
		
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
		crossover.setMaximumSize(crossover.getPreferredSize());
		crossover.setMinimumSize(crossover.getPreferredSize());
		/*for (String item : this.ctrl.getMutationStrategyList()) {
			this.mutationBox.addItem(item);			
		}
		mutation.setMaximumSize(mutation.getPreferredSize());
		mutation.setMinimumSize(mutation.getPreferredSize());*/
		
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
