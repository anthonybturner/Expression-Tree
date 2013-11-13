import java.util.Random;


public class ExpressionTreeTester implements Operatable{
	
	
	private ExpressionTree e_tree;

	public ExpressionTreeTester(){
		
		e_tree = new ExpressionTree();
		
	}
	
	public void testAll(){
		
//		//testIsOperator();
		//testIsBinaryOperator();
	}
		
	public void testIsBinaryOperator(){
		
		System.out.println("Testing isBinaryOperator()");
		
		String [] operators = {
				String.valueOf(ADDITION),
				String.valueOf(DIVISION),
				String.valueOf(END_MARKER), 
				EQUAL_TO, 
				GREATER_THAN_EQUAL_TO, 
				LESS_THAN_EQUAL_TO,
				LOGICAL_AND,
				EXPONENTIATION,
				LOGICAL_OR, 
				NOT_EQUAL_TO,
				LOGICAL_NOT
				};
		
		int index = 0;
		
		while(index < operators.length){
			
			String operator = operators[index];

			if( e_tree.isBinaryOperator( operator) ){
				System.out.println(operator + " IS a binary operator");
			}else
				System.out.println(operator + " is NOT a binary operator");
			
			index++;
		}

	}
	
	public void testIsOperator(){
		
		System.out.println("Testing isOperator()");
		
		String.valueOf(ExpressionTree.EXPONENTIATION);
		String [] operators = {String.valueOf(ExpressionTree.ADDITION),String.valueOf(ExpressionTree.DIVISION),
				String.valueOf(ExpressionTree.END_MARKER), ExpressionTree.EQUAL_TO, ExpressionTree.GREATER_THAN_EQUAL_TO, ExpressionTree.LESS_THAN_EQUAL_TO,
				ExpressionTree.LOGICAL_AND,ExpressionTree.LOGICAL_OR, ExpressionTree.NOT_EQUAL_TO};
		
		
		int index = 0;
		while(index < operators.length){
		
			String operator = operators[index];
			if( e_tree.isOperator( operator) ){
				
				System.out.println(operator + " is an operator");
			}else
				System.out.println(operator + " is not an operator");
			
			
			
			index++;
		
		}
	}

	public static void main(String[] args) {
		
		new ExpressionTreeTester().testAll();
		
	}

}
