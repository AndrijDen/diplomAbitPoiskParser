package main;

import graphLogic.RelationshipEdge;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.cycle.SzwarcfiterLauerSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.Arrays;
import java.util.List;

public class TestGraph {
    public void testJgraphtLib() {

        // Test 2

        SzwarcfiterLauerSimpleCycles graph = new SzwarcfiterLauerSimpleCycles(gr2Relay());
//        System.out.println(gr2());
//        System.out.println(gr2Relay());
        System.out.println(graph.getGraph());
        List cycles = graph.findSimpleCycles();
//        findOptimalRes(cycles);
//        System.out.println(cycles);

        Graph<String, RelationshipEdge> directedGraph = (Graph<String, RelationshipEdge>) cycles.get(0);

        System.out.println(directedGraph);

    }

    public List findOptimalRes(List cycles) {
        for (int i = 0; i < cycles.size()-1; i++) {
            for (int j = i+1; j < cycles.size(); j++) {
                if (isSubArray((List) cycles.get(i), (List) cycles.get(j))) {
                    cycles.remove(j);
                }
            }
        }
        return cycles;
    }

    static boolean isSubArray(List Arr1, List Arr2)
    {
        int arr1Length = Arr1.size();
        int arr2Length = Arr2.size();
        int i = 0, j = 0;
        while (i < arr1Length && j < arr2Length) {
            if (Arr1.get(i) == Arr2.get(j)) {
                i++;
                j++;
                // If array B is completely
                // traversed
                if (i == arr1Length || j == arr2Length)
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Graph gr1() {
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");
        directedGraph.addVertex("5");


        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "0");
        directedGraph.addEdge("0", "2");
        directedGraph.addEdge("2", "0");
        directedGraph.addEdge("2", "3");
        directedGraph.addEdge("3", "1");
        directedGraph.addEdge("1", "4");
        directedGraph.addEdge("4", "5");
        directedGraph.addEdge("5", "2");
        return directedGraph;
    }
    public Graph gr2() {
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph(DefaultEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");

        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "2");
        directedGraph.addEdge("2", "1");
        directedGraph.addEdge("1", "3");
        directedGraph.addEdge("3", "0");
        return directedGraph;
    }
    public Graph gr2Relay() {
        Graph<String, RelationshipEdge> directedGraph =
                new DefaultDirectedGraph<String, RelationshipEdge>(RelationshipEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "2", new RelationshipEdge("1q"));
        directedGraph.addEdge("2", "1", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "3", new RelationshipEdge("3q"));
        directedGraph.addEdge("3", "0", new RelationshipEdge("4q"));
        return directedGraph;
    }
    public Graph gr3() {
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");

        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "0");
        return directedGraph;
    }
    public Graph gr4() {
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");
        directedGraph.addVertex("5");
        directedGraph.addVertex("6");
        directedGraph.addVertex("7");
        directedGraph.addVertex("8");
        directedGraph.addVertex("9");
        directedGraph.addVertex("10");
        directedGraph.addVertex("11");
        directedGraph.addVertex("12");

        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("0", "2");
        directedGraph.addEdge("1", "3");
        directedGraph.addEdge("3", "6");
        directedGraph.addEdge("6", "0");
        directedGraph.addEdge("2", "4");
        directedGraph.addEdge("4", "5");
        directedGraph.addEdge("5", "0");

        directedGraph.addEdge("6", "7");
        directedGraph.addEdge("6", "8");
        directedGraph.addEdge("8", "6");
        directedGraph.addEdge("7", "9");
        directedGraph.addEdge("9", "10");
        directedGraph.addEdge("10", "11");
        directedGraph.addEdge("11", "12");
        directedGraph.addEdge("12", "6");

        return directedGraph;
    }
    public Graph gr5() {
        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");
        directedGraph.addVertex("5");
        directedGraph.addVertex("6");
        directedGraph.addVertex("7");
        directedGraph.addVertex("8");
        directedGraph.addVertex("9");
        directedGraph.addVertex("10");
        directedGraph.addVertex("11");
        directedGraph.addVertex("12");

        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "2");
        directedGraph.addEdge("2", "3");
        directedGraph.addEdge("1", "5");
        directedGraph.addEdge("5", "1");
        directedGraph.addEdge("5", "9");
        directedGraph.addEdge("9", "2");
        directedGraph.addEdge("2", "9");

        directedGraph.addEdge("2", "4");
        directedGraph.addEdge("4", "7");
        directedGraph.addEdge("7", "4");
        directedGraph.addEdge("4", "7");
        directedGraph.addEdge("7", "4");
        directedGraph.addEdge("4", "8");
        directedGraph.addEdge("4", "8");
        directedGraph.addEdge("8", "4");
        directedGraph.addEdge("8", "11");
        directedGraph.addEdge("11", "2");
        directedGraph.addEdge("3", "6");
        directedGraph.addEdge("6", "10");
        directedGraph.addEdge("6", "10");
        directedGraph.addEdge("10", "6");
        directedGraph.addEdge("10", "12");
        directedGraph.addEdge("12", "0");

        return directedGraph;
    }
}
