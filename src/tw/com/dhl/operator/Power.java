package tw.com.dhl.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import ch.obermuhlner.math.big.BigDecimalMath;
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
		BigDecimal r = new BigDecimal(this.right.interpret());
		return BigDecimalMath.pow(l, r, new MathContext(10)).toString();
	}
}