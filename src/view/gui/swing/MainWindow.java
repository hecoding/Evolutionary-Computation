package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		JPanel option1 = new JPanel();
		JLabel option1Label = new JLabel("holi");
		leftPanel.add(option1Label);
		JTextField option1TextField = new JTextField(8);
		//option1TextField.setInputVerifier(new IntegerInputVerifier());
		option1.add(option1Label);
		option1.add(option1TextField);
		option1.setMaximumSize( option1.getPreferredSize() );
		leftPanel.add(option1);
		option1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JPanel option2 = new JPanel();
		JLabel option2Label = new JLabel("holaaa");
		JTextField option2TextField = new JTextField(8);
		option2.add(option2Label);
		option2.add(option2TextField);
		option2.setMaximumSize( option2.getPreferredSize() );
		leftPanel.add(option2);
		option2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
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
