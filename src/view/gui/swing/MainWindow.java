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
}
