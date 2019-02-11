package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class MoreThan extends Expression {
	
	public static final String SYMBOL = ">";
	
	private Expression left;
	private Expression right;
	
	public MoreThan(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return new BigDecimal(this.left.interpret().compareTo(this.right.interpret()) == 1 ? 0 : -1);
	}
}