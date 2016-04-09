package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
		
		constructSettings();		
		this.add(settings, BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		runButton = new JButton("Lanzar");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ctrl.setParameters(transfer);
				try {
					ctrl.run();
				} catch(IllegalChromosomeException ex) {
					JOptionPane.showMessageDialog(null,
							ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					for (Component cmp : buttonPanel.getComponents()) {
						cmp.setEnabled(true);
					}
				}
			}
		});
		buttonPanel.add(runButton, BorderLayout.CENTER);
		resetButton = new JButton("Resetear");
		resetButton.setToolTipText("Volver a los valores iniciales");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(ctrl.getParameters());
			}
		});
		buttonPanel.add(resetButton, BorderLayout.PAGE_END);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// maybe wrap around a JScrollPane and/or JSplitPane
	}
	
	private void constructSettings() {
		settings = new JPanel();
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		
		//---------------------------------------------
		
		JPanel population = new JPanel();
		JLabel populationLabel = new JLabel("Población");
		population.add(populationLabel);
		JTextField populationText = new JTextField(4);
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
		JTextField generationText = new JTextField(4);
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
		JSlider crossoverSlider = new JSlider(0,100,60);
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
		JSlider mutationSlider = new JSlider(0,100,5);
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
		JSlider elitismSlider = new JSlider(0,100,10);;
		JCheckBox elitismCheck = new JCheckBox();
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
		
		JPanel selection = new JPanel();
		JLabel selectionLabel = new JLabel("Selección");
		selection.add(selectionLabel);
		String[] cosas = {"bla", "blabla"};
		JComboBox selectionBox = new JComboBox(cosas);
		selection.add(selectionBox);
		selection.setMaximumSize(selection.getPreferredSize());
		selection.setMinimumSize(selection.getPreferredSize());
		selection.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(selection);
		
		//---------------------------------------------
		
		JPanel crossover = new JPanel();
		JLabel crossoverLabel = new JLabel("Cruce");
		crossover.add(crossoverLabel);
		String[] cosas1 = {"bla", "blabla"};
		JComboBox crossoverBox = new JComboBox(cosas1);
		crossover.add(crossoverBox);
		crossover.setMaximumSize(crossover.getPreferredSize());
		crossover.setMinimumSize(crossover.getPreferredSize());
		crossover.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(crossover);
		
		//---------------------------------------------
		
		JPanel mutation = new JPanel();
		JLabel mutationLabel = new JLabel("Mutación");
		mutation.add(mutationLabel);
		String[] cosas2 = {"bla", "blabla"};
		JComboBox mutationBox = new JComboBox(cosas2);
		mutation.add(mutationBox);
		mutation.setMaximumSize(mutation.getPreferredSize());
		mutation.setMinimumSize(mutation.getPreferredSize());
		mutation.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(mutation);
		
		// misma población inicial
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
