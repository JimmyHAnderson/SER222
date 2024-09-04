package edu.ser222.m01_03;

public class DoubleLinearNode<T> {
    private DoubleLinearNode<T> next;
    private DoubleLinearNode<T> previous;
    private T node;

    public DoubleLinearNode(T elem, DoubleLinearNode<T> nextNode, DoubleLinearNode<T> previousNode) {
        next = nextNode;
        previous = previousNode;
        node = elem;
    }
//we will be implementing our getters and setters utilizing a next and previous system
//gets us the next element
    public DoubleLinearNode<T> getNext() {

        return next;
    }
//sets the next element
    public void setNext(DoubleLinearNode<T> nextNode) {
        next = nextNode;
    }
//gets the previous element
    public DoubleLinearNode<T> getPrevious() {
        return previous;
    }
//sets us the previous element
    public void setPrevious(DoubleLinearNode<T> previousNode) {
        previous = previousNode;
    }
//gets the current node
    public T getNode() {
        return node;
    }
//implementation of equals method to help save some efficiency for methods
    public boolean equals(DoubleLinearNode<T> parameter) {
        if (this == parameter) {
            return true;
        }
        return (parameter.getNode() == this.getNode()) && (parameter.getNext() == this.getNext()) && (parameter.getPrevious() == this.getPrevious());
    }
}