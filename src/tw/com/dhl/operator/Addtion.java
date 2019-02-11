package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.utility.Log;

public class Addtion extends Expression {
	
	public static final String SYMBOL = "+";
	
	private Expression left;
	private Expression right;
	
	public Addtion(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return this.left.interpret().add(this.right.interpret());
	}
}