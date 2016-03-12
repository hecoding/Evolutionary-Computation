package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import controller.Controller;

public class CenterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	
	public CenterPanel(Controller ctrl) {
		this.ctrl = ctrl;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.setBackground(Color.GREEN);
		// define your data
		double[] y = { 45, 89, 6, 32, 63, 12, 15, 23, 12, 34 };
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		// define the legend position
		plot.addLegend("SOUTH");
		// add a line plot to the PlotPanel
		plot.addLinePlot("my plot", y);
		// put the PlotPanel in a JFrame like a JPanel
		this.add(plot, BorderLayout.CENTER);
	}
}
