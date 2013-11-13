/**
 * BTNode represents a Node for a Binary Tree
 * @author Anthony
 *
 */
public class BTNode<E> {
	
	//Member fields
	private BTNode<E> left_child;
	private BTNode<E> right_child;
	private E data;
	
	public BTNode(E data){
	
		this.data = data;
	
	}

	public BTNode<E> getLeftChild() {
		return left_child;
	}

	public BTNode<E> getRightChild() {
		return right_child;
	}

	public void setLeftChild(BTNode<E> left_child) {
		this.left_child = left_child;
	}
	
	public void setRightChild(BTNode<E> right_child) {
		this.right_child = right_child;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
	
	public boolean isLeaf(){
		
		if( left_child == null && right_child == null)
			return true;
		else
			return false;
	}
	
	public E getRightMostData(){
	
		if (right_child == null )
			return data;
		else
			return right_child.getRightMostData();
		
		
	}
	
	public E getLeftMostData(){
		
		if (left_child == null )
			return data;
		else
			return left_child.getLeftMostData();
	
	}
	
	public BTNode<E> removeLeftMost(){
		
		if(left_child == null)
			return right_child;
		else{
			left_child = left_child.removeLeftMost();
			return this;
		
		}
	}
	
	public BTNode<E> removeRightMost(){
		
		if(right_child == null)
			return left_child;
		else{
			right_child = right_child.removeRightMost();
			return this;
		}
	}
	
	public void inorderPrint(){
		
		if(left_child != null)
			left_child.inorderPrint();
		
		System.out.println(data);
		
		if( right_child != null)
			right_child.inorderPrint();
		
		
	}
	
	public void postorderPrint(){
		
		if(left_child != null)
			left_child.inorderPrint();

		if( right_child != null)
			right_child.inorderPrint();
		
		System.out.println(data);
		
		
	}
	
	public void preorderPrint(){
		
		System.out.println(data);

		if(left_child != null)
			left_child.inorderPrint();

		if( right_child != null)
			right_child.inorderPrint();
	
	}
	
	public void print(int depth){
		
		
		int i;
		
		//Print indentation and data from the current node
		for( i = 1; i <= depth; i++)
			System.out.print("    ");
		
		System.out.println(data);
		
		//Print left sub tree or a dash if there is no right child and no left child
		if( left_child != null)
			left_child.print(depth+1);
		else if(right_child != null){
			
			for( i = 1; i <= depth +1; i++)
				System.out.print("    ");
			System.out.println("--");

		}
		//Print right sub tree or a dash if there is a left child and no right child
		if( right_child != null)
			right_child.print(depth+1);
		else if(left_child != null){
			
			for( i = 1; i <= depth +1; i++)
				System.out.print("    ");
			System.out.println("--");

		}

	}
	
	
}
