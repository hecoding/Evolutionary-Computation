package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Map;
import model.Map.CellType;

public class MapParser {
	
	public static Map parse(String fname) throws IOException {
		ArrayList<ArrayList<CellType>> cells = new ArrayList<ArrayList<CellType>>();
		BufferedReader br = new BufferedReader(new FileReader(fname));
		String thisLine;
		
        while ((thisLine = br.readLine()) != null) {
        	String[] columns = thisLine.split(" ");
        	ArrayList<CellType> row = new ArrayList<>(columns.length);
        	
           for (String cell : columns) {
        	   if(cell.equals("@"))
        		   row.add(CellType.beginning);
        	   else if (cell.equals("#"))
        		   row.add(CellType.food);
        	   else
        		   row.add(CellType.nothing);
           }
           cells.add(row);
        }
		
		br.close();
		
		return new Map(cells);
	}
	
}
