package tw.com.dh.excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.com.dh.utility.Log;
import tw.com.dhl.operator.Addtion;
import tw.com.dhl.operator.Colon;
import tw.com.dhl.operator.Division;
import tw.com.dhl.operator.DivisionInteger;
import tw.com.dhl.operator.Equal;
import tw.com.dhl.operator.LassThan;
import tw.com.dhl.operator.LassThanEqual;
import tw.com.dhl.operator.MoreThan;
import tw.com.dhl.operator.MoreThanEqual;
import tw.com.dhl.operator.Multiplication;
import tw.com.dhl.operator.NotEqual;
import tw.com.dhl.operator.Number;
import tw.com.dhl.operator.Power;
import tw.com.dhl.operator.Remainder;
import tw.com.dhl.operator.Subtration;

public abstract class Formula {
	
	public static final String S_TRUE = "TRUE";
	public static final String S_FALSE = "FALSE";
	
	public static final BigDecimal S_TRUE_VALUE = BigDecimal.ONE;
	public static final BigDecimal S_FALSE_VALUE = BigDecimal.ZERO;
	
	public static final String S_LEFT_PARENTHESIS	= "(";
	public static final String S_RIGHT_PARENTHESIS	= ")";
	
	public static String FORMULA_REGEX = "";
	public static String NAME = "";
	
	private String expressionOperatorRegex = "(\\" + Power.SYMBOL + "|" +
			"\\" + Multiplication.SYMBOL + "|" +
			"\\" + DivisionInteger.SYMBOL + "|" +
			"\\" + Division.SYMBOL + "|" +
			"\\" + Remainder.SYMBOL + "|" +
			"\\" + Addtion.SYMBOL + "|" +
			"[^\\" + Power.SYMBOL + "\\" + Multiplication.SYMBOL + "\\" + DivisionInteger.SYMBOL + "\\" + Division.SYMBOL + "\\" + Remainder.SYMBOL + "\\" + Addtion.SYMBOL   + "]\\" + Subtration.SYMBOL + "|" +
			"\\" + Colon.SYMBOL + "|" +
			"\\" + S_LEFT_PARENTHESIS + "|" +
			"\\" + S_RIGHT_PARENTHESIS + "|" +
			NotEqual.SYMBOL + "|" +
			MoreThanEqual.SYMBOL + "|" + LassThanEqual.SYMBOL + "|" +
			MoreThan.SYMBOL + "|" + LassThan.SYMBOL +  "|" +
			Equal.SYMBOL + ")";
	
	protected ExcelData excelData;
	
	public abstract BigDecimal interpret(String statement);
	
	protected String[] splitComman(String str) {
		return this.splitComman(str, ",");
	}
	
	protected String[] splitComman(String str, String comman) {
		String[] splitStr = str.split(comman);
		return splitStr;
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
	
	private BigDecimal cal(String operator, String numA, String numB) {
		BigDecimal A = this.excelData.getColumnType(numA);
		BigDecimal B = this.excelData.getColumnType(numB);
		
		switch (operator) {
		case Power.SYMBOL:
			return new Power(new Number(A), new Number(B)).interpret();
		case Multiplication.SYMBOL:
			return new Multiplication(new Number(A), new Number(B)).interpret();
		case Division.SYMBOL:
			return new Division(new Number(A), new Number(B)).interpret();
		case DivisionInteger.SYMBOL:
			return new DivisionInteger(new Number(A), new Number(B)).interpret();
		case Remainder.SYMBOL:
			return new Remainder(new Number(A), new Number(B)).interpret();
		case Addtion.SYMBOL:
			return new Addtion(new Number(A), new Number(B)).interpret();
		case Subtration.SYMBOL:
			return new Subtration(new Number(A), new Number(B)).interpret();
		case Colon.SYMBOL:
			return new Colon(new Number(A), new Number(B)).interpret();
		case MoreThan.SYMBOL:
			return new MoreThan(new Number(A), new Number(B)).interpret();
		case LassThan.SYMBOL:
			return new LassThan(new Number(A), new Number(B)).interpret();
		case MoreThanEqual.SYMBOL:
			return new MoreThanEqual(new Number(A), new Number(B)).interpret();
		case LassThanEqual.SYMBOL:
			return new LassThanEqual(new Number(A), new Number(B)).interpret();
		case Equal.SYMBOL:
			return new Equal(new Number(A), new Number(B)).interpret();
		case NotEqual.SYMBOL:
			return new NotEqual(new Number(A), new Number(B)).interpret();
		default:
			return null;
		}
	}
	
	protected boolean isOperator(String c) {
		return this.getExpressionOperatorPriority(c) != 99;
	}
	
	protected ArrayList<String> convertToPostfix(String statement) {
		Pattern p = Pattern.compile(this.expressionOperatorRegex);
		Matcher m = p.matcher(statement);
		ArrayList<String> opStack = new ArrayList<>(), stack = new ArrayList<>();
		final String toStack = "(", toOutput = ")";
		int start = 0, end = 0;
		while (m.find()) {
			Log.d("m " + m.group());
			String operand = statement.substring(start, m.start()).trim();
			if (!operand.isEmpty()) { 
				opStack.add(operand);
			}
			
			String operator = m.group().trim();
			switch(operator) {
			case toStack: {
				stack.add(operator);
			} break;
			case toOutput: {
				while(stack.size() != 0 && !stack.get(stack.size() - 1).equals(toStack)) {
					opStack.add(stack.get(stack.size() - 1));
					stack.remove(stack.size() - 1);
				}
				stack.remove(stack.size() - 1);
			} break;
			default: {
				while(stack.size() != 0 && this.getExpressionOperatorPriority(stack.get(stack.size() - 1)) <= this.getExpressionOperatorPriority(operator)) {
					opStack.add(stack.get(stack.size() - 1));
					stack.remove(stack.size() - 1);
				}
				stack.add(operator);
			} break;
			}
			
			start = m.end();
			end = m.end();
		}
		
		if (end < statement.length()) 
			opStack.add(statement.substring(end).trim());
		
		
		while(stack.size() != 0) {
			opStack.add(stack.get(stack.size() - 1));
			stack.remove(stack.size() - 1);
		}
		
		Log.d("postfix: " + opStack.toString());
		
		return opStack;
	}
	
	protected BigDecimal calPostfix(ArrayList<String> stack) {
		if (stack.size() < 3)
			return this.excelData.getColumnType(stack.get(0));
		
		int numAIndex = 0, numBIndex = 0, opIndex = 0;
		boolean isCalculator = false;
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 0 ; i < stack.size() ; i++) {
			String operator = stack.get(i);
			if (this.isOperator(operator)) {
				if (i < 2) return null;
				
				opIndex = i;
	            numAIndex = i - 2;
	            numBIndex = i - 1;
	            
	            result = this.cal(operator, stack.get(i - 2), stack.get(i - 1));
	            
	            isCalculator = true;
	            break;
			}
		}
		
		if (isCalculator) {
			stack.remove(numAIndex);
			stack.remove(numBIndex - 1);
			stack.remove(opIndex - 2);

			if (result != null) 
				stack.add(numAIndex, result.stripTrailingZeros().toPlainString());
	    }
		
		return this.calPostfix(stack);
	}
}
