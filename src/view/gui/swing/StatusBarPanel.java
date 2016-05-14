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
import model.genProgAlgorithm.TSPGeneticAlgorithm;
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
	}
	
	public void setErrors(Boolean b) {
		if(b) {
			this.outputTextArea.setForeground(Color.red);
			this.outputTextArea.setText("Hay errores");
		}
		else {
			this.outputTextArea.setForeground(defaultColor);
			this.outputTextArea.setText("");
		}
	}
	
	public void setStatus(String s) {
		this.outputTextArea.setForeground(Color.black);
		this.outputTextArea.setText(s);
	}

	@Override
	public void onStartRun() {
	}

	@Override
	public void onEndRun() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String s = new String();
				if(!ctrl.isRangeParameters()) {
					for (Double res : ctrl.getResult()) {
						s = s + TSPGeneticAlgorithm.cityNames.values()[res.intValue()] + ", ";
					}
					s = s.substring(0, s.length() - 2);
					if(ctrl.getResult().size() > 1)
						s = "Mejor: " + "[" + s + "]";
					else
						s = "Mejor: " + s;
					
					s = s + " Resultado: " + new Double(ctrl.getFunctionResult()).intValue();
				}
				else {
					double[] x = ctrl.getRangeList();
					ArrayList<Double> y = ctrl.getRangeResults();
					Double best = Collections.min(y);
					int idx = y.indexOf(best);
					
					s += "El mejor resultado es " + best.intValue() + ", con el parámetro " + String.format("%.2f", x[idx]);
				}
				setStatus(s);
			}
		});
	}
}
