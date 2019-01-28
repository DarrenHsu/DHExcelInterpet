package tw.com.skl.invest;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Expression {

	public static final String S_POWER 				= "^";
	public static final String S_MULTIPLICATION 	= "*";
	public static final String S_DIVISION			= "/";
	public static final String S_DIVISION_INTEGER 	= "\\";
	public static final String S_REMAINDER 			= "%";
	public static final String S_ADDTION 			= "+";
	public static final String S_SUBTRATION 		= "-";
	public static final String S_MORE_THAN 			= ">";
	public static final String S_LASS_THAN 			= "<";
	public static final String S_MORE_THAN_EUQAL 	= ">=";
	public static final String S_LASS_THAN_EQUAL 	= "<=";
	public static final String S_NOT_EUQAL 			= "<>";
	public static final String S_EUQAL 				= "=";
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

	protected int getExpressionOperatorPriority(String c) {
		switch (c) {
		case S_POWER:
			return 1;
		case S_MULTIPLICATION: case S_DIVISION:
			return 2;
		case S_DIVISION_INTEGER:
			return 3;
		case S_REMAINDER:
			return 4;
		case S_ADDTION: case S_SUBTRATION:
			return 5;
		case S_MORE_THAN: case S_LASS_THAN: case S_MORE_THAN_EUQAL: case S_LASS_THAN_EQUAL:
			return 6;
		case S_EUQAL: case S_NOT_EUQAL:
			return 7;
		default:
			return 99;
		}
	}
	
	protected String getExpressionOperatorRegex() {
		return "(\\" + S_POWER + "|" +
				"\\" + S_MULTIPLICATION + "|" +
				"\\" + S_DIVISION + "|" +
				"\\" + S_DIVISION_INTEGER + "|" +
				"\\" + S_REMAINDER + "|" +
				"\\" + S_ADDTION + "|" +
				"\\" + S_SUBTRATION + "|" +
				"\\" + S_EUQAL + "|" +
				"\\" + S_LEFT_PARENTHESIS + "|" +
				"\\" + S_RIGHT_PARENTHESIS + "|" +
				"(" + S_NOT_EUQAL + "|" +
				S_MORE_THAN_EUQAL + "|" + S_LASS_THAN_EQUAL +  "|" +
				S_MORE_THAN + "|" + S_LASS_THAN + ")" + ")";
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
				print("operand:" + operand);
				opStack.add(operand);
			}
			
			String operator = m.group();
			print("operator:" + operator);
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
			print("operand:" + operand);
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
		if (stack.size() < 3) {
			return;
		}
		
		int numAIndex = 0, numBIndex = 0, opIndex = 0;
		boolean isCalculator = false;
		
		for (int i = 0 ; i < stack.size() ; i++) {
			String s = stack.get(i);
			if (this.isOperator(s)) {
				opIndex = i;
				if (opIndex < 2) 
					return;
	            
	            numAIndex = i - 2;
	            numBIndex = i - 1;
	            
	            String numA = stack.get(i - 2);
	            String numB = stack.get(i - 1);
	            
	            switch (s) {
	    		case S_POWER:
	    			print("cal: " + numA + S_POWER + numB);
	    			break;
	    		case S_MULTIPLICATION:
	    			print("cal: " + numA + S_MULTIPLICATION + numB);
	    			break;
	    		case S_DIVISION:
	    			print("cal: " + numA + S_POWER + numB);
	    			break;
	    		case S_DIVISION_INTEGER:
	    			print("cal: " + numA + S_DIVISION_INTEGER + numB);
	    			break;
	    		case S_REMAINDER:
	    			print("cal: " + numA + S_REMAINDER + numB);
	    			break;
	    		case S_ADDTION:
	    			print("cal: " + numA + S_ADDTION + numB);
	    			break;
	    		case S_SUBTRATION:
	    			print("cal: " + numA + S_SUBTRATION + numB);
	    			break;
	    		case S_MORE_THAN:
	    			print("cal: " + numA + S_MORE_THAN + numB);
	    			break;
	    		case S_LASS_THAN:
	    			print("cal: " + numA + S_LASS_THAN + numB);
	    			break;
	    		case S_MORE_THAN_EUQAL:
	    			print("cal: " + numA + S_MORE_THAN_EUQAL + numB);
	    			break;
	    		case S_LASS_THAN_EQUAL:
	    			print("cal: " + numA + S_LASS_THAN_EQUAL + numB);
	    			break;
	    		case S_EUQAL:
	    			print("cal: " + numA + S_EUQAL + numB);
	    			break;
	    		case S_NOT_EUQAL:
	    			print("cal: " + numA + S_NOT_EUQAL + numB);
	    			break;
	    		default:
	    			break;
	    		}
	            
	            isCalculator = true;
	            break;
			}
		}
		
		if (isCalculator) {
			stack.remove(numAIndex);
			stack.remove(numBIndex - 1);
			stack.remove(opIndex - 2);

			stack.add(numAIndex, "Result");
	    }
		
		this.calPostfix(stack);
	}
}
