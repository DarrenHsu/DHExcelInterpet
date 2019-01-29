package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaOR extends Expression {
	
	public static final String FORMULA_REGEX = "(OR|or)\\(";
	public static final String NAME = "OR";
	
	private String[] logicalS;
	
	public FormulaOR(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		
		this.logicalS = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.logicalS.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.logicalS[i]));
		}
		
		return NAME + "_Result";
	}
}
