
/*Robert Huntington
 * Data Structures Assignment 5
 * 
 * Program Name: Calc.java
 * Purpose:  This is a program which prompts the user for an infix expression and converts it into postfix form.
 * 			 The expression can be all integers or can contain a variable 'x' for which the user may enter different values for.
 * 			 Once an expression and or value for variables have been entered, the program will convert the given infix expression
 * 			 to postfix, and then will solve the expression for all of the values given.  
 * 
 * Solution: There are 7 methods (including main) within the driver class that are used to run this program.
 * 			 1 + 2: convertToPostfix(String s) and convertToPostfixList(ArrayList<String> ar):
 * 						These two methods contain most of the algorithms and logic which make the program successfully run.  
 * 						The difference between the two is that the one that takes a String as an argument is used to convert
 * 						infix expressions without spaces.  The other converts infix containing spaces between arguments.
 * 						These methods contain a series of if/else statements and switch statements in order to check each 
 * 						token in the input and appropriately append it to the postfix string or add operators and parentheses 
 * 						to the stack.  The stack is then popped appropriately to ensure that the operators are placed in the correct
 * 						places.
 * 
 * 			3: 		verifyString(String input) method is used to verify that a legal argument has been inputed.  This this takes into acount 
 * 					characters that are not digits, x's, operators, or parenthesis.  It also check formatting to ensure that the input 
 * 					is in infix form to begin with.  This is done with a for loop, if statement, and switch/case statement.
 * 
 * 			4: 		convertSpaced(String s) method converts the input string into arguments separated by spaces.
 * 			
 * 			5:  	evaluate(String input) method evaluates the transformed postfix expression into an integer answer.  This is done using
 * 					a stack from which values are pushed and popped, as well as a series of if else statements to check each operator.  
 * 			
 * 			6:  	replaceX(String s, Object o) method is used to insert values for the variable 'x' when it comes time to evaluate the expression.
 * 
 * 
 * 			7:  	Main method		
 * 
 * 
 * Data Structures used: Stacks and ArrayLists.
 * 
 * How to use/ Expected i/o: 
 * 				User will be given instructions to input an infix expression that is to be converted into postfix.  Expressions
 * 				with or without spaces are permitted, as are expressions with or without variable 'x'.
 * 				Once the infix expression is entered, the corresponding postfix for of the expression will be displayed, with spaces
 * 				between arguments.  If 'x' was used as a variable, The user will then be prompted to enter a value for 'x', which will
 * 				then be used in the evaluation of the expression.  Until the user enters 'q', the program will prompt for new values of 
 * 				'x' for it to be evaluated at.  If there are no variables in the expression, it will just be evaluated and the program ends. 
 * 
 * 
 * Classes:  One main Class called Calc, and an Operator Class.
 * 			 Calc Class:  Contains most of the logic and methods for running the program.
 * 			 Operator Class:  is used to evaluate precedence between each operator by creating an operator object.				
 * 
 * 
 */		

import java.util.*;

public class Calc {
	
	//Preconditions: user input of infix expression.  May contain spaces or not.
	//Postconditions: returns a postfix string version of the input as well as an answer for which it is evaluated.
	
	public static void main(String[] args) {
		System.out.println("Instructions:\nThis program will accept an infix expression (spaced or not) and convert it into postfix"
				+ ".\nIf you choose to use spaces, make sure you place a space between every argument, including parentheses.\n\n");
		String input;
		String postfix;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter an infix expression to be converted (use a variable 'x' if you wish): ");
			input = sc.nextLine();

		} while (verifyString(input) == false); // Checks validity of string.

		if (input.indexOf(" ") >= 0) {  // Checks if the expression is spaced or not.
			boolean quit = false;
			postfix = convertToPostfixList(convertSpaced(input));
			System.out.println(postfix);
			if(postfix.indexOf("x") >= 0){
			do {
				postfix = convertToPostfixList(convertSpaced(input));
				System.out.println("Enter value of x ('q' to quit): ");
				String in = sc.next();
				if (in.equals("q")) {
					quit = true;
				} else{
					postfix = replaceX(postfix, in);
					System.out.print("Answer: ");
					System.out.println(evaluate(postfix));
				
				}
			} while (quit == false);
			}else{
				System.out.print("Answer: ");
				System.out.println(evaluate(postfix));
			}
		} else {
			boolean quit = false;
			postfix = convertToPostfix(input);
			System.out.println(postfix);
			if(postfix.indexOf("x") >= 0){
			do {
				postfix = convertToPostfix(input);
				System.out.println("Enter value of x: ");
				String in = sc.next();
				if (in.equals("q")) {
					quit = true;
				} else{
					postfix = replaceX(postfix, in);
					System.out.print("Answer: ");
					System.out.println(evaluate(postfix));
				
				}
			} while (quit == false);
			}else{
				System.out.print("Answer: ");
				System.out.println(evaluate(postfix));
			}
			
			
		}

	}

	//Precondition: Validated infix expression
	//Postcondition: postfix form of the given argument.
	public static String convertToPostfix(String input) {

		String postString = "";
		Stack<Character> st = new Stack<>();
		for (int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i)) || input.charAt(i) == 'x') {
				postString = postString + input.charAt(i);
			} else if (input.charAt(i) == '(' || input.charAt(i) == ')') {
				switch (input.charAt(i)) {
				case '(':
					st.push('(');
					break;
				case ')':
					while (!st.peek().equals('(') && !st.isEmpty()) {
						postString = postString + st.pop();
					}
					if (st.peek().equals('(')) {
						st.pop();
					}
					break;
				}
			} else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*'
					|| input.charAt(i) == '/' || input.charAt(i) == '%') {
				Operator o = new Operator(input.charAt(i));
				if (!st.isEmpty()) {
					Operator stack = new Operator((char) st.peek());
					while (!st.isEmpty() && o.precedence() <= stack.precedence()) {
						stack = new Operator((char) st.peek());
						postString = postString + st.pop();
					}
					st.push(input.charAt(i));
				} else {
					Operator stack = new Operator(input.charAt(i));
					while (!st.isEmpty() && !st.peek().equals('(') && o.precedence() < stack.precedence()) {
						postString = postString + st.pop();
					}
					st.push(input.charAt(i));
				}
			}
		}
		while (!st.isEmpty()) {
			postString = postString + st.pop();
		}

		String spaced = "";
		for (int j = 0; j < postString.length(); j++) {
			spaced = spaced + postString.charAt(j) + " ";
		}
		return spaced;
	}

	//Precondition: Validated infix expression
	//Postcondition: postfix form of the given argument.
	public static String convertToPostfixList(ArrayList<String> input) {

		ArrayList<String> postString = new ArrayList<String>();
		
		Stack<String> st = new Stack<>();
		for (int i = 0; i < input.size(); i++) {
			if (Character.isDigit(input.get(i).charAt(0)) || input.get(i).equals("x")) {
				postString.add(input.get(i));
			} else if (input.get(i).equals("(") || input.get(i).equals(")")) {
				switch (input.get(i)) {
				case "(":
					st.push("(");
					break;
				case ")":
					while (!st.peek().equals("(") && !st.isEmpty()) {
						postString.add(st.pop());
					}
					if (st.peek().equals("(")) {
						st.pop();
					}
					break;
				}
			} else if (input.get(i).equals("+") || input.get(i).equals("-") || input.get(i).equals("*")
					|| input.get(i).equals("/") || input.get(i).equals("%")) {
				Operator o = new Operator(input.get(i).charAt(0));
				if (!st.isEmpty()) {
					Operator stack = new Operator(st.peek().charAt(0));
					while (!st.isEmpty() && o.precedence() <= stack.precedence()) {
						stack = new Operator(st.peek().charAt(0));
						postString.add(st.pop());
					}
					st.push(input.get(i));
				} else {
					Operator stack = new Operator(input.get(i).charAt(0));
					while (!st.isEmpty() && !st.peek().equals("(") && o.precedence() < stack.precedence()) {
						postString.add(st.pop());
					}
					st.push(input.get(i));
				}
			}
		}
		while (!st.isEmpty()) {
			postString.add(st.pop());
		}
		String str = "";
		for (int j = 0; j < postString.size(); j++) {
			str = str + postString.get(j) + " ";
		}
		return str;
	}

	//Precondition: infix string expression
	//Postcondition: boolean value verifying whether or not a valid string has been inputted
	public static boolean verifyString(String input) {
		boolean flag = true;

		if (!Character.isDigit(input.charAt(0)) && !Character.isDigit(input.charAt(input.length() - 1))
				&& input.charAt(0) != '(' && input.charAt(input.length() - 1) != ')') {
			System.out.println("invalid input format.");
			flag = false;
		} else {

			for (int i = 1; i < input.length(); i++) {
				switch (input.charAt(i)) {
				case '1':
					break;
				case '2':
					break;
				case '3':
					break;
				case '4':
					break;
				case '5':
					break;
				case '6':
					break;
				case '7':
					break;
				case '8':
					break;
				case '9':
					break;
				case '0':
					break;
				case 'x':
					break;
				case '+':
					break;
				case '-':
					break;
				case '*':
					break;
				case '/':
					break;
				case '%':
					break;
				case '(':
					break;
				case ')':
					break;
				case ' ':
					break;
				default:
					System.out.println("Invalid characters.");
					flag = false;
					i = input.length();
					break;
				}
			}
		}
		return flag;

	}

	//Precondition: infix string expression
	//Postcondition: infix string expression with spaces between operands, operators, and parentheses.
	public static ArrayList<String> convertSpaced(String input) {
		ArrayList<String> ar = new ArrayList<String>(Arrays.asList(input.split(" ")));

		return ar;
	}

	
	//Precondition: valid infix string expression
	//Postcondition: integer value at which the expression has been evaluated.
	public static int evaluate(String input) {
		Stack<Integer> s = new Stack<Integer>();
		Scanner sc = new Scanner(input);
		while (sc.hasNext()) {
			if (sc.hasNextInt()) {
				s.push(sc.nextInt());
			} else {
				int num2 = s.pop();
				int num1 = s.pop();
				String op = sc.next();

				if (op.equals("+")) {
					s.push(num1 + num2);
				} else if (op.equals("-")) {
					s.push(num1 - num2);
				} else if (op.equals("*")) {
					s.push(num1 * num2);

				} else if (op.equals("/")) {
					s.push(num1 / num2);
				} else {
					s.push(num1 % num2);
				}
			}

		}
		return s.pop();
	}

	//Precondtion:  infix expression containing 'x' variables.  Value at which x will be evaluated.
	//Postcondtion: infix string expression that has replaced all 'x' variables with digit values. 
	public static String replaceX(String input, Object x) {
		String replace = "";
		ArrayList<String> ar = new ArrayList<String>(Arrays.asList(input.split(" ")));
		for (int i = 0; i < ar.size(); i++) {
			if (ar.get(i).equals("x")) {
				ar.set(i, "" + x);
			}
		}
		for (int j = 0; j < ar.size(); j++) {
			replace = replace + ar.get(j) + " ";
		}

		return replace;
	}
}
