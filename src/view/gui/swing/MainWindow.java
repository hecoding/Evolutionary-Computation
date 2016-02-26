package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public MainWindow() {
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
		JPanel centerPanel = new JPanel();
		
		//----------Content-----------
		
		leftPanel.setBackground(Color.BLUE);
		final ConfigPanel<Caca> cp = creaPanelConfiguracion();
		Caca caca = new Caca();
		cp.setTarget(caca);
		cp.initialize();
		leftPanel.add(cp);
		// meter quiza un JScrollPane y/o JSplitPane
		mainPanel.add(leftPanel, BorderLayout.LINE_START);
		
		centerPanel.setBackground(Color.GREEN);
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
		this.setSize(new Dimension(780,690));
		this.setMinimumSize(new Dimension(660, 550));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public ConfigPanel<Caca> creaPanelConfiguracion() {
		String[] colores = new String[] { "yeah", "nope", "maybe" };
		Forma[] formas = new Forma[] { new Rectangulo() };
		
		ConfigPanel<Caca> config = new ConfigPanel<Caca>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new ConfigPanel.IntegerOption<Caca>(  // -- entero
				"grosor (px)", 					     // texto a usar como etiqueta del campo
				"pixeles de grosor del borde",       // texto a usar como 'tooltip' cuando pasas el puntero
				"grosor",  						     // campo (espera que haya un getGrosor y un setGrosor)
				1, 10))							     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
		.addOption(new ConfigPanel.ChoiceOption<Caca>(	 // -- eleccion de objeto no-configurable
			    "color",							 // etiqueta 
			    "color del borde", 					 // tooltip
			    "color",   							 // campo (debe haber un getColor y un setColor)
			    colores))                            // elecciones posibles
		.addOption(new ConfigPanel.StrategyOption<Caca>( // -- eleccion de objeto configurable
				"forma",							 // etiqueta
				"forma de la figura",                // tooltip
				"forma",                             // campo
				formas))                             // elecciones (deben implementar Cloneable)
				
			  // para cada clase de objeto interno, hay que definir sus opciones entre un beginInner y un endInner 
			  .beginInner(new ConfigPanel.InnerOption<Caca,Forma>( 
			  	"rectangulo", "opciones del rectangulo", "forma", Rectangulo.class))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "ancho", "ancho del rectangulo", "ancho", 0, Double.POSITIVE_INFINITY))
		  		  .addInner(new ConfigPanel.DoubleOption<Forma>(
		  		     "alto", "alto del rectangulo", "alto", 0, Double.POSITIVE_INFINITY))
		  	  .endInner()
		.endOptions();
		
		return config;
	}
	
	public class Caca {
		int a;
		double b;
		private int grosor = 1;
		private String color;
		private Forma forma;
		
		public int getGrosor() {
			return grosor;
		}
		public void setGrosor(int grosor) {
			this.grosor = grosor;
		}
		
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public Forma getForma() {
			return forma;
		}
		public void setForma(Forma forma) {
			this.forma = forma;
		}
		public String toString() {
			return "grosor: " + this.grosor;
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
