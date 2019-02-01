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
		
		for (int i = sindex[0] ; i <= eindex[0]; i++) {
			for (int j = sindex[1] ; j <= eindex[1]; j++) {
				if(i == 0) {
					if (this.rangeLookup.equals(S_TRUE)) {
						BigDecimal start = new BigDecimal(this.excelData.table[i][j]);
						BigDecimal end = new BigDecimal(this.excelData.table[i == eindex[0] ? i : i + 1][j]);
						BigDecimal value = new BigDecimal(this.lookUpValue);
						if (start.compareTo(value) <= 0 && end.compareTo(value) >= 0) {
							return this.excelData.table[Integer.parseInt(colIndex)][j];
						}
					}else {
						if (lookUpValue.equals(this.excelData.table[i][j])) {
							return this.excelData.table[Integer.parseInt(colIndex)][j];
						}
					}
				}
			}
		}
		
		return "";
	}
}
