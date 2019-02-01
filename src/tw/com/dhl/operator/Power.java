package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class Power extends Expression {
	
	public static final String SYMBOL = "^";
	
	private Expression left;
	private Expression right;
	
	public Power(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		BigDecimal l = new BigDecimal(this.left.interpret());
		int r = Integer.parseInt(this.right.interpret());
		return l.pow(r).toString();
	}
}