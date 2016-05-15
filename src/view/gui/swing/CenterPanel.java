package view.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.math.plot.Plot2DPanel;

import controller.Controller;
import model.Map;
import model.genProgAlgorithm.AntTrailGeneticAlgorithm;
import model.observer.GeneticAlgorithmObserver;

public class CenterPanel extends JPanel implements GeneticAlgorithmObserver {
	private static final long serialVersionUID = 1L;
 	private Controller ctrl;
 	private JTabbedPane tabs;
 	private JPanel graphPanel;
 	private JPanel mapPanel;
 	private JPanel programPanel;
 	private StatusBarPanel status;
 	JPanel centerPanel;
 	
 	AntTrailPane map;
 	Plot2DPanel plot;
 	JTextArea programText;
 	JPanel runButtonPanel;
 	
	public CenterPanel(Controller ctrl, StatusBarPanel status) {
		this.ctrl = ctrl;
		this.ctrl.addModelObserver(this);
		this.status = status;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI();
			}
		});
	}

	private void initGUI() {
		mapPanel = new JPanel(new BorderLayout());
		graphPanel = new JPanel(new BorderLayout());
		programPanel = new JPanel(new BorderLayout());
		tabs = new JTabbedPane();
		this.setLayout(new BorderLayout());
		
		map = new AntTrailPane();
		mapPanel.add(map, BorderLayout.CENTER);
		tabs.add("Mapa", mapPanel);
		
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		graphPanel.add(plot, BorderLayout.CENTER);
		tabs.add("Aptitud", graphPanel);
		
		programText = new JTextArea();
		programText.setEditable(false);
		programText.setLineWrap(true);
		programText.setWrapStyleWord(true);
		programPanel.setLayout(new BorderLayout());
		programPanel.add(new JScrollPane(programText), BorderLayout.CENTER);
		tabs.add("Programa", programPanel);
		
		tabs.setMnemonicAt(0, KeyEvent.VK_1);
		tabs.setMnemonicAt(1, KeyEvent.VK_2);
		tabs.setMnemonicAt(2, KeyEvent.VK_3);
		
		this.add(tabs, BorderLayout.CENTER);
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
				updateMapPanel();
				updateGraphPanel();
				updateProgramPanel();
			}
		});
	}
	
	private void updateMapPanel() {
		map = new AntTrailPane(ctrl.getResultMap());
	}
	
	private void updateGraphPanel() {
		plot.removeAllPlots();
		
		if(!ctrl.isRangeParameters()) {
			double[] avgApt = ctrl.getAverageAptitudeList();
			plot.addLinePlot("Mejor absoluto", ctrl.getBestChromosomeList());
			plot.addLinePlot("Mejor de la generación", ctrl.getBestAptitudeList());
			plot.addLinePlot("Media de la generación", avgApt);
			plot.setFixedBounds(0, 0, avgApt.length);
		}
		else {
			double[] range = ctrl.getRangeList();
			plot.addLinePlot("Distancia", Color.red, range, ctrl.getResultsList());
			plot.setFixedBounds(0, range[0], range[range.length - 1]);
		}
		
		plot.setVisible(true);
	}
	
	private void updateProgramPanel() {
		String s = new String("");
		s += " Resultado: " + new Double(ctrl.getFunctionResult()).intValue() + System.lineSeparator();
		s += "Mejor:" + System.lineSeparator();
		s += ctrl.getResult();
		
		programText.setText(s);
	}
	
	public class AntTrailPane extends JPanel {
		private static final long serialVersionUID = 1L;
		private int columnCount;
		private int rowCount;
		private Map map;
		private Color white = new Color(250,250,250);
		private Color black = new Color(33,33,33);
		private Color green = new Color(0,230,118);
		private Color yellow = new Color(251,192,45);
		private Color orange = new Color(251,140,0);
		private Color gray = new Color(158,158,158);

		public AntTrailPane() {
			this.map = AntTrailGeneticAlgorithm.getMap();
			this.rowCount = this.map.getRows();
			this.columnCount = this.map.getColumns();
		}
		
		public AntTrailPane(Map map) {
			this.map = map;
			this.rowCount = this.map.getRows();
			this.columnCount = this.map.getColumns();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, 200);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();

			int width = getWidth();
			int height = getHeight();

			int cellWidth = width / columnCount;
			int cellHeight = height / rowCount;

			int xOffset = (width - (columnCount * cellWidth)) / 2;
			int yOffset = (height - (rowCount * cellHeight)) / 2;

			for (int row = 0; row < rowCount; row++) {
				for (int col = 0; col < columnCount; col++) {
					
					switch(this.map.get(row, col)) {
					case nothing:
						g.setColor(white);
						break;
					case food:
						g.setColor(black);
						break;
					case trail:
						g.setColor(yellow);
						break;
					case eatenfood:
						g.setColor(orange);
						break;
					case beginning:
						g.setColor(green);
						break;
					default:
						break;
					}
					
					g.fillRect(
							xOffset + (col * cellWidth),
							yOffset + (row * cellHeight),
							cellWidth,
							cellHeight);
					
					g.setColor(gray);
					g.drawRect(
							xOffset + (col * cellWidth),
							yOffset + (row * cellHeight),
							cellWidth,
							cellHeight);
				}
			}

			g2d.dispose();
		}
	}

}
