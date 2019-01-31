package tw.com.skl.operator;

import java.math.BigDecimal;

import tw.com.skl.excel.Formula;
import tw.com.skl.utility.Log;

public class LassThan extends Expression {
	
	public static final String SYMBOL = "<";
	
	private Expression left;
	private Expression right;
	
	public LassThan(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		Log.d("cal: " + this.left.interpret() + " " + SYMBOL + " " + this.right.interpret());
		BigDecimal l = new BigDecimal(this.left.interpret());
		BigDecimal r = new BigDecimal(this.right.interpret());
		return l.compareTo(r) == -1 ? Formula.S_TRUE : Formula.S_FALSE;
	}
}