package edu.ser222.m01_03;
import java.util.NoSuchElementException;
/**
 * This program provides an implementation of the Deque interface. Also provides a main that
 * demonstrates it.
 *
 * @author (Jimmy Anderson), Acuna
 * @version (version 1) Lost count not gonna lie lol
 */


public class CompletedDeque<Item> implements Deque<Item> {
    //we need to create a Node class since we cannot import as well as initialize our variables to traverse through the deque
// :/ lol
    //This will refer to the link of the left most node
    private Node head;
    //This will refer to the link of the right most node
    private Node tail;
    //This will refer to the amount of elements of the CompletedDeque
    private int N;

    //Constructor for the CompletedDeque
//We will implement front and back as null since we cannot set to 0 in this case
    public CompletedDeque(){
        head=null;
        tail=null;
        N=0;

    }

    //creation of the Node class
    //we will use next to help traverse as well as previous as these will serve as our iterators
    class Node {

        //Node Attributes
        Item item;
        Node next;
        Node previous;

        //Constructor for setting up our current value, our next value, and previous value
        public Node(Item item) {
            this.item = item;
            next = null;
            previous = null;
        }
        //Getters and Setters
        public Item getItem() {
            return item;
        }
        //This will retrieve the next item
        public Node getNext() {
            return next;
        }
        //this will get the previous item
        public Node getPrev() {
            return previous;
        }
        //this sets a value as previous
        public void setPrevious(Node previous) {
            this.previous = previous;
        }
        //this sets the value as next
        public void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * Program entry point for deque.
     * @param args command line arguments
     */
    //Basically our push method, we will simply add an element to head of the deque
    //We will need to create a temp Node to store the new element and shift elements around
    //we also need to check if the element is null so we can set both the head and the tail as the new value
    @Override
    public void enqueueFront(Item element){
        Node tempNode = new Node(element);
        //if we have an empty deque, we will set the new element as both the head and tail
        if(head == null){
            head = tempNode;
            tail = tempNode;
        }

        else {
            //we set the new value's node so it connects next to the current head
            tempNode.setNext(head);
            //we transfer the current node into previous node
            head.setPrevious(tempNode);
            //we set the new head as the new value
            head = tempNode;
        }
        //increment
        N++;
    }
    //This method will do the same but will push it to the tail instead
    //We will need to create a temp Node to store the new element and shift elements around
    @Override
    public void enqueueBack(Item element){
        Node tempNode = new Node(element);
        //case where the deque is empty we set both the front and the back as the temp since the head and tail should be the same value
        if  (tail == null){
            head = tempNode;
            tail = tempNode;
        }


        else {
            //we set the new value's node so it connects next to the current tail
            tail.setNext(tempNode);
            //we transfer the current node into previous node
            tempNode.setPrevious(tail);
            //we set the new tail as the new value
            tail = tempNode;
        }
        //increment
        N++;
    }


    //Basically our pop method, we will check to see if the deque is empty
    //if it is null, we will return a NoSuchElementException
    //if it passes the check, then we will retrieve the value and remove it form the dequeue
    @Override
    public Item dequeueFront() throws NoSuchElementException{
        if(head==null){
            throw new NoSuchElementException("Lil bro, we can't get anything from a null list");
        }

        //we create a variable for the poppedHead
        Item poppedHead = head.getItem();
        //the new head will point towards the value next to the previous head
        head = head.getNext();

        //in the case that the deque has only one element we set the head and tail to null to indicate that it is empty
        if(head == null){
            tail = null;
        }

        //if there are still any more elements, then we will null out the previous head since we have a new head
        else{
            head.setPrevious(null);
        }
        //decrement
        N--;
        return poppedHead;
    }

    //Basically our pop method, we will check to see if the deque is empty
    //if it is null, we will return a NoSuchElementException
    //if it passes the check, then we will retrieve the value and remove it form the dequeue
    @Override
    public Item dequeueBack() throws NoSuchElementException{
        if(tail == null){
            throw new NoSuchElementException("Lil bro, we can't get anything from a null list");
        }
        //now we need to retrieve the item and store it using the getItem() method
        Item poppedTail = tail.getItem();
        tail = tail.getPrev();

        //in the case that the deque has only one element we set the head and tail to null to indicate that it is empty
        if(tail==null){
            head = null;
        }
        //if there are still any more elements, then we will null out the previous tail since we have a new tail
        else{
            tail.setNext(null);
        }
        //Decrement
        N--;
        return poppedTail;
    }
    //Checks to see if the list is empty, if it is we throw a NoSuchElementException, if it isn't we return the top element of the deque without eliminating
    @Override
    public Item first() throws NoSuchElementException{
        if(head==null){
            throw new NoSuchElementException("Lil bro, we can't get anything from a null list");
        }
        else{
            return head.getItem();

        }
    }

    //Checks to see if the list is empty, if it is we throw a NoSuchElementException, if it isn't we return the last element of the deque without eliminating
    @Override
    public Item last() throws NoSuchElementException{
        if(tail==null){
            throw new NoSuchElementException("Lil bro, we can't get anything from a null list");
        }
        else{
            return tail.getItem();

        }
    }
    //Checks to see if the CompletedDeque is null, if the amount of elements is 0 then we will return a true and false if there is more than 0 elements
    @Override
    public boolean isEmpty(){
        return N == 0;
    }
    //simply returns N which is the size of the completedDeque
    @Override
    public int size(){
        return N;
    }
    //toString Method
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        //case if we have an empty dequeue
        if(isEmpty()){
            return "empty";
        }
        //if the dequeue is not empty then we will create a loop with the condition set to !null and get our values to append
        //We will start at the tail and append values from the start of the tail to the head which will be the bottom in this case
        else{
            Node values = tail;
            //As long as the current value isn't null then we will continue this loop
            while (values != null){
                //we use stringbuilder in this case to append the values
                sb.append(values.getItem());
                //if the previous value is not null we will increment it with a space
                if (values.getPrev() != null) {
                    sb.append(" ");
                }
                //once we print the value, we retrieve the previous value
                values = values.getPrev();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        CompletedDeque<Integer> deque = new CompletedDeque<>();

        //standard queue behavior
        deque.enqueueBack(3);
        deque.enqueueBack(7);
        deque.enqueueBack(4);
        deque.dequeueFront();
        deque.enqueueBack(9);
        deque.enqueueBack(8);
        deque.dequeueFront();
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());

        //deque features
        System.out.println(deque.dequeueFront());
        deque.enqueueFront(1);
        deque.enqueueFront(11);
        deque.enqueueFront(3);
        deque.enqueueFront(5);
        System.out.println(deque.dequeueBack());
        System.out.println(deque.dequeueBack());
        System.out.println(deque.last());
        deque.dequeueFront();
        deque.dequeueFront();
        System.out.println(deque.first());
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());
    }
}