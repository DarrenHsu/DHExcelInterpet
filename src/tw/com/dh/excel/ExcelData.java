package tw.com.dh.excel;

import java.math.BigDecimal;
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
	
	public HashMap<String, BigDecimal[][]> sheets;
	
	public String[] statements;
	public BigDecimal[][] table;
	
	public int rowCount, colCount;
	public int currentRow, currentCol;
	public int firstRow, firstCol;
	
	public ExcelData() {}
	
	public ExcelData(String name, int cols, int rows, String[] statements, int firstCol, int firstRow) {
		HashMap<String, BigDecimal[][]> sheet = new HashMap<>();
		this.table = new BigDecimal[cols][rows];
		
		for(int i = 0 ; i < this.table.length ; i++)
			for(int j = 0 ; j < this.table[i].length ; j ++)
				this.table[i][j] = BigDecimal.ZERO;
		
		sheet.put(name, this.table);
		this.statements = statements;
		this.sheets = sheet;
		this.table = this.sheets.get(name);
		
		this.colCount = table.length;
		this.rowCount = table[0].length;
		
		this.currentCol = firstCol;
		this.currentRow = firstRow;
		
		this.firstCol = firstCol;
		this.firstRow = firstRow;
	}
	
	public ExcelData(HashMap<String, BigDecimal[][]> sheet, String name, String[] statements, int currentCol, int currentRow) {
		this.statements = statements;
		this.sheets = sheet;
		this.table = this.sheets.get(name);
		
		this.colCount = table.length;
		this.rowCount = table[0].length;
		
		this.currentCol = currentCol;
		this.currentRow = currentRow;
		
		this.firstCol = currentCol;
		this.firstRow = currentRow;
	}
	
	public void setSheet(String name, BigDecimal[][] table) {
		this.sheets.put(name, table);
	}
	
	public void setValue(String sheetName, int col, int row, BigDecimal value) {
		BigDecimal[][] table = this.sheets.get(sheetName);
		if (table != null) {
			table[col][row] = value;
		}
	}
	
	public BigDecimal[][] getTable(String sheetName) {
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
		Pattern p = Pattern.compile("-{0,1}\\d{1,3}");
		Matcher m = p.matcher(column);
		if (m.find()) 
			return Integer.parseInt(m.group());
		
		return -1;
	}
	
	public int[] getColumnIndex(String column) {
		Pattern p = Pattern.compile("^(\\$)([A-Z]{1,2})(-{0,1}\\d{1,3})$");
		Matcher m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_ROW
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1 - this.firstRow + this.currentRow;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("^([A-Z]{1,2})(\\$)(-{0,1}\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// RELATIVE_CELL
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("^\\$([A-Z]{1,2})\\$(-{0,1}\\d{1,3})$");
		m = p.matcher(column);
		if (m.find()) {
			// ABSOLUTE_COLUMN
			int col = getCol(m.group());
			int row = getRow(m.group()) - 1;
			
			Log.d(m.group() + " col " + col + ",row " + row);
			
			return new int[]{col, row};
		}
		
		p = Pattern.compile("([A-Z]{1,2})(-{0,1}\\d{1,3})$");
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
	
	public BigDecimal getColumnType(String column) {
		int[] index = this.getColumnIndex(column);
		return index != null ? this.table[index[0]][index[1]] : new BigDecimal(column.trim().equals("\"\"") ? "0" : column);
	}
}