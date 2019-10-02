import java.lang.*;
import java.util.*;

public class AVLTree<E extends Comparable<? super E>> {

    /**
     * Class AVLTree is a basic implementaion of Adelson-Velskii and
     * Landis' Balanced Binary Search Tree.
     * Kyle Strougo
     * Algorithms Project 
     */


    //public static class AVL_Tree<E extends Comparable<? super E>> {
    protected Node root;
    protected int size;

    /**
     * Construct an empty AVLTree.
     */
    public void AVLTree() {
        // not necessary, but explicit stating root starts at null
        this.root = null;
        this.size = 0;
    }

    /**
     * Insert the element into this AVLTree.
     * @param element the element to insert into the tree. Duplicates are
     * allowed
     */
    public void insert(E element) {
        this.root = insert(this.root, element);
        this.size++;
    }

    /**
     * Remove the element from this AVLTree.
     * @param element the element to remove
     */
    public void remove(E element) {
        this.root = remove(this.root, element);
        this.size--;
    }

    /**
     * Check if this tree contains the element.
     * @return true if this tree contains the element, false otherwise
     */
    public boolean contains(E element) {
        return contains(this.root, element);
    }

    /**
     * Return the minimum elemnent in this tree.
     * @return the mininum element in this tree
     */
    public E findMin() {
        return findMin(this.root);
    }

    /*
     * A protected helper method for insertion.
     * By taking a Node as a parameter, we can write this method
     * recursively, continuing to call insert on subtrees until the element
     * can be inserted.
     * @param node the root of some subtree of this AVLTree
     * @param element the element to insert into this subtree
     */
    protected Node insert(Node node, E element) {

        if(node == null) {
            return new Node(element);
        }
        if(element.compareTo(node.element) < 0) {
            // insert element into the left subtree
            node.left = insert(node.left, element);
        } else {
            // insert element into the right subtree
            node.right = insert(node.right, element);
        }
        if(Math.abs(height(node.left) - height(node.right)) > 1) {
            node = balance(node);
        }

        node.height = this.height(node);
        return node;
    }

    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    /*
     * A protected helper method for removal.
     * @param node the root of some subtree of this AVLTree
     * @param element the element to remove from this subtree
     */
    protected Node remove(Node node, E element) {

        if (node == null)
            return node;

        int compare = (element).compareTo((node.element));

        if (compare < 0){
            node.left = remove(node.left, element); }


        else if (compare > 0) {
            node.right = remove(node.right, element);  }

        else
        {

            if ((node.left == null) || (node.right == null))
            {
                Node temp = null;
                if (temp == node.left)
                    temp = node.right;
                else
                    temp = node.left;

                if (temp == null)
                {
                    temp = node;
                    node = null;
                }
                else
                    node = temp;

            }
            else {

                E min_temp = findMin(node.right);

                node.element = min_temp;

                node.right = remove(node.right, min_temp);
            }
        }

        // If the tree had only one node then return
        if (node == null)
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        balance(node);

        return node;
    }

    /*
     * As for insert and remove, a protected helper is used for a recursive
     * implementation.
     * @param element the element to search for
     * @param node the root of the subtree to search in
     * @return true if this subtree contains the element, false otherwise
     */
    protected boolean contains(Node node, E element) {
        if(node == null) {
            return false;
        }
        if(element.compareTo(node.element) == 0) {
            return true;
        }
        if(element.compareTo(node.element) < 0) {
            return contains(node.left, element);
        } else {
            return contains(node.right, element);
        }
    }

    /*
     * Return the minimum element in the subtree rooted at node
     * @param node the root of the subtree
     * @return the minimum element in this subtree
     *
     */
    protected E findMin(Node node) {

        Node min = node;

        while (min.left != null)
             min = min.left;

        return min.element;
    }

    /*
     * Balance the subtree rooted at this node.
     * @param node the root of the subtree to balance
     * @return the new root of the balanced subtree
     *
     */
    protected Node balance(Node node) {
        // Determine which type of rotation should be used and call on it.
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0)
            return singleRotateWithRightChild(node);

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0)
        {
            node.left = singleRotateWithLeftChild(node.left);
            return singleRotateWithRightChild(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0)
            return singleRotateWithLeftChild(node);

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0)
        {
            node.right = singleRotateWithRightChild(node.right);
            return singleRotateWithLeftChild(node);
        }

        return node;

    }

    /*
     * Perform a single rotation for left outside case.
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    protected Node singleRotateWithLeftChild(Node node) {
    
        Node rootRightChild = node.right;
        node.right = rootRightChild.left;
        rootRightChild.left = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        rootRightChild.height = Math.max(height(rootRightChild.left), height(rootRightChild.right)) + 1;

        return rootRightChild;
    }

    /*
     * Perform a single rotation for right outside case.
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    protected Node singleRotateWithRightChild(Node node) {

        Node rootLeftChild = node.left;
        node.left = rootLeftChild.right;
        rootLeftChild.right = node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        rootLeftChild.height = Math.max(height(rootLeftChild.left), height(rootLeftChild.right)) + 1;

        return rootLeftChild;

    }

    /*
     * Perform a double rotation for left inside case.
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    protected Node doubleRotateWithLeftChild(Node node) {
        //STUBBED
        node.left = singleRotateWithRightChild(node.left);
        node = singleRotateWithLeftChild(node);

        return node;
    }

    /*
     * Perform a double rotation for right inside case.
     * @param node the root of the subtree to rotate
     * @return the new root of this subtree
     */
    protected Node doubleRotateWithRightChild(Node node) {

        node.right = singleRotateWithLeftChild(node.right);
        node = singleRotateWithRightChild(node);

        return node;
    }

    /*
     * protected helper method to calculate the height of a node. A node's
     * height is the larger of its left and right subtree's heights plus
     * one. To make this calculation consistent and easy, we define
     * height of an empty subtree is -1.
     * @param node the node to calculate the height of
     * @return its height as determined by the heights of its subtrees
     */
    protected int height(Node node) {
        // if the left child is null, its height is -1, otherwise, retrieve
        // its height
        if(node == null) { return -1; }
        int leftHeight = (node.left == null ? -1 : node.left.height);
        // same
        int rightHeight = (node.right == null ? -1 : node.right.height);
        return Math.max(leftHeight, rightHeight) + 1;
    }
    public void inorder(){
        inorder(root);
    }
    private void inorder(Node r){
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.element + " ");
            inorder(r.right);
        }
    }

    protected class Node {
        // since this is a protected inner class, and the outer AVLTree class
        // will need to freely modify the connections and update the height
        // of its nodes, the following three variables are not protected.
        Node left;
        Node right;
        int height;
        E element;

        
        public Node(E element) {
            this.left = null;
            this.right = null;
            this.height = 0;
            this.element = element;
        }
    }

}
