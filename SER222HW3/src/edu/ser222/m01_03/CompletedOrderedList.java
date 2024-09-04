package edu.ser222.m01_03;


/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author (Jimmy Anderson), Acuna
 * @version (1)
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
        implements OrderedListADT<T>
{
    @Override
    public void add(T element) throws NullPointerException {
        //Checking to see if the element is null, if it is null then we will return a NullPointerException
        if (element == null) {
            throw new NullPointerException("null element lil bro");
        }

        DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element, null, null);
        //this checks to see if the nodes are empty, if it is empty then we will initialize a new head, tail, and increment the count, and modChange
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            count++;
            modChange++;
        }
        // if it is not empty we will call our getters and setters
        else {
            DoubleLinearNode<T> curNode = new DoubleLinearNode<>(head.getNode(), head.getNext(), head.getPrevious());
            for (T elem : this) {
                if (element.compareTo(curNode.getNode()) <= 0) {
                    if (curNode.equals(head)) {
                        newNode.setNext(head);
                        head.setPrevious(newNode);
                        head = newNode;
                        count++;
                        modChange++;
                        return;
                    }
                    else {
                        newNode.setNext(curNode);
                        newNode.setPrevious(curNode.getPrevious());
                        curNode.getPrevious().setNext(newNode);
                        curNode.setPrevious(newNode);
                        count++;
                        modChange++;
                        return;
                    }
                }
                    else if ((element.compareTo(curNode.getNode()) > 0) && (curNode.getNext() == null)) {
                    newNode.setPrevious(tail);
                    tail.setNext(newNode);
                    tail = newNode;
                    count++;
                    modChange++;
                    return;
                }
                curNode = curNode.getNext();
            }
        }
    }
}
