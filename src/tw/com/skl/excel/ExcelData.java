package tw.com.skl.excel;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelData {
	
	public enum ColumnType {
		NONE,				//ex: Value
		RELATIVE_ALL,		//ex: A3
		RELATIVE_ROW,		//ex: $A3
		RELATIVE_CELL,		//ex: A$3
		ABSOLUTE_COLUMN		//ex: $A$3
	}
	
	public String[] statements;
	public String[][] table;
	public int rowCount;
	public int currentRow;
	public int currentCell;
	public int firstRow;
	public int firstCell;
	public HashMap<String, String> map;
	
	public ExcelData(String[][] table, String[] statements, HashMap<String, String> map, int currentCell, int currentRow) {
		this.statements = statements;
		this.table = table;
		this.rowCount = table.length;
		this.map = map;
		this.currentRow = currentRow;
		this.currentCell = currentCell;
		this.firstRow = currentRow;
		this.firstCell = currentCell;
	}
	
	public int getCell(String column) {
		Pattern p = Pattern.compile("[A-Z]{1,2}");
		Matcher m = p.matcher(column);
		if (m.find()) {
			char[] cs = m.group().toCharArray();
			int number = 0;
			for(int i = 0 ; i < cs.length ; i++) {
				char c = cs[i];
				number = i * 26 + (short)c - 65; 
			}
			return number;
		}
		
		return -1;
	}
	
	public int getRow(String column) {
		Pattern p = Pattern.compile("[1-9]{1,2}");
		Matcher m = p.matcher(column);
		if (m.find()) {
			return Integer.parseInt(m.group());
		}
		
		return -1;
	}
	
	public String getColumnType(String column) {
		Pattern p = Pattern.compile("^(\\$)([A-Z]{1,2})(\\d{1,3})$");
		Matcher m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_ROW
			int cell = getCell(m.group());
			int row = getRow(m.group()) - this.firstRow + this.currentRow;
			
//			Log.d("cell " + m.group() + ": " + cell + ",row " + row);
			
			return this.table[cell][row];
		}
		
		p = Pattern.compile("^([A-Z]{1,2})(\\$)(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_CELL
			int cell = getCell(m.group()) - this.firstCell + this.currentCell;
			int row = getRow(m.group());
			
//			Log.d("cell " + m.group() + ": " + cell + ",row " + row);
			
			return this.table[cell][row];
		}
		
		p = Pattern.compile("^\\$([A-Z]{1,2})\\$(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// ABSOLUTE_COLUMN
			int cell = getCell(m.group());
			int row = getRow(m.group());
			
//			Log.d("cell " + m.group() + ": " + cell + ",row " + row);
			
			return this.table[cell][row];
		}
		
		p = Pattern.compile("([A-Z]{1,2})(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_ALL
			int cell = getCell(m.group()) - this.firstCell + this.currentCell;
			int row = getRow(m.group()) - this.firstRow + this.currentRow;
			
//			Log.d("cell " + m.group() + ": " + cell + ",row " + row);
			
			return this.table[cell][row];
		}
		
		return column;
	}
}