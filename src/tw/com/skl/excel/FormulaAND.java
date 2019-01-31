package tw.com.skl.excel;

import tw.com.skl.utility.Log;

public class FormulaAND extends Formula {
	
	public static final String FORMULA_REGEX = "(AND|and)\\(";
	public static final String NAME = "AND";
	
	private String[] logicals;
	
	public FormulaAND(ExcelData excelData) {
		this.excelData = excelData;
	}
	
	@Override
	public String interpret(String statement) {
		Log.d("p " + NAME + " : " + statement);
		this.logicals = this.splitComman(statement);
		
		String result = S_TRUE;
		String r = null;
		
		for(int i  = 0 ; i < this.logicals.length ; i++) {
			if (i == 0) {
				r = this.calPostfix(this.convertToPostfix(this.logicals[i]));
				continue;
			}
			String tmp = this.calPostfix(this.convertToPostfix(this.logicals[i]));
			if (!r.equals(tmp)) {
				result = S_FALSE;
				break;
			}else {
				r = tmp;
			}
		}

		Log.d("r " + result);
		return result;
	}
}
