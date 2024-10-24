package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Implements an editable graph with sparse vertex support
 *
 * Completion time: 4 hours
 *
 * @author Jimmy Anderson
 * @version 1
 */
//constructor to intialize an empty d graph with no verticies and edges
public class BetterDiGraph implements EditableDiGraph {
    private int V;
    private int E;
    private HashMap<Integer, LinkedList<Integer>> adj;

    public BetterDiGraph() {
        this.V = 0;
        this.E = 0;
        adj = new HashMap<>();
    }
//constructor for a directed graph but with a  defined number of vertices with no edges
    public BetterDiGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new HashMap<>();
    }
//this checks to see if the graph is empty, if it is empty then we add the vertix to both v and w
    //if w is filled t hen we put it in v
    //if v is fileld then we put it in w
    //thgen we increment
    //added a method to see if an edge exists, if it doesnt then we add it and properly increment it
    @Override
    public void addEdge(int v, int w) {
        if (isEmpty()) {
            addVertex(v);
            addVertex(w);
        }
        if (!containsVertex(v)) {
            addVertex(v);
        }
        if (!containsVertex(w)) {
            addVertex(w);
        }
        if(!adj.get(v).contains(w)) {
            adj.get(v).add(w);
            this.E++;
        }
    }
//this method adds a new vertic to a graph if it doesnt exist and increments
    @Override
    public void addVertex(int v) {
        if (containsVertex(v)) {
            return;
        }
        adj.put(v, new LinkedList<>());
        this.V++;
    }
//returns an iterable collection of vertices adjacent to a given vertex which can be reached by an given edge
    @Override
    public Iterable<Integer> getAdj(int v) {
        return adj.get(v);
    }

//returns the number of edges in a given graph
    @Override
    public int getEdgeCount() {
        return E;
    }
//returns the in degree of a given vertex which is the number of edges directed towards that vertex

    @Override
    public int getIndegree(int v) throws NoSuchElementException {
        if (!adj.containsKey(v)) {
            throw new NoSuchElementException();
        }
        int inDegCount = 0;
        for (LinkedList<Integer> list : adj.values()) {
            for (int vertex : list) {
                if (vertex == v) {
                    inDegCount++;
                }
            }
        }
        return inDegCount;
    }
//returns the amount of vertices
    @Override
    public int getVertexCount() {
        return V;
    }
//checks to see if an edge exists, if it does then we remove it and decrement E
    @Override
    public void removeEdge(int v, int w) {
        //only continue if we can actually access the potential edge from the vertex
        if (containsVertex(v)) {
            LinkedList<Integer> adjList = adj.get(v);
            // Remove edge if it exists
            if (adjList.contains(w)) {
                adjList.remove((Integer) w);
                this.E--; //only decrement E if we actually remove it
            }
        }
    }
//checks to see if a vertex exists and removes it along with its edges, if it exists then we remove V and its surrounding edges and decrement V
    @Override
    public void removeVertex(int v) {
        boolean removed = false;
        if (containsVertex(v)) {
            LinkedList<Integer> adjList = adj.get(v);
            this.E -= adjList.size();
            adj.remove(v);

            for (Integer key : adj.keySet()) {
                LinkedList<Integer> list = adj.get(key);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == v) {
                        list.remove(i);
                        removed = true;
                        this.E--;
                        break;
                    }
                }
            }

            this.V--; // Decrement vertex count
        }
    }

    //returns an iterable collection of all vertices in a graph and returns all vertices
    @Override
    public Iterable<Integer> vertices() {
        return adj.keySet();
    }
//returns true if the graph is empty
    @Override
    public boolean isEmpty() {
        return this.V == 0;
    }
//checks to see if graph contains a vertex
    @Override
    public boolean containsVertex(int v) {
        return adj.get(v) != null;
    }
}