package tw.com.dhl.operator;

import java.math.BigDecimal;

import tw.com.dh.excel.Formula;
import tw.com.dh.utility.Log;

public class LassThan extends Expression {
	
	public static final String SYMBOL = "<";
	
	private Expression left;
	private Expression right;
	
	public LassThan(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public BigDecimal interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		return this.left.interpret().compareTo(this.right.interpret()) == -1 ? Formula.S_TRUE_VALUE : Formula.S_FALSE_VALUE;
	}
}