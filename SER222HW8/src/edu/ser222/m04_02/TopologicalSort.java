package edu.ser222.m04_02;

/**
 * Interface for classes providing a topological sort of some graph.
 *
 * @author Sedgewick and Wayne, Acuna
 * @version 2/17/24
 */
public interface TopologicalSort
{
    /**
     * Returns an iterable object containing a topological sort. Returns null if the graph is not a
     * DAG.
     * @return a topological sort.
     */
    public Iterable<Integer> order();
    
    /**
     * Returns true if the graph being sorted is a DAG, false otherwise.
     * @return is graph a DAG
     */
    public boolean isDAG();
}