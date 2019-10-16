import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class AVLunit {

	@Test
	//insert calls to balance which the various insert tests then call to 
	//all rotation types since it works this 
	//verifies legitimacy of their operations
	
	void insertTest() {
		
		
		AVLTree.AVL_Tree Test = new AVLTree.AVL_Tree<Integer>();
		
		Test.insert(31);
		Test.insert(6);
		Test.insert(41);
		
		Test.insert(33);
		Test.insert(36);
		//end of basic insert test
		Test.inorder(); 		
		
		//left single force test
		Test.insert(46); 
		Test.inorder();
		//dual single results in double on left child
		
		//right single force  (via double)
		Test.insert(3);
		
		//left rotate via double
		Test.insert(2);
		Test.inorder();
		
		
		//dual single results in double on right child
		
		//right single force test (via double)
		Test.insert(49); 
		
		//left single force    (via double)
		Test.insert(50);
		Test.insert(79);
		Test.inorder();
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Test.inorder();
		
		String expectedOutput = "2 3 6 31 33 36 41 46 49 50 79 ";
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	
	@Test
	void removeTest() {
		
		AVLTree.AVL_Tree Test = new AVLTree.AVL_Tree<Integer>();
	
		
		Test.insert(54);
		Test.insert(79);
		Test.insert(24);
		Test.insert(99);
		Test.remove(79);
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Test.inorder();
		String expectedOutput = "24 54 99 ";
		assertEquals(expectedOutput, outContent.toString());
		
	}
	@Test
	void minTest() {
		
		AVLTree.AVL_Tree Test = new AVLTree.AVL_Tree<Integer>();
		
		
		Test.insert(54);
		Test.insert(79);
		Test.insert(24);
		Test.insert(99);
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		
		System.out.print(Test.findMin());
		String expectedOutput = "24";
		assertEquals(expectedOutput, outContent.toString());
		
	}
	
	
	

}
