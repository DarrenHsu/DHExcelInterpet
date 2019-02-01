package tw.com.dh.excel;

import tw.com.dh.utility.Log;

public class FormulaVLOOKUP extends Formula {
	
	public static final String FORMULA_REGEX = "(VLOOKUP|vlookup)\\(";
	public static final String NAME = "VLOOKUP";
	
	private String lookUpValue;
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
		this.tableArray = statements[1];
		this.colIndex = statements[2];
		this.rangeLookup = statements[3];

		this.calPostfix(this.convertToPostfix(this.lookUpValue));
		this.calPostfix(this.convertToPostfix(this.tableArray));
		this.calPostfix(this.convertToPostfix(this.colIndex));
		this.calPostfix(this.convertToPostfix(this.rangeLookup));
		
		return NAME + "_Result";
	}
}
