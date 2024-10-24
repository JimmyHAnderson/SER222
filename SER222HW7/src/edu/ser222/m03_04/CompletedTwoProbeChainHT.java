package edu.ser222.m03_04;
import java.util.LinkedList;

/**
 * A symbol table implemented using a hashtable with chaining.
 * Does not support load balancing or resizing.
 * 
 * @author (Jimmy Anderson), Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedTwoProbeChainHT<Key, Value> implements TwoProbeChainHT<Key, Value> {
    private int N; // number of key value pairs
    private int M; // hash table size
    private LinkedList<Entry<Key, Value>>[] entries;

    //any constructors must be made public
    public CompletedTwoProbeChainHT() {
        this.N = 0;
        this.M = 97;
        this.entries = (LinkedList<Entry<Key, Value>>[]) new LinkedList[M];
        for (int i = 0; i < M; i++) {
            entries[i] = new LinkedList<>();
        }
    }
//Computes the hash (will be used as an index) for a key. We will use 2 methods for this and name it hash1 and hash 2
    @Override
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
//
    @Override
    public int hash2(Key key) {
        return (((key.hashCode() & 0x7f) % M) * 31) % M;
    }
//this method basically checks to see if N is 0 first, if it is then we basically hash it anda add it otherwise we will run hash 2
    //check to see if the size of hash1 or hash2 is bigger and either add it into hash1 or hash2
    //then we increment N
    @Override
    public void put(Key key, Value val) {
        Entry<Key, Value> newEntry = new Entry<>(key, val);
        int hash1 = hash(key);
        int hash2 = hash2(key);

       // checks to see if the key already exists in either hash1 or hash2
        for (Entry<Key, Value> entry : entries[hash1]) {
            if (entry.getKey().equals(key)) {
                entry.val = val;
                return;
            }
        }
        for (Entry<Key, Value> entry : entries[hash2]) {
            if (entry.getKey().equals(key)) {
                entry.val = val;
                return;
            }
        }

        //case where the key doesnt exist
        if (entries[hash1].size() <= entries[hash2].size()) {
            entries[hash1].add(newEntry);
        } else {
            entries[hash2].add(newEntry);
        }
        this.N++;
    }

//method that simply returns the value
    @Override
    public Value get(Key key) {
        int hash1 = hash(key);
        int hash2 = hash2(key);
        for (Entry<Key, Value> entry : entries[hash1]) {

            if (entry.getKey().equals(key)) {
                return entry.getVal();
            }
        }
        for (Entry<Key, Value> entry : entries[hash2]) {
            if (entry.getKey().equals(key)) {
                return entry.getVal();
            }
        }
        return null;
    }
//checks to see if a key value exists, and if it does, then we remove it from the hashset and decrement N only if we remove the value
    @Override
    public void delete(Key key) {
        int hash1 = hash(key);
        int hash2 = hash2(key);
        boolean removed=false;
    //checks hash 1
        for (Entry<Key, Value> entry : entries[hash1]) {
            if (entry.getKey().equals(key)) {
                entries[hash1].remove(entry);
                removed = true;
                break;
            }
        }

       //checks hash 2
        if (!removed) {
            for (Entry<Key, Value> entry : entries[hash2]) {
                if (entry.getKey().equals(key)) {
                    entries[hash2].remove(entry);
                    removed = true;
                    break;
                }
            }
        }


        if (removed) {
            this.N--;
        }
    }
//checks to see if hash1 or hash2 contains the value and returns true/false depending on the result
    @Override
    public boolean contains(Key key) {
        int hash1 = hash(key);
        int hash2 = hash2(key);
        for (Entry<Key, Value> entry : entries[hash1]) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        for (Entry<Key, Value> entry : entries[hash2]) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
//checks to see if something is empty
    @Override
    public boolean isEmpty() {
        return N == 0;
    }
//simply returns size
    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> allKeys = new LinkedList<>();
        for (LinkedList<Entry<Key, Value>> chain : entries) {
            if (!chain.isEmpty()) {
                for (Entry<Key, Value> entry : chain) {
                    if (entry != null) {
                        allKeys.add(entry.getKey());
                    }
                }
            }
        }
        return allKeys;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE TWOPROBECHAINHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getChainSize(int i) {
        return entries[i].size();
    }

    public static class Entry<Key, Value> {
        private final Key key;
        private Value val;

        public Entry(Key newKey, Value newVal) {
            this.key = newKey;
            this.val = newVal;
        }

        public Key getKey() {
            return this.key;
        }

        public Value getVal() {
            return this.val;
        }
    }
}