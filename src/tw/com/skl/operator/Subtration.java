package tw.com.skl.operator;

import tw.com.skl.utility.Log;

public class Subtration extends Expression {
	
	public static final String SYMBOL = "-";

	private Expression left;
	private Expression right;
	
	public Subtration(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cel: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return "SubtrationResult";
	}
}