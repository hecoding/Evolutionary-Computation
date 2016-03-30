package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.geneticAlgorithm.TransferGeneticAlgorithm;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Controller ctrl;
	private CenterPanel centerPanel;
	private SettingsPanel settingsPanel;
	private StatusBarPanel status;
	
	public MainWindow(Controller controller) {
		ctrl = controller;
		
		this.setTitle("Evolutionary computation");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		this.status = new StatusBarPanel(ctrl);
		this.centerPanel = new CenterPanel(ctrl, this.status);
		this.settingsPanel = new SettingsPanel(ctrl, this.status);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(settingsPanel, BorderLayout.LINE_START);
		this.add(mainPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1024,668));
		this.setMinimumSize(new Dimension(750, 550));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
