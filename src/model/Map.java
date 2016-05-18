package model;

import java.util.ArrayList;

import model.Ant.Position;

public class Map implements Cloneable {
	private ArrayList<ArrayList<CellType>> map;
	private int foodHere;
	
	public Map(ArrayList<ArrayList<CellType>> cells, int food) {
		this.map = cells;
		this.foodHere = food;
	}
	
	public int getRows() {
		return this.map.size();
	}
	
	public int getColumns() {
		return this.map.get(0).size();
	}
	
	public int getFoodHere() {
		return this.foodHere;
	}
	
	public CellType get(int row, int column) {
		return this.map.get(row).get(column);
	}
	
	public CellType get(Position pos) {
		return get(pos.x, pos.y);
	}
	
	public void set(CellType cell, int row, int column) {
		ArrayList<CellType> currentRow = this.map.get(row);
		currentRow.set(column, cell);
		this.map.set(row, currentRow);
	}
	
	public void set(CellType cell, Position pos) {
		set(cell, pos.x, pos.y);
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
		
		return new Map(newRows, this.foodHere);
	}
	
	public static enum CellType {
		beginning, nothing, food, eatenfood, trail
	}
}
