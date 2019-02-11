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
	public BigDecimal interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		String[] statements = this.splitComman(statement);
		
		this.lookUpValue = statements[0];
		this.sheet = statements[1].substring(0, statements[1].indexOf("!"));
		this.tableArray = statements[1].substring(statements[1].indexOf("!") + 1);
		this.colIndex = statements[2];
		this.rangeLookup = statements[3];
		
		BigDecimal luValue = this.calPostfix(this.convertToPostfix(this.lookUpValue));
		int ciValue = this.calPostfix(this.convertToPostfix(this.colIndex)).intValue();
		
		String s = this.tableArray.substring(0, this.tableArray.indexOf(":"));
		String e = this.tableArray.substring(this.tableArray.indexOf(":") + 1);
		int [] sindex = this.excelData.getColumnIndex(s);
		int [] eindex = this.excelData.getColumnIndex(e);
		BigDecimal[][] table = this.excelData.getTable(this.sheet);
		
		for (int i = sindex[0] ; i <= eindex[0]; i++) {
			for (int j = sindex[1] ; j <= eindex[1]; j++) {
				if(table[i][j] == null) break;
				
				if(i != sindex[0]) continue;
					
				if (this.rangeLookup.equals("1") || this.rangeLookup.equals("TRUE")) {
					BigDecimal start = table[i][j];
					BigDecimal end = table[i == eindex[0] ? i : i + 1][j];
					BigDecimal value = luValue;
					Log.d(start + " ~ " + end + " -> " + value);
					if (start.compareTo(value) <= 0 && end.compareTo(value) >= 0) 
						return table[ciValue][j];
				}else {
					Log.d(luValue + " -> " + table[i][j]);
					if (luValue.compareTo(table[i][j]) == 0) 
						return table[i + ciValue - 1][j];
				}
			}
		}
		
		Log.d("Not Lookup!");
		return BigDecimal.ZERO;
	}
}
