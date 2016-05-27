package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import controller.Controller;
import model.observer.GeneticAlgorithmObserver;

public class StatusBarPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JTextArea outputTextArea;
	private Color defaultColor = new Color(245,245,245);
	
	public StatusBarPanel(Controller ctrl) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}
	
	private void initGUI() {
		outputTextArea = new JTextArea();
		outputTextArea.setEditable(false);
		outputTextArea.setBackground(defaultColor);
		outputTextArea.setLineWrap(true);
		outputTextArea.setWrapStyleWord(true);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
		this.setVisible(false);
	}
	
	public void setErrors(Boolean b) {
		if(b) {
			this.outputTextArea.setForeground(Color.red);
			this.outputTextArea.setText("There are errors");
			this.setVisible(true);
		}
		else {
			this.outputTextArea.setForeground(defaultColor);
			this.outputTextArea.setText("");
			this.setVisible(false);
		}
	}
	
	public void setStatus(String s) {
		this.outputTextArea.setForeground(Color.black);
		this.outputTextArea.setText(s);
		if(s.equals(""))
			this.setVisible(false);
		else
			this.setVisible(true);
	}

	@Override
	public void onStartRun() {
	}

	@Override
	public void onEndRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(ctrl.isFinished()) {
					String s = new String("");
					if(ctrl.isRangeParameters()) {
						double[] x = ctrl.getRangeList();
						ArrayList<Double> y = ctrl.getRangeResults();
						Double best;
						if(ctrl.isMinimization()) best = Collections.min(y);
						else best = Collections.max(y);
						int idx = y.indexOf(best);
						
						s += "El mejor resultado es " + best.intValue() + ", con el parámetro " + String.format("%.2f", x[idx]);
					}
					setStatus(s);
				}
			}
		});
	}
	
	@Override
	public void onIncrement(int n) {
		
	}
}
