package tw.com.dh.excel;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.com.dh.utility.Log;

public class ExcelData {
	
	public enum ColumnType {
		NONE,				//ex: Value
		RELATIVE_ALL,		//ex: A3
		RELATIVE_ROW,		//ex: $A3
		RELATIVE_CELL,		//ex: A$3
		ABSOLUTE_COLUMN		//ex: $A$3
	}
	
	public HashMap<String, String[][]> sheets;
	
	public String[] statements;
	public String[][] table;
	
	public int rowCount, colCount;
	public int currentRow, currentCol;
	public int firstRow, firstCol;
	
	public ExcelData() {}
	
	public ExcelData(HashMap<String, String[][]> sheet, String name, String[] statements, int currentCol, int currentRow) {
		this.statements = statements;
		this.sheets = sheet;
		this.table = this.sheets.get(name);
		
		this.colCount = table.length;
		this.rowCount = table[0].length;
		
		this.currentCol = currentCol;
		this.currentRow = currentRow;
		
		this.firstCol = currentCol;
		this.firstRow = currentRow;
		
		for(int i = 0 ; i < this.table.length ; i++)
			for(int j = 0 ; j < this.table[i].length ; j ++) 
				this.table[i][j] = "";
		
	}
	
	public String[][] getTable(String sheetName) {
		return this.sheets.get(sheetName);
	}
	
	public int getCol(String column) {
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
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(column);
		if (m.find()) {
			return Integer.parseInt(m.group());
		}
		
		return -1;
	}
	
	public int[] getColumnIndex(String column) {
		Pattern p = Pattern.compile("^(\\$)([A-Z]{1,2})(\\d{1,3})$");
		Matcher m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_ROW
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1 - this.firstRow + this.currentRow;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("^([A-Z]{1,2})(\\$)(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_CELL
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("^\\$([A-Z]{1,2})\\$(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// ABSOLUTE_COLUMN
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("([A-Z]{1,2})(\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_ALL
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1 - this.firstRow + this.currentRow;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		return null;
	}
	
	public String getColumnType(String column) {
		int[] index = this.getColumnIndex(column);
		return index != null ? this.table[index[0]][index[1]].equals("") ? "0" : this.table[index[0]][index[1]] : column;
	}
}