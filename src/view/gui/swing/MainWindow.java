package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Controller ctrl;
	private CenterPanel centerPanel;
	private SettingsPanel settingsPanel;
	
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
		this.centerPanel = new CenterPanel(ctrl);
		this.settingsPanel = new SettingsPanel(ctrl);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(settingsPanel, BorderLayout.LINE_START);
		this.add(mainPanel);
		
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
	
}
