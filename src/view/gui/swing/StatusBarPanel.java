package view.gui.swing;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.observer.GeneticAlgorithmObserver;

public class StatusBarPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JLabel result;
	private JLabel status;
	private JLabel errors;
	
	public StatusBarPanel(Controller ctrl) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.result = new JLabel();
		this.status = new JLabel();
		this.errors = new JLabel();
		this.errors.setForeground(Color.red);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}
	
	private void initGUI() {
		this.add(this.errors);
		this.add(this.status);
		this.add(this.result);
	}
	
	public void setErrors(Boolean b) {
		if(b)
			this.errors.setText("Hay errores");
		else
			this.errors.setText("");
	}
	
	public void setStatus(String s) {
		this.status.setText(s);
	}

	@Override
	public void onStartRun() {
	}

	@Override
	public void onEndRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String s = new String();
				for (Double res : ctrl.getResult()) {
					s = s + String.format("%.4f", res) + ", ";
				}
				s = s.substring(0, s.length() - 2);
				if(ctrl.getResult().size() > 1)
					s = "Mejor: " + "[" + s + "]";
				else
					s = "Mejor: " + s;
				
				s = s + " Resultado: " + String.format("%.4f", ctrl.getFunctionResult());
				result.setText(s);
			}
		});
	}
}
