package edu.ser222.m02_02;

/**
 * Implements various divide and conquer algorithms.
 *
 * Last updated 4/2/2022.
 *
 * Completion time: (3 hours)
 *
 * @author (Jimmy Anderson), Acuna, Sedgewick and Wayne
 * @verison (version 1)
 */
import java.util.Random;

public class CompletedMerging implements MergingAlgorithms {

    @Override
    public <T extends Comparable> Queue<T> mergeQueues(Queue<T> q1, Queue<T> q2) {
        //basic implementation of constructor
        Queue returnQueue = new ListQueue();
        //we will check to see if both of these queues have at least 1 value
        while(!q1.isEmpty() || !q2.isEmpty())
        {
            //if q1 is empty then we will empty out q2 utilizing enqueue onto returnQueue
            if(q1.isEmpty()) returnQueue.enqueue(q2.dequeue());
            //if q2 is empty then we will empty out q2 utilizing enqueue onto returnQueue
            else if(q2.isEmpty()) returnQueue.enqueue(q1.dequeue());
            //if the top element of q1 is smaller than the top element of q2 then we will dequeue the element from q1
            //and enqueue it to returnQueue
            else if(q1.peek().compareTo(q2.peek()) < 0)
                returnQueue.enqueue(q1.dequeue());
            //same as above but will do it for q2 instead
            else
                returnQueue.enqueue(q2.dequeue());
        }
        //returns the merged queue after the loops complete
        return returnQueue;
    }
//we will simply just run mergesort utilizing recursion
    @Override
    public void sort(Comparable[] a) {
        mergesort(a);
    }
    //mergeSort method that uses arrays as our parameters
    //we will check for the base case to see if an array already has a length of 1
    //otherwise we will split our array into a left side and a right side
    //we will then fill the left and the right side using a for loop and then use our mergesort method for the left and right side

    @Override
    public Comparable[] mergesort(Comparable[] a) {
        Comparable[] tempArray;
        //base case where an array has a length of 1 meaning that it is already sorted

        if(a.length == 1||a.length == 0) {
            return a;
        }
        //this basically creates a mid-section for our array into 2 even sections
        int mid = a.length/2;

        // mergesorts the left side
        Comparable[] left = new Comparable[mid];
        //iterates through a to fill on the left side
        for(int i = 0; i < mid; i++)
        {
            left[i] = a[i];
        }
        mergesort(left);

        // mergesorts the right side
        Comparable[] right = new Comparable[a.length - mid];
        //iterates through a to fill on the right side
        for(int i = mid; i < a.length; i++)
        {
            right[i - mid] = a[i];
        }
        mergesort(right);

        //combines both he left and right side using the merge method
        tempArray = merge(left,right);
        for(int i = 0; i < tempArray.length; i++)
        {
            a[i] = tempArray[i];
        }

        return tempArray;
    }
//merge method where we input our 2 sorted arrays a and b(left and right) and
    @Override
    public Comparable[] merge(Comparable[] a, Comparable[] b) {
        //we create two arrays one with the array we actually return and a temporary one to swap elements with
        Comparable[] newArray = new Comparable[a.length + b.length];
        Comparable[] temp = new Comparable[a.length + b.length];
        //iterate through the array with a for loop to fill up the array on temp
        for(int i = 0; i < temp.length; i++)
        {
            if(i < a.length)
                temp[i] = a[i];
            //case where temp[i] is greater than a[i] in which we put a b value in a instead
            else temp[i] = b[i - a.length];
        }
        //reset i to 0 and initialize j to be our two pointer system
        //we basically use one pointer to track 1 array to sort the first part and j to track the other part of the array
        //while we iterate through k
        int i = 0;
        int j = a.length;

        // we merge it back to a[] utilizing our two pointer system
        for (int k = 0; k < temp.length; k++)
        {
            //if i is greater than the length of a, then we fill in k to js value
            if (i > a.length - 1)
                newArray[k] = temp[j++];
                //if j is greater than the length of a, then we fill in k to is value
            else if (j > temp.length - 1)
                newArray[k] = temp[i++];
            // if i or j has gone out of  bounds, then we compare the current element from a and b
            //if j is less then i then we go to the next element which should go to j
            else if (less(temp[j], temp[i]))
                newArray[k] = temp[j++];

            else newArray[k] = temp[i++];
        }
        return newArray;
    }
    //our recursive merging mechanism that will allow for any element to reposition to any other position
    //we will take in array a and create an auxilary array witht he same length as a
    //we will create two help methods that will actually do the recursion

    @Override
    public void shuffle(Object[] a) {
        Object[] aux = new Object[a.length];
        //start of our recursion by going through our helper methods shuffleDown and shuffle Merge
        shuffleDown(a, aux, 0, a.length - 1);
    }
    //Helper method for shuffle()
    private void shuffleDown(Object[] a, Object[] aux, int start, int end)
    {
        //if our end point is less than our start/base case
        if(end <= start) {
            return;
        }
        //creates a mid by dividing the section
        int mid = (start + (end - start)/2);

        //sorts the left called by recursion
        shuffleDown(a, aux, start, mid);
        //sorts the right called by recursion
        shuffleDown(a, aux, mid + 1, end);
        //merges the left and the right
        shuffleMerge(a, aux, start, mid, end);
    }

    //Helper method for the shuffle method
    //this will be responsible for merging the two sections of the array in a random order
    private void shuffleMerge(Object[] a, Object[] aux, int start, int mid, int end)
    {
        //copies the array
        for (int i = start; i <= end; i++){
            aux[i] = a[i];
        }
        //initializing our two pointer system
        int i=start;
        int j = mid + 1;
        //we iterate through the array with k and check to see if we increment i or j based on the comparisons
        for (int k = start; k <= end; k++){
            if (i > mid) {
                a[k] = aux[i++];
            } else if (j > end) {
                a[k] = aux[i++];
            } else if (randomBool()) {
                a[k] = aux[j++];
            } else{
                a[k] = aux[i++];
            }
        }
    }

    //Helper method for the shuffle method
    //this basically returns a true or false for random boolean value
    private static boolean randomBool()
    {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    /**
     * entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<String> q1 = new ListQueue<>(); q1.enqueue("E"); q1.enqueue("L"); q1.enqueue("O"); q1.enqueue("R"); q1.enqueue("T");
        Queue<String> q2 = new ListQueue<>(); q2.enqueue("A"); q2.enqueue("E"); q2.enqueue("M"); q2.enqueue("P"); q2.enqueue("S"); q2.enqueue("X");
        Queue<Integer> q3 = new ListQueue<>(); q3.enqueue(5); q3.enqueue(12); q3.enqueue(15); q3.enqueue(17); q3.enqueue(20);
        Queue<Integer> q4 = new ListQueue<>(); q4.enqueue(1); q4.enqueue(4); q4.enqueue(12); q4.enqueue(13); q4.enqueue(16); q4.enqueue(18);

        MergingAlgorithms ma = new CompletedMerging();

        //Q1 - sample test cases
        Queue merged1 = ma.mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = ma.mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.shuffle(b);
        show(b);
        
        ma.shuffle(b);
        show(b);
    }

    //below are utilities functions, please do not change them.
        
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}