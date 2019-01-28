package tw.com.skl.excel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.com.skl.invest.operator.Addtion;
import tw.com.skl.invest.operator.Colon;
import tw.com.skl.invest.operator.Division;
import tw.com.skl.invest.operator.DivisionInteger;
import tw.com.skl.invest.operator.Equal;
import tw.com.skl.invest.operator.LassThan;
import tw.com.skl.invest.operator.LassThanEqual;
import tw.com.skl.invest.operator.MoreThan;
import tw.com.skl.invest.operator.MoreThanEqual;
import tw.com.skl.invest.operator.Multiplication;
import tw.com.skl.invest.operator.NotEqual;
import tw.com.skl.invest.operator.Number;
import tw.com.skl.invest.operator.Power;
import tw.com.skl.invest.operator.Remainder;
import tw.com.skl.invest.operator.Subtration;

public abstract class Expression {

	public static final String S_LEFT_PARENTHESIS	= "(";
	public static final String S_RIGHT_PARENTHESIS	= ")";
	
	public static String FORMULA_REGEX = "";
	public static String NAME = "";

	public abstract String interpret(String statement);

	protected static void print(String msg) {
		System.out.println(msg);
	}
	
	protected String[] splitComman(String str) {
		String[] splitStr = str.split(",");
		return splitStr;
	}
	
	protected String getExpressionOperatorRegex() {
		return "(\\" + Power.SYMBOL + "|" +
				"\\" + Multiplication.SYMBOL + "|" +
				"\\" + Division.SYMBOL + "|" +
				"\\" + DivisionInteger.SYMBOL + "|" +
				"\\" + Remainder.SYMBOL + "|" +
				"\\" + Addtion.SYMBOL + "|" +
				"\\" + Subtration.SYMBOL + "|" +
				"\\" + Colon.SYMBOL + "|" +
				"\\" + Equal.SYMBOL + "|" +
				"\\" + S_LEFT_PARENTHESIS + "|" +
				"\\" + S_RIGHT_PARENTHESIS + "|" +
				"(" + NotEqual.SYMBOL + "|" +
				MoreThan.SYMBOL + "|" + LassThan.SYMBOL +  "|" +
				MoreThanEqual.SYMBOL + "|" + LassThanEqual.SYMBOL + ")" + ")";
	}

	protected int getExpressionOperatorPriority(String c) {
		switch (c) {
		case Power.SYMBOL:
			return 1;
		case Multiplication.SYMBOL: case Division.SYMBOL:
			return 2;
		case DivisionInteger.SYMBOL:
			return 3;
		case Remainder.SYMBOL:
			return 4;
		case Addtion.SYMBOL: case Subtration.SYMBOL:
			return 5;
		case Colon.SYMBOL:
			return 6;
		case MoreThan.SYMBOL: case LassThan.SYMBOL: case MoreThanEqual.SYMBOL: case LassThanEqual.SYMBOL:
			return 7;
		case Equal.SYMBOL: case NotEqual.SYMBOL:
			return 8;
		default:
			return 99;
		}
	}
	
	private String cal(String operator, String numA, String numB) {
		switch (operator) {
		case Power.SYMBOL:
			return new Power(new Number(numA), new Number(numB)).interpret();
		case Multiplication.SYMBOL:
			return new Multiplication(new Number(numA), new Number(numB)).interpret();
		case Division.SYMBOL:
			return new Division(new Number(numA), new Number(numB)).interpret();
		case DivisionInteger.SYMBOL:
			return new DivisionInteger(new Number(numA), new Number(numB)).interpret();
		case Remainder.SYMBOL:
			return new Remainder(new Number(numA), new Number(numB)).interpret();
		case Addtion.SYMBOL:
			return new Addtion(new Number(numA), new Number(numB)).interpret();
		case Subtration.SYMBOL:
			return new Subtration(new Number(numA), new Number(numB)).interpret();
		case Colon.SYMBOL:
			return new Colon(new Number(numA), new Number(numB)).interpret();
		case MoreThan.SYMBOL:
			return new MoreThan(new Number(numA), new Number(numB)).interpret();
		case LassThan.SYMBOL:
			return new LassThan(new Number(numA), new Number(numB)).interpret();
		case MoreThanEqual.SYMBOL:
			return new MoreThanEqual(new Number(numA), new Number(numB)).interpret();
		case LassThanEqual.SYMBOL:
			return new LassThanEqual(new Number(numA), new Number(numB)).interpret();
		case Equal.SYMBOL:
			return new Equal(new Number(numA), new Number(numB)).interpret();
		case NotEqual.SYMBOL:
			return new NotEqual(new Number(numA), new Number(numB)).interpret();
		default:
			return null;
		}
	}
	
	protected String[] splitOperater(String statement) {
		return this.splitOperandOrOperater(statement, 1);
	}
	
	protected String[] splitOperand(String statement) {
		return this.splitOperandOrOperater(statement, 2);
	}
	
	protected String[] splitAll(String statemnt) {
		return this.splitOperandOrOperater(statemnt, 3);
	}
	
	protected String[] splitOperandOrOperater(String statement, int mode) {
		Pattern p = Pattern.compile(this.getExpressionOperatorRegex());
		Matcher m = p.matcher(statement);
		ArrayList<String> os = new ArrayList<>();
		int start = 0, end = 0;
		while (m.find()) {
			switch (mode) {
			case 1: {
				String operator = m.group();
				print("operator:" + operator);
				os.add(operator);
			} break;
			case 2: {
				String operand = statement.substring(start, m.start());
				if (!operand.isEmpty()) { 
					print("operand:" + operand);
				}
				os.add(operand);
			} break;
			default: {
				String operand = statement.substring(start, m.start());
				if (!operand.isEmpty()) { 
					print("operand:" + operand);
				}
				os.add(operand);
				
				String operator = m.group();
				print("operator:" + operator);
				os.add(operator);
			} break;
			}
			
			start = m.end();
			end = m.end();
		}
		
		if (mode >= 2 && end < statement.length()) {
			String operand = statement.substring(end);
			print("operand:" + operand);
			os.add(operand);
		}
		
		return os.toArray(new String[os.size()]);
	}
	
	protected boolean isOperator(String c) {
		return this.getExpressionOperatorPriority(c) != 99;
	}
	
	protected String[] splitStatement(String statement) {
		Pattern p = Pattern.compile(this.getExpressionOperatorRegex());
		Matcher m = p.matcher(statement);
		ArrayList<String> opStack = new ArrayList<>(), stack = new ArrayList<>();
		final String toStack = "(", toOutput = ")";
		int start = 0, end = 0;
		while (m.find()) {
			String operand = statement.substring(start, m.start());
			if (!operand.isEmpty()) { 
//				print("operand:" + operand);
				opStack.add(operand);
			}
			
			String operator = m.group();
//			print("operator:" + operator);
			switch(operator) {
			case toStack: {
				stack.add(operator);
			} break;
			case toOutput: {
				while(stack.size() != 0 && !stack.get(stack.size() - 1).equals(toStack)) {
					String lastObj = stack.get(stack.size() - 1);
					opStack.add(lastObj);
					stack.remove(stack.size() - 1);
				}
				stack.remove(stack.size() - 1);
			} break;
			default: {
				while(stack.size() != 0 && this.getExpressionOperatorPriority(stack.get(stack.size() - 1)) <= this.getExpressionOperatorPriority(operator)) {
					String lastObj = stack.get(stack.size() - 1);
					opStack.add(lastObj);
					stack.remove(stack.size() - 1);
				}
				stack.add(operator);
			} break;
			}
			
			start = m.end();
			end = m.end();
		}
		
		if (end < statement.length()) {
			String operand = statement.substring(end);
//			print("operand:" + operand);
			opStack.add(operand);
		}
		
		while(stack.size() != 0) {
			String lastObj = stack.get(stack.size() - 1);
			opStack.add(lastObj);
			stack.remove(stack.size() - 1);
		}
		
		print("postfix: " + opStack.toString());
		
		ArrayList<String> ss = new ArrayList<>(opStack);
		this.calPostfix(ss);
		
		return opStack.toArray(new String[opStack.size()]);
	}
	
	protected void calPostfix(ArrayList<String> stack) {
		if (stack.size() < 3) 
			return;
		
		int numAIndex = 0, numBIndex = 0, opIndex = 0;
		boolean isCalculator = false;
		String result = null;
		for (int i = 0 ; i < stack.size() ; i++) {
			String operator = stack.get(i);
			if (this.isOperator(operator)) {
				if (i < 2)
					return;
				
				opIndex = i;
	            numAIndex = i - 2;
	            numBIndex = i - 1;
	            
	            String numA = stack.get(i - 2);
	            String numB = stack.get(i - 1);
	            
	            result = cal(operator, numA, numB);
	            
	            isCalculator = true;
	            break;
			}
		}
		
		if (isCalculator) {
			stack.remove(numAIndex);
			stack.remove(numBIndex - 1);
			stack.remove(opIndex - 2);

			if (result != null) 
				stack.add(numAIndex, result);
	    }
		
		this.calPostfix(stack);
	}
}
