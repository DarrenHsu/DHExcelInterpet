package tw.com.skl.excel;

import java.util.ArrayList;

public class FormulaAND extends Expression {
	
	public static final String FORMULA_REGEX = "(AND|and)\\(";
	public static final String NAME = "AND";
	
	private String[] operands;
	
	@Override
	public String interpret(String statement) {
		print("p " + NAME + " : " + statement);
		this.operands = this.splitComman(statement);
		
		String msg = "p ";
		for(int i  = 0 ; i < this.operands.length ; i++) {
			String o = this.operands[i];
			this.calPostfix(this.convertToPostfix(o));
			msg += o + " & ";
		}

		return NAME + "_Result";
	}
}
