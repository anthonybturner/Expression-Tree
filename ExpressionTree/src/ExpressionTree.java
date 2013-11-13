import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
/**
 * 
 * @author Anthony Turner<br>
 * <b>Date:</b> October 23, 2013<br>
 * <b>Course:</b> Computer Science III <br> 
 *  	Advanced Data Structures<br>
 * <b>Assignment:</b> #5 ExpressionTree
 * <b>Description:</b> prints the post fix expression before calculation, a tree graph of a created expression tree,
 * the fully parenthesized infix expression, and the calculated value(s) for a given input file of expression(s).
 * The program returns a calculated value or zero(meaning false)
 * 
 */


public class ExpressionTree implements Operatable{

	private static final String FILE_INPUT = "in.dat";

	private Stack<BTNode<String>> expression_tree_stack;//Stack which holds the express tree (nodes)

	/**
	 * Constructs a new Binary ExpressionTree
	 */
	public ExpressionTree(){

		expression_tree_stack = new Stack<BTNode<String>>();
	}

	private void evaluate(String file) {

		printWelcome();
		
		File input_file = new File(file);
		Scanner scan = null;
		try {

			scan = new Scanner(input_file);
			while( scan.hasNextLine() ){

				getContinuePrompt();

				String expression  = scan.nextLine();//Get each expression
				
				printExpression(expression);//Print expression before work is done.
				createExpressionTree(expression);
				
				BTNode<String> root = expression_tree_stack.pop();
			
				printTree(root);//Prints a graph of the expression tree
				printInfix(root);//Prints the infix expression
			
				int value = calculateInfixExpression(root);
				
				printCalculatedResults(value, expression);
				

			}
			
			printGoodbye();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}finally{

			scan.close();
		}
	}



	private void getContinuePrompt() {
		
		print("\n\nPress <Enter> to continue...\n");
		
		@SuppressWarnings("resource")//Scanner warning about system resource leak for not closing the scanner
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}

	private void createExpressionTree(String expression) {

		StringTokenizer tokenizer = new StringTokenizer(expression);

		while( tokenizer.hasMoreTokens() ){//Get each operand or operator from the expression

			String token = tokenizer.nextToken();

			if(!token.equals(END_MARKER)){//Ignore end marker and any input after it because it's a comment

				if( isOperator(token) ){//Create root node for Operator

					BTNode<String> root_node_operator = new BTNode<String>(token);//Create root node with the operator as data

					addOperatorsChildren(root_node_operator, token);
					addToExpressionTree(root_node_operator);

				}else{//Operand

					expression_tree_stack.push( new BTNode<String>(token));

				}
			}
		}
	}

	
	private void addToExpressionTree(BTNode<String>root_node_operator) {

		expression_tree_stack.push(root_node_operator);

	}

	

	//Adds children to the given root operator node
	private void addOperatorsChildren(BTNode<String> root_node_operator, String token) {

		if( !expression_tree_stack.isEmpty() ){

			BTNode<String> node_top = expression_tree_stack.pop();
			root_node_operator.setLeftChild(node_top);

			if( isBinaryOperator(token) && !expression_tree_stack.isEmpty()){//We need to pop another operand

				BTNode<String> node_next_to_top = expression_tree_stack.pop();
				root_node_operator.setRightChild(node_next_to_top);	
			}
		}
	}

	
	/**
	 * Calculates the value of an infix expression for an expression tree
	 * <b>Precondition:</b> the given parameter root is not null
	 * <b>Postcondition:</b> The values of the tree have been calculated to a single integer value
	 * @param root the root node to begin calculation at
	 * @return returns one(meaning true) or zero(meaning false) for logical boolean operators or
	 * returns the calculated integer value for an expression
	 */
	public int calculateInfixExpression(BTNode<String>root) {
		
		if( root == null){	return 0;	}
		
		if( isOperand(root.getData())){
			//This is safe since if it is an operand, then it is an integer by our definition.
			return Integer.parseInt(root.getData());
			
		}else{
			
			String operator = root.getData();
			
			//Decide which calculation is to happen based on the operator
			if( operator.equals(LOGICAL_NOT) ){
				
				boolean val = !(calculateInfixExpression(root.getLeftChild()) == calculateInfixExpression(root.getRightChild()));
				if( val ){
					return 1;
				}else{
					return 0;
				}
				
			}else if( operator.equals(EXPONENTIATION) ){
				
				return (int) Math.pow(calculateInfixExpression(root.getLeftChild()) , calculateInfixExpression(root.getRightChild()));
				
			}else if (	operator.equals(MULTIPLICATON) ){
				
				return calculateInfixExpression(root.getLeftChild()) *	calculateInfixExpression(root.getRightChild());
				
			}else if (	operator.equals(DIVISION) ){
				
				return calculateInfixExpression(root.getLeftChild()) /	calculateInfixExpression(root.getRightChild());
				
			}else if ( 	operator.equals(MODULUS) ){
				
				return calculateInfixExpression(root.getLeftChild()) %	calculateInfixExpression(root.getRightChild());
				
			}else if (	operator.equals(ADDITION) ){
				
				return calculateInfixExpression(root.getLeftChild()) +	calculateInfixExpression(root.getRightChild());
				
			}else if ( operator.equals(SUBTRACTION) ){
				
				return calculateInfixExpression(root.getLeftChild()) -	calculateInfixExpression(root.getRightChild());
				
			}else if ( operator.equals(LESS_THAN) ){
				
				boolean val = (calculateInfixExpression(root.getLeftChild()) < calculateInfixExpression(root.getRightChild()));
				if( val ){
					return 1;
				}else{
					return 0;
				}
				
			}else if ( operator.equals(LESS_THAN_EQUAL_TO) ){
				
				boolean val = (calculateInfixExpression(root.getLeftChild()) <= calculateInfixExpression(root.getRightChild()));
				if( val ){
					return 1;
				}else{
					return 0;
				}
				
			}else if (	operator.equals(GREATER_THAN) ){//Using ternary for the next few cases just for a change
				
				return (calculateInfixExpression(root.getLeftChild()) > calculateInfixExpression(root.getRightChild())) ? 1 : 0;

				
			}else if (	operator.equals(GREATER_THAN_EQUAL_TO)){
				
				return (calculateInfixExpression(root.getLeftChild()) >= calculateInfixExpression(root.getRightChild())) ? 1 : 0;

				
			}else if (	operator.equals(EQUAL_TO)){
				
				return (calculateInfixExpression(root.getLeftChild()) == calculateInfixExpression(root.getRightChild())) ? 1 : 0;

				
			}else if (	operator.equals(NOT_EQUAL_TO)){
				
				return (calculateInfixExpression(root.getLeftChild()) != calculateInfixExpression(root.getRightChild())) ? 1 : 0;

				
			}else if (	operator.equals(LOGICAL_AND)){
				
				int val_1 = calculateInfixExpression(root.getLeftChild());
				int val_2 = calculateInfixExpression(root.getRightChild());
				
				if( val_1 == 1 && val_2 ==1 ){//Logical AND. Both values must be true for the expression or subexpression to be true
					
					return 1;//returning one represents true
				}else 
					return 0;
				
			}else {//Default operation if none of the other cases is that the operation must be a logical OR
				
				int val_1 = calculateInfixExpression(root.getLeftChild());
				int val_2 = calculateInfixExpression(root.getRightChild());	
				
				if( val_1 == 1 || val_2 ==1 ){//Logical OR. either values can be true for the expression or subexpression to be true
					
					return 1;
				}else 
					return 0;
				
			}
		}
	}

	
	/*
	 * ********************************************************************************************
	 * 	
	 * 											Predicate/boolean return methods
	 * 
	 * ********************************************************************************************
	 */
	
	/**
	 * Determines if token is a binary operator
	 * @param token the value to determine if it is an operator
	 * @return true if the token is a binary operator or false if it is not
	 */
	public boolean isBinaryOperator(String token) {

		return !token.equals(END_MARKER) && !token.equals(LOGICAL_NOT) ;

	}

	/**
	 * Determines if token is an operand
	 * @param token the value to determine if it is an operand
	 * @return true if the token is a operand or false if it is not
	 */
	public boolean isOperand(String token){
		//Simple call where if the token is not an operator, 
		//Then it must be an operand
		//The call is for readability from other calls.
		return !isOperator(token);
	}
	
	/**
	 * Determines if token is an operator
	 */
	public boolean isOperator(String token){

		if( isArithmeticOperator(token) || isLogicalOperator(token))
			return true;
		else
			return false;
	}
	
	/**
	 * Determines if the given token is an arithmetic operator
	 */
	public boolean isArithmeticOperator(String token){

		if(	token.equals(EXPONENTIATION) ||
			token.equals(MULTIPLICATON) ||
			token.equals(DIVISION) ||
			token.equals(MODULUS) ||
			token.equals(ADDITION) ||
			token.equals(SUBTRACTION)){
			return true;
		}else
			return false;
	}
	
	/**
	 * Determines if the given token is a logical operator
	 */
	public boolean isLogicalOperator(String token){

		if( token.equals(LOGICAL_NOT)||
			token.equals(LESS_THAN) ||
			token.equals(LESS_THAN_EQUAL_TO) ||
			token.equals(GREATER_THAN) ||
			token.equals(GREATER_THAN_EQUAL_TO)|| 
			token.equals(EQUAL_TO) 	  || 
			token.equals(NOT_EQUAL_TO)|| 
			token.equals(LOGICAL_AND) ||
			token.equals(LOGICAL_OR)){
			return true;
		}else
			return false;
	}
	
	/**
	 * Determines if an expression contains any logical operators
	 */
	public boolean containsLogicalOperator(String expression){

		Scanner scan = new Scanner(expression);
		while( scan.hasNext() ){

			String token  = scan.next();//Get each expression
		
			if( isLogicalOperator(token) ){

				scan.close();
				return true;
			}
		}
		
		scan.close();
		return false;//No logical operators were found
	}
	

	/*
	 * ********************************************************************************************
	 * 	
	 * 											Print methods
	 * 
	 * ********************************************************************************************
	 */
	
	
	private void printWelcome() {
		
		print("================================================\n\n");
		print("\tWelcome to the Expression Tree program\n\n");
		print("================================================\n\n");
		
		
	}

	private void printGoodbye() {
		print("\n\nNo more expressions, Goodbye!");

		
	}
	
	/*
	 * Prints the final results for a calculated expression
	 */
	private void printCalculatedResults(int value, String expression) {
		
		boolean hasLogicalOperators = containsLogicalOperator(expression);
		if( hasLogicalOperators){
		
			if( value == 1 ){
				
				print("The expression evaluates to true -> " );

			}else
				print("The expression evaluates to false -> ");
			
			print("" + value);
			
		}else{
			
			print("calulated value is " + value);

		}
		
	}

	private void printInfixExpression(BTNode<String>root) {
				
		if( root == null){	return;	}
		
		//determine if the current node is an operator or operand
		if( isOperand(root.getData())){
			//If the current node is an operand, then print it.
			print(root.getData());
			
		}else{//Otherwise, We encountered an operator, so for each operator print a left parenthesis.
			//When encountering an operand, print it, and then print the right children 
			//and when done with the right children, print a right parenthesis
			
			print("(");
			printInfixExpression(root.getLeftChild());//Recursively call the current node's children and print any parens or operands for the childrens
			print(root.getData());
			printInfixExpression(root.getRightChild());
			print(")");

		}
	}
	
	private void printInfix(BTNode<String> root) {

		print("========================\n");
		print("Printed Infix Expression:\n");
		print("-------------------------\n");
		printInfixExpression(root);
		print("\n========================\n");
		
	}

	private void printExpression(String expression) {
		
		print("=====================================\n");
		print("Printed Expression before evaluation:\n");
		print("-------------------------------------\n");
		print(expression);//Print the expression, which is in post-fix form
		print("\n=====================================\n");
		print("\n\n");

	}


	private void printTree(BTNode<String>root) {
		
		if( root == null)
			return;
		print("========================\n");
		print("Printed Tree Expression:\n");
		print("------------------------\n");

		root.print(0);
		
		print("========================\n");
	}

	private void print(String expression) {

		System.out.print(expression);

	}


	public static void main(String[] args) {

		ExpressionTree et = new ExpressionTree();
		et.evaluate(FILE_INPUT);

	}

}
