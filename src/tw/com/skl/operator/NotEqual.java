package tw.com.skl.operator;

import tw.com.skl.excel.Formula;
import tw.com.skl.utility.Log;

public class NotEqual extends Expression {
	
	public static final String SYMBOL = "<>";
	
	private Expression left;
	private Expression right;
	
	public NotEqual(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		String result = this.left.interpret().equals(this.right.interpret()) ? Formula.S_FALSE : Formula.S_TRUE;
		return result;
	}
}