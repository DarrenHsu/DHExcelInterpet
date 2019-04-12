package tw.com.dhl.operator;

import java.math.BigDecimal;

public class Number extends Expression {
	
	private BigDecimal operand;
	
	public Number(BigDecimal operand) {
		this.operand = operand;
	}
	
	@Override
	public BigDecimal interpret() {
		return this.operand;
	}
}