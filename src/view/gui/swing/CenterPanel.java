package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;

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
				
				if(!ctrl.isRangeParameters()) {
					double[] avgApt = ctrl.getAverageAptitudeList();
					plot.addLinePlot("Absolute best", ctrl.getBestChromosomeList());
				plot.addLinePlot("Best of generation", ctrl.getBestAptitudeList());
				plot.addLinePlot("Generation average", ctrl.getAverageAptitudeList());
					plot.setFixedBounds(0, 0, avgApt.length);
				}
				else {
					double[] range = ctrl.getRangeList();
					plot.addLinePlot("Distance", Color.red, range, ctrl.getResultsList());
					plot.setFixedBounds(0, range[0], range[range.length - 1]);
				}
				
				plot.setVisible(true);
			}
		});
	}
}
