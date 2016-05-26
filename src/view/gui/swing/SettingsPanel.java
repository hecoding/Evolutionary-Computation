package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.chromosome.exception.IllegalChromosomeException;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.ConfigPanel.ChoiceOption;
import view.gui.swing.ConfigPanel.ConfigListener;
import view.gui.swing.ConfigPanel.DoubleOption;
import view.gui.swing.ConfigPanel.InnerOption;
import view.gui.swing.ConfigPanel.IntegerOption;
import view.gui.swing.ConfigPanel.StrategyOption;

public class SettingsPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private TransferGeneticAlgorithm transfer;
 	private StatusBarPanel status;
 	private JPanel buttonPanel;
 	JButton runButton;

	public SettingsPanel(Controller ctrl, StatusBarPanel status) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.transfer = new TransferGeneticAlgorithm();
		this.transfer = ctrl.getParameters();
		this.status = status;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder("Settings"));
		
		final ConfigPanel<TransferGeneticAlgorithm> settings = creaPanelConfiguracion();
		settings.setTarget(transfer);
		settings.initialize();
		settings.addConfigListener(new ConfigListener() {
			@Override
			public void configChanged(boolean isConfigValid) {
				if (!isConfigValid) {
					runButton.setEnabled(false);
					status.setErrors(true);
				}
				else {
					status.setErrors(false);
					if (!runButton.isEnabled())
						runButton.setEnabled(true);
				}
			}
		});
		this.add(settings, BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		runButton = new JButton();
		JButton doSomth = new JButton();
		runButton.setText("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.setParameters(transfer);
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
		buttonPanel.add(runButton, BorderLayout.PAGE_START);
		doSomth.setText("show args. in console");
		doSomth.setToolTipText("Hi");
		doSomth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(ctrl.getParameters());
			}
		});
		//buttonPanel.add(doSomth, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// maybe wrap around a JScrollPane and/or JSplitPane
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
	
	public ConfigPanel<TransferGeneticAlgorithm> creaPanelConfiguracion() {
		String[] funciones = new String[] { "function 1", "function 2", "function 3", "function 4", "function 5" };
		Check[] check = new Check[] {new Yes(), new No()};
		String[] metodoSeleccion = new String[] { "roulette", "tournament" };
		String[] metodoCruce = new String[] { "one-point bit-to-bit", "one-point", "uniform", "arithmetic", "SBX" };
		
		ConfigPanel<TransferGeneticAlgorithm> config = new ConfigPanel<TransferGeneticAlgorithm>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Function",							 // etiqueta 
			    "Function to optimize",				 // tooltip
			    "function",   							 // campo (debe haber un getColor y un setColor)
			    funciones))                            // elecciones posibles
		
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"func 4 param num", 					     // texto a usar como etiqueta del campo
				"Number of parameter for function 4",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"paramFunc4",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Real chroms.",					 // etiqueta
				"Use floating-point numbers instead of bit strings",            // tooltip
				"realChromosome",	             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Selection",							 // etiqueta 
			    "Selection method",				 // tooltip
			    "selection",   							 // campo (debe haber un getColor y un setColor)
			    metodoSeleccion))                            // elecciones posibles
		.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Crossover",							 // etiqueta 
			    "Crossover method",				 // tooltip
			    "crossover",   							 // campo (debe haber un getColor y un setColor)
			    metodoCruce))                            // elecciones posibles
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "Precision",	 					 // etiqueta
			    "Decimal precision for binary strings",				 // tooltip
			    "precision",	                     // campo
			    0, Double.POSITIVE_INFINITY))	     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY)
		  		  
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Population size", 					     // texto a usar como etiqueta del campo
				"Number of chromosomes",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"populationSize",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Generations",					     // texto a usar como etiqueta del campo
				"Number of iterations",       // texto a usar como 'tooltip' cuando pasas el puntero
				"generations",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "Crossover prob", 						 // etiqueta
			    "",           // tooltip
			    "crossoverProb",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "Mutation prob", 						 // etiqueta
			    "",           // tooltip
			    "mutationProb",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Custom seed",					 // etiqueta
				"to feed the random generator",                // tooltip
				"customSeed",             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Seed",						     // texto a usar como etiqueta del campo
				"Used seed when checking custom seed",       // texto a usar como 'tooltip' cuando pasas el puntero
				"seed",						     // campo (espera que haya un getGrosor y un setGrosor)
				0, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		
		/*.addOption(new ConfigPanel.StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
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
		  	  .endInner()*/
		.addOption(new StrategyOption<TransferGeneticAlgorithm>( // -- eleccion de objeto configurable
				"Elitism",							 // etiqueta
				"Use elitism in the population",                // tooltip
				"elitism",                             // campo
				check))                             // elecciones (deben implementar Cloneable)
		.beginInner(new InnerOption<TransferGeneticAlgorithm,Percentage>(
			  	"Elitism rate", "", "eliteRate", Percentage.class))
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
	
	public static class Yes extends Check {
		
		public Yes() {
			super();
			this.opcion = true;
		}
		
		public void dibuja() { /* ... */ };
		
		public String toString() {
			return "Yes"; 
		}
	}
	
	public static class No extends Check {
		
		public No() {
			super();
			this.opcion = false;
		}
		
		public void dibuja() { /* ... */ };
		
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
	
	/** una forma; implementa cloneable */
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
	
	/** un rectangulo (una forma, y por tanto 'cloneable') */
	public static class Rectangulo extends Forma {
		private double ancho = 1, alto = 1;
	
		public double getAncho() { return ancho; }
		public void setAncho(double ancho) { this.ancho = ancho; }
		public double getAlto() { return alto; }
		public void setAlto(double alto) { this.alto = alto; }

		public void dibuja() { /* ... */ };

		public String toString() {
			return "rect. de " + ancho  + "x" + alto; 
		}
	}

}
