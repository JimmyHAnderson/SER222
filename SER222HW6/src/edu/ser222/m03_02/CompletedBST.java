package edu.ser222.m03_02;

/**
 * A binary search tree based implementation of a symbol table.
 *
 * Completion time: (5 Hours)
 *
 * @author (Jimmy Anderson), Sedgewick, Acuna
 * @version (version 1)
 */
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    private Node<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value> iter = root;

        while(iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    private Value get(Node<Key, Value> x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Key min() {
        if(root == null)
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Key, Value> min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        if(root == null)
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Key, Value> max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        if(root == null)
            throw new NoSuchElementException();

        Node<Key, Value> x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node<Key, Value> t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node<Key, Value> x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    @Override
    public void deleteMin() {
        if(root == null)
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        if (root == null)
            return new LinkedList<>();
        else
            return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public Key ceiling(Key key) {
        //SKIP, UNNEEDED
        return null;
    }


    //returns root
    public Node getRoot() {
        return root;
    }

    //returns true or false as long as the key value isn't null
    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    //returns true or false as the size is 0 or more than 1 respectively
    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        } else {
            return false;
        }
    }
    // We use a recursive call to call upon deleteMax
    @Override
    public void deleteMax()  {

        root = deleteMax(root);
    }
    // main implementation of the deleteMax method
    //we will check to see if the value from the key is null, if it is null then we will throw
    //a noSuchElementException
    //otherwise then if the right is null(the end of the bst) then we return the left,
    private Node<Key, Value> deleteMax(Node<Key, Value> n)
    {
        // Check if empty
        if(n == null) {
            throw new NoSuchElementException("Can't have a null type");
        }
        //case where we are at the end
        if (n.right == null) {
            return n.left;
        }
        //everything else we will delete the value to the right
        n.right = deleteMax(n.right);
        //adjust the size
        n.N = size(n.left) + size(n.right) + 1;

        return n;
    }
    //this method will return the size by comparing the low and high key
    //if the low is greater than high then we return a 0
    //if we run contains(hi) and it returns true then we return the difference of the size including high
    //otherwise we simply reuturn high - low if hi is not contained in the collection
    @Override
    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }
    //this method will take a key and value and insert it into the tree
    //if they key already exists, then we will simply update the value
    //if it is null(empty) then we will add the key and value

    @Override
    public void putFast(Key key, Value val) {
        //case where we do not have any roots we create a new root(start of tree)
        if (root == null) {
            root = new Node<Key, Value>(key, val, 1);
        }
        //if we are not at the base case then we will check to see the other methods
        else {
            Node<Key, Value> node = root;
            Node<Key, Value> parent = null;
            //as long as the node isn't null we will set parent to the node value can make comparisons
            while (node != null) {
                parent = node;
                //if the key is less than zero then we put it on the left
                //if the key is more than zero then we put it on the right
                //otherwise we simply set the value as is
                if (key.compareTo(node.key) < 0) {
                    node = node.left;
                } else if (key.compareTo(node.key) > 0) {
                    node = node.right;
                } else {
                    node.val = val;
                    return;
                }
            }
            //sets node to root, and as long as the empty isnt null we will set the values based on the left or right checks
            node = root;
            while (node != null) {
                node.N = node.N + 1;
                if (key.compareTo(node.key) < 0) {
                    node = node.left;
                } else if (key.compareTo(node.key) > 0) {
                    node = node.right;
                }
            }

            Node<Key, Value> newNode = new Node<Key, Value>(key, val, 1);

            if (key.compareTo(parent.key) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    //this method will return a value paried with a key in the tree
    //if the key doesn't exist we will reutrn null
    @Override
    public Value getFast(Key key) {
        Node<Key, Value> n = root;
        while (n != null) {
            int value = key.compareTo(n.key); //running comparisons to return key
            //if the value is 0(root)
            if (value == 0) {
                return n.val;
                //returns left child
            } else if (value < 0) {
                n = n.left;
            } else {//returns right child
                n = n.right;
            }
        }
        return null;
    }
    //this method basically returns a string representation of the tree
    //where the ordering is a level traversal
    //each n ode's value is sperated by a space
    //in the case where we have no valid substree rooted at the key we simply return empty
    @Override
    public String displayLevel(Key key) {
        Node<Key, Value> node = root;
        if (root == null) {
            return "empty";
        }

        while (node != null) {
            int value = key.compareTo(node.key);
            if (value < 0) node = node.left;
            else if (value > 0) node = node.right;
            else break;
        }

        if (node == null) {
            return "empty";
        }

        Queue<Node<Key, Value>> queue = new LinkedList<>();
        queue.add(node);
        StringBuilder result = new StringBuilder();

        while (!queue.isEmpty()) {
            node = queue.poll();
            result.append(" ").append(node.val);

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return result.toString().trim(); // Removes the extra space at the start
    }
    //sorts the nodes into a balancedTree
    //it is balanced if the height of the left and right subtrees have a differfent of at most 1
    @Override
    public void balance() {
        LinkedList<Node<Key, Value>> nodes = new LinkedList<Node<Key, Value>>();

        sortNodes(nodes, root);

        root = balanceTree(nodes, 0, size() - 1);
        updateSize(root);
    }
    //helper method to update the size of the BST once we invoke it
    private void updateSize(Node<Key, Value> node) {
        if (node == null) {
            return;
        }
        node.N = size(node.left) + size(node.right) + 1;
        updateSize(node.left);
        updateSize(node.right);
    }

    //sorts the nodes in order as long as n isn't null
    private void sortNodes(LinkedList<Node<Key, Value>> nodes, Node<Key, Value> n)
    {
        if (n == null) {
            return;
        }
        sortNodes(nodes, n.left);
        nodes.add(n);
        sortNodes(nodes, n.right);
    }


    private Node<Key, Value> balanceTree(LinkedList<Node<Key, Value>> nodes, int start, int end)
    {
        if (start > end) {
            return null;
        }

        int middle = (start + end) / 2;

        Node<Key, Value> middleNode = nodes.get(middle);

        middleNode.left = balanceTree(nodes, start, middle - 1);

        middleNode.right = balanceTree(nodes, middle + 1, end);

        middleNode.N = size(middleNode.left) + size(middleNode.right) + 1;

        return middleNode;
    }
    /**
     * entry point for testing.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BST<Integer, String> bst = new CompletedBST();

        bst.put(10, "TEN");
        bst.put(3, "THREE");
        bst.put(1, "ONE");
        bst.put(5, "FIVE");
        bst.put(2, "TWO");
        bst.put(7, "SEVEN");

        System.out.println("Before balance:");
        System.out.println(bst.displayLevel(10)); //root

        System.out.println("After balance:");
        bst.balance();
        System.out.println(bst.displayLevel(3)); //root
    }
}