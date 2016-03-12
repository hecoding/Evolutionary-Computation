package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.ButtonPeer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import controller.Controller;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;
import view.gui.swing.ConfigPanel.ChoiceOption;
import view.gui.swing.ConfigPanel.DoubleOption;
import view.gui.swing.ConfigPanel.IntegerOption;
import view.gui.swing.ConfigPanel.StrategyOption;

public class SettingsPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private TransferGeneticAlgorithm transfer;
 	JPanel buttonPanel;

	public SettingsPanel(Controller ctrl) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder("Ajustes"));
		this.transfer = new TransferGeneticAlgorithm();
		
		//this.setBackground(Color.BLUE);
		final ConfigPanel<TransferGeneticAlgorithm> settings = creaPanelConfiguracion();
		transfer = ctrl.getParameters();
		settings.setTarget(transfer);
		settings.initialize();
		this.add(settings, BorderLayout.CENTER);
		
		buttonPanel = new JPanel(new BorderLayout());
		JButton runButton = new JButton();
		JButton doSomth = new JButton();
		JButton meteParams = new JButton();
		runButton.setText("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.setParameters(transfer);
				ctrl.run();
			}
		});
		runButton.setVisible(false);
		buttonPanel.add(runButton, BorderLayout.PAGE_START);
		doSomth.setText("show in console");
		doSomth.setToolTipText("Hi");
		doSomth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(ctrl.getParameters());
			}
		});
		buttonPanel.add(doSomth, BorderLayout.CENTER);
		meteParams.setText("meter params");
		meteParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.setParameters(transfer);
			}
		});
		buttonPanel.add(meteParams, BorderLayout.PAGE_END);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// meter quiza un JScrollPane y/o JSplitPane
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
		String[] funciones = new String[] { "función 1", "función 2", "función 3", "función 4", "función 5" };
		Forma[] formas = new Forma[] { new Rectangulo() };
		Check[] check = new Check[] {new Si(), new No()};
		
		ConfigPanel<TransferGeneticAlgorithm> config = new ConfigPanel<TransferGeneticAlgorithm>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new ChoiceOption<TransferGeneticAlgorithm>(	 // -- eleccion de objeto no-configurable
			    "Función",							 // etiqueta 
			    "función a optimizar",				 // tooltip
			    "funcion",   							 // campo (debe haber un getColor y un setColor)
			    funciones))                            // elecciones posibles
		
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "Precisión",	 					 // etiqueta
			    "bla",				 // tooltip
			    "precision",	                     // campo
			    0, Double.POSITIVE_INFINITY))	     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY)
		  		  
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Población", 					     // texto a usar como etiqueta del campo
				"bla",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"poblacion",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Generaciones",					     // texto a usar como etiqueta del campo
				"bla",       // texto a usar como 'tooltip' cuando pasas el puntero
				"generaciones",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "% cruces", 						 // etiqueta
			    "bla",           // tooltip
			    "porcCruces",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new DoubleOption<TransferGeneticAlgorithm>(   // -- doble, parecido a entero
			    "% mutación", 						 // etiqueta
			    "bla",           // tooltip
			    "porcMutacion",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new IntegerOption<TransferGeneticAlgorithm>(  // -- entero
				"Semilla",						     // texto a usar como etiqueta del campo
				"bla",       // texto a usar como 'tooltip' cuando pasas el puntero
				"semilla",						     // campo (espera que haya un getGrosor y un setGrosor)
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
				"Elitismo",							 // etiqueta
				"bla			",                // tooltip
				"elitismo",                             // campo
				check))                             // elecciones (deben implementar Cloneable)
		
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
		
		public void dibuja() { /* ... */ };
		
		public String toString() {
			return "Sí"; 
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
