package tw.com.skl.excel;

import java.util.HashMap;

public class ExcelData {
	public String[] statements;
	public String[][] table;
	public int currentRow;
	public HashMap<String, String> map;
	
	public ExcelData(String[][] table, String[] statements, HashMap<String, String> map, int currentRow) {
		this.statements = statements;
		this.table = table;
		this.map = map;
		this.currentRow = currentRow;
	}
}