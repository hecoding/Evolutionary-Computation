package view.gui.swing;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import controller.Controller;
import model.observer.GeneticAlgorithmObserver;

public class CenterPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private StatusBarPanel status;
 	JPanel centerPanel;
 	Plot2DPanel plot;
 	JPanel runButtonPanel;
 	
	public CenterPanel(Controller ctrl, StatusBarPanel status) {
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
		
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setVisible(false);
		this.add(plot, BorderLayout.CENTER);
		
		this.add(this.status, BorderLayout.PAGE_END);
	}

	@Override
	public void onStartRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				plot.setVisible(false);
			}
		});
	}

	@Override
	public void onEndRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				plot.removeAllPlots();
				plot.addLinePlot("Mejor absoluto", ctrl.getBestChromosomeList());
				plot.addLinePlot("Mejor de la generación", ctrl.getBestAptitudeList());
				plot.addLinePlot("Media de la generación", ctrl.getAverageAptitudeList());
				plot.setVisible(true);
			}
		});
	}
}
