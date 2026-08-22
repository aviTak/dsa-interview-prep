import java.util.*;

class BST {
    class Node {
        int value;
        Node left;
        Node right;
        
        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }
    
    private Node root;
    
    public BST() {
        root = null;
    }
    
    public void add(int value) {
        Node node = new Node(value);
        
        if (root == null) {
            root = node;
            return;
        }
        
        Node curr = root;
        
        while (true) {
            if (value < curr.value) {
                if (curr.left == null) {
                    curr.left = node;
                    return;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = node;
                    return;
                }
                curr = curr.right;
            }
        }
    }
    
    public void inorder() {
        inorder(root);
    }
    
    private void inorder(Node node) {
        if (node == null) {
            return;
        }
        
        inorder(node.left);
        System.out.println(node.value);
        inorder(node.right);
    }
    
    public boolean isValid() {
        return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isValid(Node node, int min, int max) {
        if (node == null) {
            return true;
        }
        
        if (node.value <= min || node.value >= max) {
            return false;
        }
        
        return isValid(node.left, min, node.value) &&
            isValid(node.right, node.value, max);
    }
    
    public Node lowestAncestor(Node root, Node a, Node b) {
        Node curr = root;
        
        while (curr != null) {
            if (a.value < curr.value && b.value < curr.value) {
                curr = curr.left;
            } else if (a.value > curr.value && b.value > curr.value) {
                curr = curr.right;
            } else {
                return curr;
            }
        }
        
        return null;
    }
}










