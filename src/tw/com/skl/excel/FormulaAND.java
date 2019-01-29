package tw.com.skl.excel;

import java.util.ArrayList;

public class FormulaAND extends Expression {
	
	public static final String FORMULA_REGEX = "(AND|and)\\(";
	public static final String NAME = "AND";
	
	private String[] logicals;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		this.logicals = this.splitComman(statement);
		
		for(int i  = 0 ; i < this.logicals.length ; i++) {
			this.calPostfix(this.convertToPostfix(this.logicals[i]));
		}

		return NAME + "_Result";
	}
}
