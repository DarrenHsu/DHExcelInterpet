package tw.com.dhl.operator;

import java.math.BigDecimal;
import java.math.MathContext;

import tw.com.dh.excel.Formula;
import tw.com.dh.utility.Log;

public class Subtration extends Expression {
	
	public static final String SYMBOL = "-";

	private Expression left;
	private Expression right;
	
	public Subtration(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return this.left.interpret().subtract(this.right.interpret(), new MathContext(Formula.S_PRECISION));
	}
}