package edu.ser222.m02_01;

import java.util.Random;


/**
 * (basic description of the program or class)
 * 
 * Completion time: (estimation of hours spent on this program)
 *
 * @author (Jimmy Anderson), Acuna, Sedgewick
 * @version (version 1)
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/
    //instance variables
    private double smallTime;
    private double largeTime;
    static Random random = new Random(25L);

    //this  method will essentially take an int which will be the size and return an Integer array
    //The integer array will basically contain half 0s and half 1s
    //the best way we can do this approach is to iterate throughout the array, if the index is within the first half,
    //then it will be 0s while the other half will be 1s
    //example if we put in a size of 6, the array will return [0,0,0,1,1,1]
    @Override
    public Integer[] generateTestDataBinary(int size){
        Integer[] a = new Integer[size];
        //iterating through the array
        for(int i = 0; i < a.length; i++){
            //conditional to check if the index is within the first half
            //if it is the first half we set the array to 0
            if (i < a.length/2){
                a[i] = 0;
            }
            //if it is the second half, we set it to 1
            else{
                a[i] = 1;
            }
        }
        return a;
    }
    //This method will be very similar for the first half, we will iterate through the first half and fill it with 0s
    //then we will run a conditional to check if we are at the midpoint, once we are, we will increment a count
    //each count will increment once the remaining half subarray is filled
    //for example given an array size of 16 we will return
    //[0,0,0,0,0,0,0,0,1,1,1,1,2,2,3,4]
    @Override
    public Integer[] generateTestDataHalves(int size){
        Integer[] a = new Integer[size];
        //now we have to create midpoints and an incrementer
        //we will keep shifting the midpoint by dividing into 2s and increase increment
        int middle=(size/2);
        int index = middle; // current starting index
        int count = 0; // current number to fill
            for(int i = 0; i < size; i++){
                if(i==index){
                    middle = (middle/2)+(middle%2);
                    index += middle;
                    count++;
                }
                a[i] = count;
            }

        return a;
    }
    //very similar to the binary half but instead of 0s we will fill with random
    //we will use the same implementation of generateTestDataBinary but instead of filling it with 1s we will fill with random
    @Override
    public Integer[] generateTestDataHalfRandom(int size) {
        Integer[] a = new Integer[size];
        //iterating through the array
        for (int i = 0; i < a.length; i++) {
            //conditional to check if the index is within the first half
            //if it is the first half we set the array to 0
            if (i < a.length / 2) {
                a[i] = 0;
            }
            //if it is the second half, we set it to random
            else {
                a[i] = random.nextInt(Integer.MAX_VALUE);
            }

        }
        return a;
    }
//simply running to the double formula which is basically log(t2/t1)
    //since java log is based on 10 we have to utilize log rules and take the log of t2/t1 and divide it by math.log2
    @Override
    public double computeDoublingFormula(double t1, double t2){

        return Math.log(t2/t1)/Math.log(2);
    }
//we will utilize stopwatches to test for the small and large input
    //then we basically input the insertion sort with small and large and return the doubling formula using these values
    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large){
        Stopwatch smallWatch = new Stopwatch();
        Stopwatch largeWatch = new Stopwatch();

        insertionSort(small);
        insertionSort(large);

        this.smallTime = smallWatch.elapsedTime();
        this.largeTime = largeWatch.elapsedTime();

        return computeDoublingFormula(this.smallTime, this.largeTime);

    }
//very similar to benchmark for insertion sortbut we basically change insertion to shellsort lol
    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large){
        Stopwatch smallWatch = new Stopwatch();
        Stopwatch largeWatch = new Stopwatch();

        shellsort(small);
        shellsort(large);

        this.smallTime = smallWatch.elapsedTime();
        this.largeTime = largeWatch.elapsedTime();

        return computeDoublingFormula(this.smallTime, this.largeTime);
    }
//benchmark method we will basically create 6  arrays to test binary, data halves, and random and
// run each of these methods with the original size as well as a doubled size
    //then we run the benchmarks for both insertion and shellsort utilizing the 2 values
    @Override
    public void runBenchmarks(int size){

        //now we create new arrays to test for each of these methods
        //after we create the arrays, then we can run the sorts on each of the sort benchmarks
        Integer[] normalBinary=generateTestDataBinary(size);
        Integer[] doubleBinary=generateTestDataHalfRandom(2*size);

        Integer[] normalHalf=generateTestDataHalves(size);
        Integer[] doubleHalf=generateTestDataHalves(2*size);

        Integer[] normalRandom=generateTestDataHalfRandom(size);
        Integer[] doubleRandom=generateTestDataHalfRandom(2*size);


        double binaryShellsort=benchmarkShellsort(normalBinary,doubleBinary);
        double binaryInsertionsort=benchmarkInsertionSort(normalBinary,doubleBinary);



        double halfShellsort=benchmarkShellsort(normalHalf,doubleHalf);
        double halfInsertionsort=benchmarkInsertionSort(normalHalf,doubleHalf);


        double randomShellsort=benchmarkShellsort(normalRandom,doubleRandom);
        double randomInsertionsort=benchmarkInsertionSort(normalRandom,doubleRandom);

        System.out.print("Insertion    Shellsort");
        System.out.printf("\nBin    %.3f    %.3f", binaryInsertionsort,binaryShellsort);
        System.out.printf("\nHalf    %.3f    %.3f", halfInsertionsort,halfShellsort);
        System.out.printf("\nRanInt    %.3f    %.3f", randomInsertionsort,randomShellsort);

    }
    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 4096;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        
        me.runBenchmarks(size);
    }
}
