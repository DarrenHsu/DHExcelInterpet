package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class MoreThanEqual extends Expression {

	public static final String SYMBOL 	= ">=";
	
	private Expression left;
	private Expression right;
	
	public MoreThanEqual(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return new BigDecimal(this.left.interpret().compareTo(this.right.interpret()) >= 0 ? 1 : 0);
	}
}