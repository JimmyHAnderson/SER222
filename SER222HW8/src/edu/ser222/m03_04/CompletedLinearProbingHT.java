package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with linear probing.
 * 
 * @author (Jimmy Anderson), Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedLinearProbingHT<Key, Value> implements ProbingHT<Key, Value> {
    private int N;
    private int M;
    private Entry<Key, Value>[] entries;
    //any constructors must be made public
    public CompletedLinearProbingHT() {
        this.N = 0;
        this.M = 997;
        this.entries = (Entry<Key, Value>[]) new Entry[this.M];
    }
    //takes a key and and integer and turns it into hashcode
    @Override
    public int hash(Key key, int i) {
        return ((key.hashCode() & 0x7fffffff) + i) % M;
    }
//takes a key and value and puts it as an entry
    @Override
    public void put(Key key, Value val) {
        int i = 0;
        for (i = hash(key, i); entries[i] != null; i = (i + 1) % M) {
            if (entries[i].getKey().equals(key)) {
                entries[i].setVal(val);
                return;
            }
        }
        entries[i] = new Entry<>(key, val);
        this.N++;
    }
//returns null if there isn't a value/key otherwise, we return the value.
    @Override
    public Value get(Key key) {
        int i = 0;
        for (i = hash(key, i); entries[i] != null; i = (i + 1) % M) {
            if (entries[i].getKey().equals(key)) {
                return entries[i].getVal();
            }
        }
        return null;
    }
//checks to see if there is the key, if there is then we remove it and decrement N
    @Override
    public void delete(Key key) {
        //if it doesnt contain the key we simnply retain since it doesnt exist
        if (!contains(key)) {
            return;
        }
        int i = hash(key, 0);
        //iterate through the hashset until we get to the desired hashvalue and set it to null
        while (!key.equals(entries[i].getKey())) {
            i = (i + 1) % M;
        }

        entries[i] = null;
        this.N--;
        //rehashing method since we delete the entry
        i = (i + 1) % M;
        while (entries[i] != null) {
            Entry<Key, Value> entryToRehash = entries[i];
            entries[i] = null;
            this.N--;
            put(entryToRehash.getKey(), entryToRehash.getVal());
            i = (i + 1) % M;
        }

    }
//returns true or false if key existss
    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }
//returns true or false if N is empty
    @Override
    public boolean isEmpty() {
        return N == 0;
    }
//returns size
    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> allKeys = new LinkedList<>();
        for (Entry<Key, Value> entry : entries) {
            if (entry != null) {
                allKeys.add(entry.getKey());
            }
        }
        return allKeys;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE PROBINGHT INTERFACE.
//returns the length of the internal array
    @Override
    public int getM() {
        return M;

    }

    @Override
    public Object getTableEntry(int i) {
        return entries[i];
    }

    public static class Entry<Key, Value> {
        private Key key;
        private Value val;

        public Entry(Key newKey, Value newVal) {
            this.key = newKey;
            this.val = newVal;
        }

        public Key getKey() {
            return this.key;
        }

        public void setVal(Value newVal) {
            this.val = newVal;
        }

        public Value getVal() {
            return this.val;
        }
    }
}