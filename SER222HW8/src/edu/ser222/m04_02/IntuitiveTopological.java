package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Interface for classes providing a topological sort of some graph.
 *
 * Completion time: 4 hours
 *
 * @author Jimmy Anderson
 * @version 1
 */

public class IntuitiveTopological implements TopologicalSort {
    private LinkedList<Integer> order;
    private boolean hasCycle;

//constructor
    public IntuitiveTopological(EditableDiGraph graph) {
        order = new LinkedList<>();
        DirectedCycle cycleFinder = new DirectedCycle(graph);

        // Checkss to see if the graph is acyclic
        if (!cycleFinder.hasCycle()) {
            hasCycle = false;
            findOrder(graph); // Proceed to find the topological order
        } else {
            hasCycle = true;
        }
    }


///returns the topological order of the vertices
    @Override
    public Iterable<Integer> order() {
            if(hasCycle==true){
                return null;
            }
            return order;
    }
//checks to see if the greaph is a directed acyclic graph
    @Override
    public boolean isDAG() {
        return !hasCycle;
    }
//this method builds the topological order hby removing vertices with zero
    //once we remove the cverticesa from the graph we add them to the order list
    //any vertice with zero in degree are added based on order until every verticle is removed from the graph
    private void findOrder(EditableDiGraph graph) {
        LinkedList<Integer> vertsToDel = new LinkedList<>();
        while (graph.getVertexCount() != 0) {
            for (int i : graph.vertices()) {
                int inDegree = graph.getIndegree(i);
                if (inDegree == 0) {
                    order.add(i);
                    vertsToDel.add(i);
                }
            }
            for (int j : vertsToDel) {
                graph.removeVertex(j);
            }
            vertsToDel.clear();
        }
    }

    private class DirectedCycle {
        private HashMap<Integer, Boolean> marked;
        private HashMap<Integer, Integer> edgeTo;
        private LinkedList<Integer> cycle;
        private HashMap<Integer, Boolean> onStack;
//constructor
        public DirectedCycle(EditableDiGraph graph) {
            Iterable<Integer> verts = graph.vertices();
            onStack = new HashMap<>();
            for (int v : verts) {
                onStack.put(v, false);
            }
            edgeTo = new HashMap<>();
            marked = new HashMap<>();
            for (int v : verts) {
                marked.put(v, false);
            }
            for (int v : verts) {
                if (!marked.get(v)) {
                    dfs(graph, v);
                }
            }
        }
//explores the graph and detect cycles
//when we traverse, it should mark the verticles and tracks back  the edges to give us the cycles
        private void dfs(EditableDiGraph graph, int v) {
            onStack.put(v, true);
            marked.put(v, true);
            for (int w : graph.getAdj(v)) {
                if (this.hasCycle()) {
                    return;//stop if we find a cycle
                } else if (!marked.get(w)) {
                    edgeTo.put(w, v);
                    dfs(graph, w);
                } else if (onStack.get(w)) {
                    cycle = new LinkedList<>();
                    for (int x = v; x != w; x = edgeTo.get(x)) {
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }
            //we mark the vertex as not on the stack after exploration
            onStack.put(v, false);
        }

//returns true if there is a cycle during DFS
        public boolean hasCycle() {
            return cycle != null;
        }
//returns the verticles of a cycle if it exists
        public Iterable<Integer> cycle() {
            return cycle;
        }
    }
}