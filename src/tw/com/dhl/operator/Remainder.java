package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class Remainder extends Expression {
	public static final String SYMBOL = "%";
	
	private Expression left;
	private Expression right;
	
	public Remainder(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		BigDecimal l = new BigDecimal(this.left.interpret());
		BigDecimal r = new BigDecimal(this.right.interpret());
		return l.remainder(r).toString();
	}
}