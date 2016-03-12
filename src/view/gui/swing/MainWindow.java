package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import model.geneticAlgorithm.TransferGeneticAlgorithm;
import view.gui.swing.ConfigPanel.*;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private TransferGeneticAlgorithm transfer = new TransferGeneticAlgorithm();
	
	public MainWindow() {
		this.setTitle("Evolutionary computation");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.add(mainPanel);
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new BorderLayout());
		
		//----------Content-----------
		
		leftPanel.setBackground(Color.BLUE);
		final ConfigPanel<TransferGeneticAlgorithm> settings = creaPanelConfiguracion();
		transfer.setDefault();
		settings.setTarget(transfer);
		settings.initialize();
		leftPanel.add(settings);
		JButton doSomth = new JButton("surprise");
		doSomth.setToolTipText("Hi");
		leftPanel.add(doSomth, BorderLayout.PAGE_END);
		doSomth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(transfer);
			}
		});
		// meter quiza un JScrollPane y/o JSplitPane
		mainPanel.add(leftPanel, BorderLayout.LINE_START);
		
		centerPanel.setBackground(Color.GREEN);
		// define your data
		double[] y = { 45, 89, 6, 32, 63, 12, 15, 23, 12, 34 };
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		// define the legend position
		plot.addLegend("SOUTH");
		// add a line plot to the PlotPanel
		plot.addLinePlot("my plot", y);
		// put the PlotPanel in a JFrame like a JPanel
		centerPanel.add(plot, BorderLayout.CENTER);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		//----------Settings-----------
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				//closeOperation();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
			
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1024,768));
		this.setMinimumSize(new Dimension(750, 550));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
