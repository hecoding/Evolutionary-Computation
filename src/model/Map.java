package model;

import java.util.ArrayList;

public class Map implements Cloneable {
	private ArrayList<ArrayList<CellType>> map;
	
	public Map(ArrayList<ArrayList<CellType>> cells) {
		this.map = cells;
	}
	
	public int getRows() {
		return this.map.size();
	}
	
	public int getColumns() {
		return this.map.get(0).size();
	}
	
	public CellType get(int row, int column) {
		return this.map.get(row).get(column);
	}
	
	public Map clone() {
		ArrayList<ArrayList<CellType>> newRows = new ArrayList<ArrayList<CellType>>(this.getRows());
		
		for (ArrayList<CellType> originalRow : this.map) {
			ArrayList<CellType> copyRow = new ArrayList<>(this.getColumns());
			for (CellType column : originalRow) {
				copyRow.add(column);
			}
			newRows.add(copyRow);
		}
		
		return new Map(newRows);
	}
	
	public static enum CellType {
		beginning, nothing, food, eatenfood, trail
	}
}
