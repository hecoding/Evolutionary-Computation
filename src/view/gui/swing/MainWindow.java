package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
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
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel(new BorderLayout());
		
		//----------Content-----------
		
		leftPanel.setBackground(Color.BLUE);
		final ConfigPanel<Modelo> cp = creaPanelConfiguracion();
		Modelo modelo = new Modelo();
		cp.setTarget(modelo);
		cp.initialize();
		leftPanel.add(cp);
		// meter quiza un JScrollPane y/o JSplitPane
		mainPanel.add(leftPanel, BorderLayout.LINE_START);
		
		centerPanel.setBackground(Color.GREEN);
		// define your data
		double[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		double[] y = { 45, 89, 6, 32, 63, 12, 15, 23, 12, 34 };
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		// define the legend position
		plot.addLegend("SOUTH");
		// add a line plot to the PlotPanel
		plot.addLinePlot("my plot", x, y);
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
	
	public ConfigPanel<Modelo> creaPanelConfiguracion() {
		String[] funciones = new String[] { "función 1", "función 2", "función 3", "función 4", "función 5" };
		Forma[] formas = new Forma[] { new Rectangulo() };
		
		ConfigPanel<Modelo> config = new ConfigPanel<Modelo>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new ConfigPanel.ChoiceOption<Modelo>(	 // -- eleccion de objeto no-configurable
			    "Función",							 // etiqueta 
			    "función a optimizar",				 // tooltip
			    "funcion",   							 // campo (debe haber un getColor y un setColor)
			    funciones))                            // elecciones posibles
		
		.addOption(new ConfigPanel.DoubleOption<Modelo>(   // -- doble, parecido a entero
			    "Precisión",	 					 // etiqueta
			    "bla",				 // tooltip
			    "precision",	                     // campo
			    0, Double.POSITIVE_INFINITY))	     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY)
		  		  
		.addOption(new ConfigPanel.IntegerOption<Modelo>(  // -- entero
				"Población", 					     // texto a usar como etiqueta del campo
				"bla",	         // texto a usar como 'tooltip' cuando pasas el puntero
				"poblacion",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new ConfigPanel.IntegerOption<Modelo>(  // -- entero
				"Generaciones",					     // texto a usar como etiqueta del campo
				"bla",       // texto a usar como 'tooltip' cuando pasas el puntero
				"generaciones",					     // campo (espera que haya un getGrosor y un setGrosor)
				1, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new ConfigPanel.DoubleOption<Modelo>(   // -- doble, parecido a entero
			    "% cruces", 						 // etiqueta
			    "bla",           // tooltip
			    "porcCruces",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new ConfigPanel.DoubleOption<Modelo>(   // -- doble, parecido a entero
			    "% mutación", 						 // etiqueta
			    "bla",           // tooltip
			    "porcMutacion",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
		.addOption(new ConfigPanel.IntegerOption<Modelo>(  // -- entero
				"Semilla",						     // texto a usar como etiqueta del campo
				"bla",       // texto a usar como 'tooltip' cuando pasas el puntero
				"semilla",						     // campo (espera que haya un getGrosor y un setGrosor)
				0, Integer.MAX_VALUE))			     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		
		/*.addOption(new ConfigPanel.StrategyOption<Modelo>( // -- eleccion de objeto configurable
				"forma",							 // etiqueta
				"forma de la figura",                // tooltip
				"forma",                             // campo
				formas))                             // elecciones (deben implementar Cloneable)
				
			  // para cada clase de objeto interno, hay que definir sus opciones entre un beginInner y un endInner 
			  .beginInner(new ConfigPanel.InnerOption<Modelo,Forma>( 
			  	"rectangulo", "opciones del rectangulo", "forma", Rectangulo.class))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "ancho", "ancho del rectangulo", "ancho", 0, Double.POSITIVE_INFINITY))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "alto", "alto del rectangulo", "alto", 0, Double.POSITIVE_INFINITY))
		  	  .endInner()*/
		
		.endOptions();
		
		return config;
	}
	
	public class Modelo {
		private String funcion;
		private Double precision;
		private int poblacion;
		private int generaciones;
		private double porcCruces;
		private double porcMutacion;
		private int semilla;
		
		public Modelo() {
			this.funcion = new String("función 1");
			this.precision = 0.001;
			this.poblacion = 100;
			this.generaciones = 100;
			this.porcCruces = 0.6;
			this.porcMutacion = 0.05;
			this.semilla = 0;
		}
		
		public String getFuncion() {
			return funcion;
		}

		public void setFuncion(String funcion) {
			this.funcion = funcion;
		}
		
		public double getPrecision() {
			return precision;
		}

		public void setPrecision(double precision) {
			this.precision = precision;
		}

		public int getPoblacion() {
			return poblacion;
		}

		public void setPoblacion(int poblacion) {
			this.poblacion = poblacion;
		}

		public int getGeneraciones() {
			return generaciones;
		}

		public void setGeneraciones(int generaciones) {
			this.generaciones = generaciones;
		}

		public double getPorcCruces() {
			return porcCruces;
		}

		public void setPorcCruces(double porcCruces) {
			this.porcCruces = porcCruces;
		}

		public double getPorcMutacion() {
			return porcMutacion;
		}

		public void setPorcMutacion(double porcMutacion) {
			this.porcMutacion = porcMutacion;
		}

		public int getSemilla() {
			return semilla;
		}

		public void setSemilla(int semilla) {
			this.semilla = semilla;
		}

		public String toString() {
			return "poner bien";
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
