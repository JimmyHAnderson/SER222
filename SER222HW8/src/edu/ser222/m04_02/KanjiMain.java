package edu.ser222.m04_02;

import java.util.HashMap;

/**
 * A class that provides various methods for generating a topological sort of a kanji graph.
 *
 * @author Acuna, Buckner
 * @version 2/17/24
 */

public interface KanjiMain {

    /**
     * Loads a data file for kanji, uses it to populate a hashmap that maps IDs to characters, and
     * adds the IDs as nodes in the graph. See the assignment PDF for file format.
     *
     * @param filename filename to be loaded.
     * @param graph graph to be populated.
     * @return - hashmap of the integer keys and kanji values from the selected file.
     */
    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph);

    /**
     * Loads a data file for components, and uses it to add edges to the graph. See the assignment
     * PDF for file format.
     *
     * @param filename filename to be loaded.
     * @param graph graph to be populated.
     */
    public void loadDataComponents(String filename, EditableDiGraph graph);

    /**
     * Creates a four line string containing the kanji in a valid topological ordering.
     *
     * The first line must contain "Original:", followed by a line containing the original kanji in
     * the graph, a line containing "Sorted:", and then a line containing the sorted kanji. See the
     * assignment PDF for an example. The two kanji lines must contain only the kanji characters, no
     * other formatting or spaces.
     *
     * @param graph a EditableDiGraph object.
     * @param topSort a TopologicalSort object.
     * @param kanjiMap  hashmap of the integer keys and kanji values.
     * @return - String of the printed kanji in a valid topological ordering.
     */
    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort, HashMap<Integer, String> kanjiMap);
}
