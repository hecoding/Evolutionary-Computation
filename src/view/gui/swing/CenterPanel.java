package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import controller.Controller;
import model.geneticAlgorithm.TransferGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;

public class CenterPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private TransferGeneticAlgorithm transfer;
 	private StatusBarPanel status;
 	JPanel centerPanel;
 	Plot2DPanel plot;
 	JPanel runButtonPanel;
 	
	public CenterPanel(Controller ctrl, TransferGeneticAlgorithm transfer, StatusBarPanel status) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.transfer = transfer;
		this.status = status;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		CenterPanel that = this;// so we can use it into runButton callback
		
		this.setBackground(Color.GREEN);
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setVisible(false);
		
		JButton runButton = new JButton();
		runButton.setText("Lanzar");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.setParameters(that.transfer);
				ctrl.run();
				runButtonPanel.setVisible(false);
				that.add(plot, BorderLayout.CENTER);
			}
		});
		runButtonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints ();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		runButtonPanel.add(runButton, gbc);
		this.add(runButtonPanel, BorderLayout.CENTER);
		
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
				plot.addLinePlot("Media de la generación", ctrl.getAverageAptitudeList());
				plot.addLinePlot("Mejor de la generación", ctrl.getBestAptitudeList());
				plot.addLinePlot("Mejor absoluto", ctrl.getBestChromosomeList());
				plot.setVisible(true);
				
				status.setStatus("Mejor: " + String.format("%.4f", ctrl.getResult()));
			}
		});
	}
}
