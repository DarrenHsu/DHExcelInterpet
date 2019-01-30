package tw.com.skl.operator;

import tw.com.skl.utility.Log;

public class Colon extends Expression {
	
	public static final String SYMBOL = ":";
	
	private Expression left;
	private Expression right;
	
	public Colon(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return "ColonResult";
	}
}