package tw.com.dhl.operator;

import tw.com.dh.excel.Formula;
import tw.com.dh.utility.Log;

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