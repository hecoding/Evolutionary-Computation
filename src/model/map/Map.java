package model.map;

import java.io.IOException;
import java.util.ArrayList;

import util.MapParser;

public class Map extends ArrayList<ArrayList<MapEnum>> {
	private static final long serialVersionUID = 1L;
	private static Map master;
	
	public int getRows() {
		return this.size();
	}
	
	public int getColumns() {
		return this.get(0).size();
	}
	
	public MapEnum get(int row, int column) {
		return this.get(row).get(column);
	}
	
	public static Map getMasterMap() throws IOException {
		if(master == null)
			master = MapParser.parse("src/map.txt");
		
		return master;
	}
}
