package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import controller.Controller;
import model.observer.GeneticAlgorithmObserver;

public class CenterPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	JPanel centerPanel;
 	Plot2DPanel plot;
 	JPanel runButtonPanel;
 	
	public CenterPanel(Controller ctrl) {
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
		
		this.setBackground(Color.GREEN);
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setVisible(false);
		this.add(plot, BorderLayout.CENTER);
		
		JButton runButton = new JButton();
		runButton.setText("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ctrl.setParameters(transfer); A VER QUE HAGO CON ESTO
				ctrl.run();
				runButtonPanel.setVisible(false);
			}
		});
		runButtonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints ();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		runButtonPanel.add(runButton, gbc);
		this.add(runButtonPanel, BorderLayout.PAGE_END);
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
				plot.addLinePlot("Media de la generación", ctrl.getAverageAptitudeList());
				plot.addLinePlot("Mejor de la generación", ctrl.getBestAptitudeList());
				plot.addLinePlot("Mejor absoluto", ctrl.getBestChromosomeList());
				plot.setVisible(true);
			}
		});
	}
}
