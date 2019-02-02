package tw.com.dh.excel;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class FormulaVLOOKUP extends Formula {
	
	public static final String FORMULA_REGEX = "(VLOOKUP|vlookup)\\(";
	public static final String NAME = "VLOOKUP";
	
	private String lookUpValue;
	private String sheet;
	private String tableArray;
	private String colIndex;
	private String rangeLookup;
	
	public FormulaVLOOKUP(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		
		this.lookUpValue = statements[0];
		this.sheet = statements[1].substring(0, statements[1].indexOf("!"));
		this.tableArray = statements[1].substring(statements[1].indexOf("!") + 1);
		this.colIndex = statements[2];
		this.rangeLookup = statements[3];
		
		this.lookUpValue = this.calPostfix(this.convertToPostfix(this.lookUpValue));
		this.colIndex = this.calPostfix(this.convertToPostfix(this.colIndex));
		this.rangeLookup = this.calPostfix(this.convertToPostfix(this.rangeLookup));
		
		String s = this.tableArray.substring(0, this.tableArray.indexOf(":"));
		String e = this.tableArray.substring(this.tableArray.indexOf(":") + 1);
		int [] sindex = this.excelData.getColumnIndex(s);
		int [] eindex = this.excelData.getColumnIndex(e);
		String[][] table = this.excelData.getTable(this.sheet);
		
		for (int i = sindex[0] ; i <= eindex[0]; i++) {
			for (int j = sindex[1] ; j <= eindex[1]; j++) {
				if(table[i][j] == null || table[i][j].isEmpty()) break;
				
				if(i != sindex[0]) continue;
					
				if (this.rangeLookup.equals("1") || this.rangeLookup.equals("TRUE")) {
					BigDecimal start = new BigDecimal(table[i][j]);
					BigDecimal end = new BigDecimal(table[i == eindex[0] ? i : i + 1][j]);
					BigDecimal value = new BigDecimal(this.lookUpValue);
					Log.d(start + " ~ " + end + " -> " + value);
					if (start.compareTo(value) <= 0 && end.compareTo(value) >= 0) 
						return table[Integer.parseInt(colIndex)][j];
				}else {
					Log.d(lookUpValue + " -> " + table[i][j]);
					if (lookUpValue.equals(table[i][j])) 
						return table[i + Integer.parseInt(colIndex) - 1][j];
				}
			}
		}
		
		Log.d("Not Lookup!");
		return "";
	}
}
