package edu.ser222.m04_02;

/**
 * (basic description of the program or class)
 *
 * Completion time: (12 hours)
 *
 * @author (Jimmy Anderson), Acuna, Buckner
 * @version (3 why do I have to rewite its so joever for me lol))
 */

//Note: not all of these packages may be needed.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CompletedMain implements KanjiMain {

    //Do not add any member variables to this class.

    //turns out it was probably my location of the .text files I think???? seemed to work after I posted multiple copies
    // of it lol
//this loads the Kanji characters from a specified file and we add their IDS to a directed graph as vertices
    //we also want to skip the first line since it doesn't seem that its useful
    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph) {
        HashMap<Integer, String> kanjiMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String kanji = parts[1];
                        kanjiMap.put(id, kanji);
                        graph.addVertex(id);
                    } catch (NumberFormatException e) {
                        // Skip lines that can't be parsed as integers
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kanjiMap;
    }
    //this loads data about the Kanji character components from a specified file and adds the directed edges to the
    //corresponding vertices in the graphj
    //once again wer attempt to skip the comments but split each line and add edges between the two kanji compoments

    public void loadDataComponents(String filename, EditableDiGraph graph) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    try {
                        int src = Integer.parseInt(parts[0]);
                        int dst = Integer.parseInt(parts[1]);
                        graph.addEdge(src, dst);
                    } catch (NumberFormatException e) {
                        // Skip lines that can't be parsed as integers
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this builds a string that show sthe original Kanji characters and their sorted order based ont he graph relationships
    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort, HashMap<Integer, String> kanjiMap) {
        String result = "Original:\n";
        for (String value : kanjiMap.values()) {
            result += value;
        }
        result += "\nSorted:\n";
        for (int i : topSort.order()) {
            result += kanjiMap.get(i);
        }
        result += "\n";
        return result;

    }

    public static void main(String[] args) {
        /***************************************************************************
         * START - CORE DRIVER LOGIC, DO NOT MODIFY                                *
         **************************************************************************/
        String FILENAME_KANJI = "data-kanji.txt";
        String FILENAME_COMPONENTS = "data-components.txt";

        KanjiMain driver = new CompletedMain();
        EditableDiGraph graph = new BetterDiGraph();

        HashMap<Integer, String> kanjiMap = driver.loadKanji(FILENAME_KANJI, graph);
        driver.loadDataComponents(FILENAME_COMPONENTS, graph);

        TopologicalSort intuitive = new IntuitiveTopological(graph);

        System.out.println(driver.buildOrderString(graph, intuitive, kanjiMap));

        /***************************************************************************
         * END - CORE DRIVER LOGIC, DO NOT MODIFY                                  *
         **************************************************************************/

        //NOTE: feel free to temporarily comment out parts of the above code while
        //you incrementally develop your program. Just make sure all of it is there
        //when you test the final version of your program.
    }
}