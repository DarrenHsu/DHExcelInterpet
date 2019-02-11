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
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return BigDecimalMath.pow(this.left.interpret(), this.right.interpret(), new MathContext(10));
	}
}