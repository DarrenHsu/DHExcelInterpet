package tw.com.skl.invest.operator;

public class Multiplication extends Expression {
	private Expression left;
	private Expression right;
	
	public Multiplication(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String interpret() {
		print("cel: " + this.left.interpret() + " * " + this.right.interpret());
		return "MultiplicationResult";
	}
}