public class RunAvl {

    public static void main (String[] args){

        AVLTree<Integer> test = new AVLTree<Integer>();

        test.insert(40);
        test.insert(11);
        test.insert(47);
        test.insert(17);
        test.insert(40);

        System.out.println("Inorder: ");
        test.inorder();


        }

}
