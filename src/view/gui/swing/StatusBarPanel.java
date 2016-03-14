package view.gui.swing;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.observer.GeneticAlgorithmObserver;

public class StatusBarPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JLabel status;
	
	public StatusBarPanel(Controller ctrl) {
		this.ctrl = ctrl;
		this.status = new JLabel();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}
	
	private void initGUI() {
		this.add(this.status);
	}
	
	public void setStatus(String s) {
		this.status.setText(s);
	}

	@Override
	public void onStartRun() {
	}

	@Override
	public void onEndRun() {
	}
}
