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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
					if (a > 0) {
						populationText.setBorder(defaultborder);
						return true;
					}
					else {
						populationText.setBorder(BorderFactory.createLineBorder(Color.red));
						return false;
					}
				} catch (NumberFormatException e) {
					populationText.setBorder(BorderFactory.createLineBorder(Color.red));
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
		generations.add(generationText);
		generations.setMaximumSize(generations.getPreferredSize());
		generations.setMinimumSize(generations.getPreferredSize());
		generations.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(generations);
		
		//---------------------------------------------
		
		JPanel crossoverPerc = new JPanel();
		JLabel crossoverPercLabel = new JLabel("Cruce");
		crossoverPerc.add(crossoverPercLabel);
		JTextField crossoverPercText = new JTextField(4);
		crossoverPerc.add(crossoverPercText);
		crossoverPerc.setMaximumSize(crossoverPerc.getPreferredSize());
		crossoverPerc.setMinimumSize(crossoverPerc.getPreferredSize());
		crossoverPerc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(crossoverPerc);
		
		//---------------------------------------------
		
		JPanel mutationPerc = new JPanel();
		JLabel mutationPercLabel = new JLabel("Mutación");
		mutationPerc.add(mutationPercLabel);
		JTextField mutationPercText = new JTextField(4);
		mutationPerc.add(mutationPercText);
		mutationPerc.setMaximumSize(mutationPerc.getPreferredSize());
		mutationPerc.setMinimumSize(mutationPerc.getPreferredSize());
		mutationPerc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		settings.add(mutationPerc);
		
		//---------------------------------------------
		
		JPanel elitism = new JPanel(new BorderLayout());
		
		JPanel elitismMain = new JPanel();
		JLabel elitismLabel = new JLabel("Elitismo");
		elitismMain.add(elitismLabel);
		JCheckBox elitismCheck = new JCheckBox();
		JPanel el = new JPanel();
		elitismCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					el.setVisible(true);
					elitism.setMaximumSize(elitism.getPreferredSize());
					elitism.setBorder(BorderFactory.createLineBorder(Color.lightGray));
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					el.setVisible(false);
					elitism.setMaximumSize(elitism.getPreferredSize());
					elitism.setBorder(null);
				}
			}
		});
		elitismMain.add(elitismCheck);
		elitism.add(elitismMain, BorderLayout.CENTER);
		
		el.add(new JLabel("Porcentaje"));
		el.add(new JTextField(3));
		el.setMaximumSize(el.getPreferredSize());
		el.setMinimumSize(el.getPreferredSize());
		el.setVisible(false);
		elitism.add(el, BorderLayout.PAGE_END);
		
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
	/*
	public ConfigPanel<TransferGeneticAlgorithm> creaPanelConfiguracion() {
		String[] funciones = new String[] { "función 1", "función 2", "función 3", "función 4", "función 5" };
		Check[] check = new Check[] {new Si(), new No()};
		String[] metodoSeleccion = new String[] { "ruleta", "torneo" };
		String[] metodoCruce = new String[] { "un punto bit a bit", "un punto", "discreto uniforme", "aritmético", "SBX" };
		
		ConfigPanel<TransferGeneticAlgorithm> config = new ConfigPanel<TransferGeneticAlgorithm>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Función",							 // etiqueta 
			    "Función a optimizar",				 // tooltip
			    "funcion",   							 // campo (debe haber un getColor y un setColor)
			    funciones))                            // elecciones posibles
		
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"# param func 4", 					     // texto a usar como etiqueta del campo
				"Número de variables de la función 4",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"paramFunc4",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Cromosoma reales",					 // etiqueta
				"Cambia de cadenas de bits a reales, para todas las funciones",            // tooltip
				"cromosomaReal",	             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Selección",							 // etiqueta 
			    "Método de selección",				 // tooltip
			    "seleccion",   							 // campo (debe haber un getColor y un setColor)
			    metodoSeleccion))                            // elecciones posibles
		.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Cruce",							 // etiqueta 
			    "Método de cruce",				 // tooltip
			    "cruce",   							 // campo (debe haber un getColor y un setColor)
			    metodoCruce))                            // elecciones posibles
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "Precisión",	 					 // etiqueta
			    "Precisión decimal para las cadenas binarias",				 // tooltip
			    "precision",	                     // campo
			    0, Double.POSITIVE_INFINITY))	     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY)
		  		  
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Población", 					     // texto a usar como etiqueta del campo
				"Número de individuos",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"poblacion",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Generaciones",					     // texto a usar como etiqueta del campo
				"Número de iteraciones",       // texto a usar como 'tooltip' cuando pasas el puntero
				"generaciones",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "% cruces", 						 // etiqueta
			    "",           // tooltip
			    "porcCruces",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "% mutación", 						 // etiqueta
			    "",           // tooltip
			    "porcMutacion",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Semilla manual",					 // etiqueta
				"Semilla propia para el generador aleatorio",                // tooltip
				"semillaPersonalizada",             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Semilla",						     // texto a usar como etiqueta del campo
				"Semilla usada cuando se selecciona semilla personalizada",       // texto a usar como 'tooltip' cuando pasas el puntero
				"semilla",						     // campo (espera que haya un getGrosor y un setGrosor)
				0, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		
		.addOption(new ConfigPanel.StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"forma",							 // etiqueta
				"forma de la figura",                // tooltip
				"forma",                             // campo
				formas))                             // elecciones (deben implementar Cloneable)
				
			  // para cada clase de objeto interno, hay que definir sus opciones entre un beginInner y un endInner 
			  .beginInner(new ConfigPanel.InnerOption<TransferGeneticAlgorithm,Forma>( 
			  	"rectangulo", "opciones del rectangulo", "forma", Rectangulo.class))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "ancho", "ancho del rectangulo", "ancho", 0, Double.POSITIVE_INFINITY))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "alto", "alto del rectangulo", "alto", 0, Double.POSITIVE_INFINITY))
		  	  .endInner()
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Elitismo",							 // etiqueta
				"Usar elitismo en la población",                // tooltip
				"elitismo",                             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.beginInner(new InnerOption<TransferGeneticAlgorithm,Percentage>(
			  	"% de elitismo", "", "porcElite", Percentage.class))
		  		  .addInner(new DoubleOption<Double>(
		  		     "", "", "perc", 0, 1))
		  		  .endInner()
		.endOptions();
		
		return config;
	}
	
	public static abstract class Check implements Cloneable {
		protected boolean opcion;
		public abstract void dibuja();
		
		// implementacion de 'clone' por defecto, suficiente para objetos sencillos
		public Check clone() { 
			try {
				return (Check)super.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalArgumentException(e);
			} 
		}

		public boolean getOpcion() {
			return opcion;
		}

		public void setOpcion(boolean opcion) {
			this.opcion = opcion;
		}
	}
	
	public static class Si extends Check {
		
		public Si() {
			super();
			this.opcion = true;
		}
		
		public void dibuja() {  ...  };
		
		public String toString() {
			return "Sí"; 
		}
	}
	
	public static class No extends Check {
		
		public No() {
			super();
			this.opcion = false;
		}
		
		public void dibuja() {  ...  };
		
		public String toString() {
			return "No"; 
		}
	}
	
	public static class Percentage {
		private double perc;

		public double getPerc() {
			return perc;
		}

		public void setPerc(double perc) {
			this.perc = perc;
		}
	}
	
	*//** una forma; implementa cloneable *//*
	public static abstract class Forma implements Cloneable {			
		public abstract void dibuja();
		
		// implementacion de 'clone' por defecto, suficiente para objetos sencillos
		public Forma clone() { 
			try {
				return (Forma)super.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalArgumentException(e);
			} 
		}
	}
	
	*//** un rectangulo (una forma, y por tanto 'cloneable') *//*
	public static class Rectangulo extends Forma {
		private double ancho = 1, alto = 1;
	
		public double getAncho() { return ancho; }
		public void setAncho(double ancho) { this.ancho = ancho; }
		public double getAlto() { return alto; }
		public void setAlto(double alto) { this.alto = alto; }

		public void dibuja() {  ...  };

		public String toString() {
			return "rect. de " + ancho  + "x" + alto; 
		}
	}*/

}
