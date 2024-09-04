package edu.ser222.m01_03;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * CompletedList represents an implementation of a list.
 *
 * @author (Jimmy Anderson), Acuna
 * @version (1)
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {

    //The following three variables are a suggested start if you are using a list implementation.
    //protected int count;
    //protected int modChange;
    //protected DoubleLinearNode<T> head, tail;
    // Instance variables

    protected int count;
    protected int modChange;
    protected DoubleLinearNode<T> head, tail;

    public CompletedList() {
        count = 0;
        modChange = 0;
        head = null;
        tail = null;
    }
//This method removes and returns the first element, we will utilize the isEmpty method to check and see if the list is empty
    //if it's empty we will throw a noSuchElementException since we can't remove a list that is null
    @Override
    //checks for empty list
    public T removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("empty list lil bro we got nothing to remove");
        }
        //if we have 1 and only 1 element, then we will decrement the count and change the head and tail to null to reflect a new empty node
        else if (count == 1) {
            DoubleLinearNode<T> tempNode = new DoubleLinearNode<>(head.getNode(), head.getNext(), head.getPrevious());

            head = null;
            tail = null;
            count--;
            modChange++;

            return tempNode.getNode();
        }
        //if we have elements that are greater than one, then we will create a temporary node that will hand us the first node
        else {
            DoubleLinearNode<T> tempNode = new DoubleLinearNode<>(head.getNode(), head.getNext(), head.getPrevious());

            head = tempNode.getNext();
            head.setPrevious(null);
            count--;
            modChange++;

            return tempNode.getNode();
        }
    }
    //This method removes and returns the last element, we will utilize the isEmpty method to check and see if the list is empty
    //if it's empty we will throw a noSuchElementException since we can't remove a list that is null
    @Override
    public T removeLast() throws NoSuchElementException {
        //checks for empty list
        if (isEmpty()) {
            throw new NoSuchElementException("empty list lil bro we got nothing to remove");
        }
        //if we have 1 and only 1 element, then we will decrement the count and change the head and tail to null to reflect a new empty node

        else if (count == 1) {
            DoubleLinearNode<T> tempNode = new DoubleLinearNode<>(tail.getNode(), tail.getNext(), tail.getPrevious());

            head = null;
            tail = null;
            count--;
            modChange++;

            return tempNode.getNode();
        }
        //if we have elements that are greater than one, then we will create a temporary node that will hand us the tail node

        else {
            DoubleLinearNode<T> tempNode = new DoubleLinearNode<>(tail.getNode(), tail.getNext(), tail.getPrevious());

            tail = tempNode.getPrevious();
            tail.setNext(null);
            count--;
            modChange++;

            return tempNode.getNode();
        }
    }
//This method will check to see if the list is empty, if it is empty we will return a NoSuchElementException
    //otherwise, we will remove the head, null, or a specific  target
    //if the element size is one then we change it to a null list
    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list lil bro! There is nothing to remove.");
        }
        else {
            DoubleLinearNode<T> curNode = new DoubleLinearNode<>(head.getNode(), head.getNext(), head.getPrevious());
            for (T elem : this) {
                if (elem.equals(element)) {
                    if (count == 1) {
                        head = null;
                        tail = null;
                        count--;
                        modChange++;
                        return curNode.getNode();
                    }
                    else if (curNode.equals(head)) {
                        removeFirst();
                        return null;
                    }
                    else if (curNode.equals(tail)) {
                        removeLast();
                        return null;
                    }
                    else {
                        curNode.getPrevious().setNext(curNode.getNext());
                        curNode.getNext().setPrevious(curNode.getPrevious());
                        count--;
                        modChange++;
                        return curNode.getNode();
                    }
                }
                curNode = curNode.getNext();
            }
            throw new NoSuchElementException();
        }
    }
//This method will check to see if the list is empty, if it is empty we will return a NoSuchElementException
//Otherwise we will simply return the head element of the list
    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list lil bro! There is nothing to get.");
        }
        return head.getNode();
    }
    //This method will check to see if the list is empty, if it is empty we will return a NoSuchElementException
//Otherwise we will simply return the tail element of the list
    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty list lil bro! There is nothing to get.");
        }
        return tail.getNode();
    }
//checks to see if the list contains a value and reutrns true if it does
    @Override
    public boolean contains(T target) {
        for (T elem : this) {
            if (elem.equals(target)) {
                return true;
            }
        }
        return false;
    }
//returns true or false if the list is empty
    @Override
    public boolean isEmpty() {
        return count == 0;
    }
//returns size
    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }
//tostring method
    @Override
    public String toString() {
        String output = "";

        if (isEmpty()) {
            return "empty";
        }
        else
        {
            for (T elem : this) {
                output += elem + " ";
            }

            return output;
        }
    }
//Implementation of a ListerIterator to help us traverse through each element
    private class ListIterator implements Iterator<T> {
        private final int modCounted = modChange;
        private DoubleLinearNode<T> iter = head;


//checks to see if the list has another element and returns true if it is
        public boolean hasNext() {
            if (modChange != modCounted) {
                throw new ConcurrentModificationException();
            }
            return iter != null;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            if (modChange != modCounted)
                throw new ConcurrentModificationException();

            T element = iter.getNode();
            iter = iter.getNext();

            return element;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}