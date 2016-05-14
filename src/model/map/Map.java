package model.map;

import java.util.ArrayList;

public class Map extends ArrayList<ArrayList<MapEnum>> {
	private static final long serialVersionUID = 1L;
	
	public int getRows() {
		return this.size();
	}
	
	public int getColumns() {
		return this.get(0).size();
	}
	
	public MapEnum get(int row, int column) {
		return this.get(row).get(column);
	}
}
