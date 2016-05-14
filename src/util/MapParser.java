package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.map.Map;
import model.map.MapEnum;

public class MapParser {
	
	public static Map parse(String fname) throws IOException {
		Map ret = new Map();
		BufferedReader br=new BufferedReader(new FileReader(fname));
		String thisLine;
		
        while ((thisLine = br.readLine()) != null) {
        	ArrayList<MapEnum> row = new ArrayList<>();
        	
           for (String cell : thisLine.split(" ")) {
        	   if(cell.equals("@"))
        		   row.add(MapEnum.beginning);
        	   else if (cell.equals("#"))
        		   row.add(MapEnum.food);
        	   else
        		   row.add(MapEnum.nothing);
           }
           ret.add(row);
        }
		
		br.close();
		return ret;
	}
	
}
