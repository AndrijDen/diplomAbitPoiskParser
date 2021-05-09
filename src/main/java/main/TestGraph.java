package main;

import graphLogic.RelationshipEdge;
import models.StudentTransitionData;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.cycle.Cycles;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.cycle.SzwarcfiterLauerSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.*;

// Try use JGraphT Lib - dont have graph with labeled edges

public class TestGraph {

    private ArrayList<String> usedNames = new ArrayList();
    private ArrayList<List<String>> uniqueCycles = new ArrayList();
    private ArrayList<List<String>> cyclesWithEvenEdges = new ArrayList();

    public void testJgraphtLib() {
        testFindCycles();
//        testFindStrongConnectivity();
    }

    public void testFindEulCycle() {

//        EulerianCycleAlgorithm strongConGraphs = new HierholzerEulerianCycle(gr5ML());

//        System.out.println("GetStrConComp" + strongConGraphs);
    }

    public void testFindStrongConnectivity() {

//        StrongConnectivityAlgorithm strongConGraphs = new  KosarajuStrongConnectivityInspector(grTestFindingStrongConnectivity());

        StrongConnectivityAlgorithm strongConGraphs = new  KosarajuStrongConnectivityInspector(grTestFindingStrongConnectivitynoLable());

        System.out.println("GetStrConComp" + strongConGraphs.getStronglyConnectedComponents());
        findAllCycles(strongConGraphs.getStronglyConnectedComponents());
    }

    public void findStrongConnectedGraphs(Graph graph) {
        StrongConnectivityAlgorithm strongConGraphs = new  KosarajuStrongConnectivityInspector(graph);
//        System.out.println("GetStrConComp" + strongConGraphs.getStronglyConnectedComponents());
        findAllCycles(strongConGraphs.getStronglyConnectedComponents());
    }

    public void findAllCycles(List<Graph> strongConnectedGraphs) {
        for (int i = 0; i < strongConnectedGraphs.size(); i++) {
            if (strongConnectedGraphs.get(i).vertexSet().size() > 1) {
//                Find all cycles
//
//                Then form List of Data with student and university info
//                List studInfoList;
                System.out.println("cycles" + strongConnectedGraphs.get(i));
                usedNames.clear();
//                findCycles(strongConnectedGraphs.get(i));
            }
        }
    }

    public void findCycles(Graph graphWithCycles) {
        SzwarcfiterLauerSimpleCycles graph = new SzwarcfiterLauerSimpleCycles(graphWithCycles);
        List cycles = graph.findSimpleCycles();
        findOptimalRes(cycles);
        System.out.println("cycles" + cycles);
        getStudentsTransitionsData(cycles, graphWithCycles);
    }

    public void testFindCycles() {
//        SzwarcfiterLauerSimpleCycles graph = new SzwarcfiterLauerSimpleCycles(gr5M());
        SzwarcfiterLauerSimpleCycles graph = new SzwarcfiterLauerSimpleCycles(gr5ML());
        List cycles = graph.findSimpleCycles();

        findOptimalResFromCycles(cycles, gr5ML());
        addCycleIfEvenEdges(cycles);
//        findOptimalRes(cycles);
        System.out.println("cycles" + cycles);
//        getStudentsTransitionsData(cycles, gr5M());
        getStudentsTransitionsData(cycles, gr5ML());
    }

    public List getStudentsTransitionsData(List<List<String>> cycles, Graph graph) {
        List studsTransitionData = new ArrayList();
        for (int i = 0; i < cycles.size(); i++) {
            List<StudentTransitionData> studTransitionData = new ArrayList();
            for (int j = 0; j < cycles.get(i).size(); j++) {
                String fromUniversity = "";
                String toUniversity = "";

                if (j != cycles.get(i).size() - 1) {
                    fromUniversity = cycles.get(i).get(j).toString();
                    toUniversity = cycles.get(i).get(j+1).toString();
                } else {
                    System.out.println("here");
                    fromUniversity = cycles.get(i).get(j).toString();
                    toUniversity = cycles.get(i).get(0).toString();
                }

//                if (cycles.get(i).size() == 2 && (graph.getAllEdges(fromUniversity, toUniversity).size() == graph.getAllEdges(toUniversity, fromUniversity).size() && graph.getAllEdges(fromUniversity, toUniversity).size() > 1)) {
//                    System.out.println("HAHAHAHAHAHAHAHAHAHAHAHHAHAHAHAH");
//                    StudentTransitionData studTransitData = new StudentTransitionData(getNames(graph.getAllEdges(fromUniversity, toUniversity)), fromUniversity, toUniversity);
//                    List<StudentTransitionData> studTransitionDataDoubles = new ArrayList();
//                    studTransitionDataDoubles.add(studTransitData);
//                    studsTransitionData.add(studTransitionDataDoubles);
//                }
                StudentTransitionData studTransitData = new StudentTransitionData(getNames(graph.getAllEdges(fromUniversity, toUniversity)), fromUniversity, toUniversity);
                studTransitionData.add(studTransitData);
//                System.out.println(i + j + "studTransitionData" + studTransitionData);

            }
            studsTransitionData.add(studTransitionData);
//            System.out.println("studTransitionData" + studTransitionData);
        }
        System.out.println(studsTransitionData.size() + "studsTransitionData" + studsTransitionData);
        return studsTransitionData;
    }

    private String getNames(Set<RelationshipEdge> names) {
        String name = "";
        System.out.println("names" + names);
        Iterator<RelationshipEdge> iterator = names.iterator();

        while (iterator.hasNext()) {
            name = iterator.next().getLabel();
            if (!usedNames.contains(name)) {
                usedNames.add(name);
                break;
            }
        }
        return name;
    }

    public List findOptimalResFromCycles(List<List> cycles, Graph graph) {

        sortList(cycles);
        ArrayList<Integer> elementsToRemove = new ArrayList<Integer>();

        for (int i = 0; i < cycles.size(); i++) {
            List element = cycles.get(i);
            if (isUniqueCycle(element, graph)) {
//                if (i != cycles.size()) {
                    for (int j = i + 1; j < cycles.size(); j++) {
                        if (isSameCycles(element, (List) cycles.get(j))) {
                            elementsToRemove.add(j);
                        }
                    }
//                }
            } else {
                elementsToRemove.add(i);
                if (i == cycles.size() - 2) {
                    i = i - 1;
                }
            }
            if (elementsToRemove.size() > 0) {
                Collections.reverse(elementsToRemove);
                for (int k = 0; k < elementsToRemove.size(); k++) {
//                    System.out.println("elementsToRemove.get(k)" + elementsToRemove.get(k));
//                    Not working i dont know why
//                    cycles.remove(elementsToRemove.get(k));
                    int q = elementsToRemove.get(k);
                    cycles.remove(q);
                }
                elementsToRemove.clear();
            }
        }

        System.out.println("findOptimalResFromCycles" + cycles);
        return cycles;
    }

    private void addCycleIfEvenEdges(List<List> cycles) {
        cycles.addAll(cyclesWithEvenEdges);
    }

    private boolean isUniqueCycle(List<String> cycle, Graph graph) {
        if (cycle.size() == 2) {
            int toEdgesSize = graph.getAllEdges(cycle.get(0), cycle.get(1)).size();
            int fromEdgesSize = graph.getAllEdges(cycle.get(1), cycle.get(0)).size();
            if(graph.getAllEdges(cycle.get(0), cycle.get(1)).size() == graph.getAllEdges(cycle.get(1), cycle.get(0)).size()) {
                uniqueCycles.add(cycle);
            }
            if (toEdgesSize > 1 && toEdgesSize > 1) {
                int cyclesSize = 0;
                if (toEdgesSize < fromEdgesSize) {
                    cyclesSize = toEdgesSize;
                } else {
                    cyclesSize = fromEdgesSize;
                }
                while (cyclesSize != 1) {
                    cyclesWithEvenEdges.add(cycle);
                    cyclesSize--;
                }
            }
        } else {
            for (int i = 0; i < uniqueCycles.size(); i++) {
                System.out.println("-------------------------cycle" + cycle + "++" + i);
                if (cycle.contains(uniqueCycles.get(i).get(0)) && cycle.contains(uniqueCycles.get(i).get(1))) {
                    System.out.println("here not unique");
                    return false;
                }
            }
        }
        return true;
    }


    static boolean isSameCycles(List Arr1, List Arr2)
    {
        int arr1Length = Arr1.size();
        int arr2Length = Arr2.size();
        int i = 0, j = 0;
        if (arr1Length == arr2Length) {
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
        }


        return false;
    }

    public void sortList(List<List> cycles) {
        cycles.sort((o1, o2) -> {
            if (o1.size() >= o2.size()) {
                return 1;
            } else if (o1.size() < o2.size()) {
                return -1;
            }
            return 0;
        });
    }

    public List findOptimalRes(List cycles) {

        ArrayList<Integer> elementsToRemove = new ArrayList<Integer>();


        for (int i = 0; i < cycles.size()-1; i++) {
            for (int j = i+1; j < cycles.size(); j++) {
                if (isSubArray((List) cycles.get(i), (List) cycles.get(j))) {
                    elementsToRemove.add(j);
                }
            }

            if (elementsToRemove.size() > 0) {
                Collections.reverse(elementsToRemove);
                for (int k = 0; k < elementsToRemove.size(); k++) {
//                    System.out.println("elementsToRemove.get(k)" + elementsToRemove.get(k));
//                    Not working i dont know why
//                    cycles.remove(elementsToRemove.get(k));
                    int q = elementsToRemove.get(k);
                    cycles.remove(q);
                }
                elementsToRemove.clear();
            }
        }

        System.out.println("cycles test" + cycles);

        return cycles;
    }

    static boolean isSubArray(List Arr1, List Arr2) {
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

        System.out.println(directedGraph.getEdge("1", "2").getLabel());
        return directedGraph;
    }

    public Graph gr23Relay() {
        Graph<String, RelationshipEdge> directedGraph =
                new DefaultDirectedGraph<String, RelationshipEdge>(RelationshipEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("1q"));
        directedGraph.addEdge("0", "1", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("3q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q"));

        System.out.println(directedGraph);
        return directedGraph;
    }

    public Graph gr123Relay() {
        DirectedMultigraph<String, RelationshipEdge> directedGraph =
                new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("1q"));
        directedGraph.addEdge("0", "1", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("3q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q1"));
        directedGraph.addEdge("1", "2", new RelationshipEdge("55"));

        System.out.println(directedGraph);
        System.out.println("1------>" + directedGraph.getEdge("0", "1").getLabel());
        System.out.println("2------>" + directedGraph.getAllEdges("0", "1"));
        System.out.println("2------>" + directedGraph.getAllEdges("1", "0"));
        return directedGraph;
    }

    public Graph grTestFindingStrongConnectivity() {
        DirectedMultigraph<String, RelationshipEdge> directedGraph =
                new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);
        // Test 1

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("1q"));
        directedGraph.addEdge("0", "1", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("3q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q"));
        directedGraph.addEdge("1", "0", new RelationshipEdge("4q1"));
        directedGraph.addEdge("1", "2", new RelationshipEdge("55"));
        directedGraph.addEdge("2", "3", new RelationshipEdge("r1"));
        directedGraph.addEdge("3", "4", new RelationshipEdge("r2"));


        System.out.println(directedGraph);
//        System.out.println("1------>" + directedGraph.getEdge("0", "1").getLabel());
//        System.out.println("2------>" + directedGraph.getAllEdges("0", "1"));
//        System.out.println("2------>" + directedGraph.getAllEdges("1", "0"));
        return directedGraph;
    }

    public Graph grTestFindingStrongConnectivitynoLable() {
        DirectedMultigraph<String, DefaultEdge> directedGraph =
                new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);

        directedGraph.addVertex("0");
        directedGraph.addVertex("1");
        directedGraph.addVertex("2");
        directedGraph.addVertex("3");
        directedGraph.addVertex("4");

        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "0");
        directedGraph.addEdge("0", "1");
        directedGraph.addEdge("1", "0");
        directedGraph.addEdge("1", "2");
        directedGraph.addEdge("2", "3");
        directedGraph.addEdge("3", "2");


        System.out.println(directedGraph);
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

    public Graph gr5M() {
        DirectedMultigraph<String, DefaultEdge> directedGraph =
                new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);

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
    public Graph gr5ML() {
        DirectedMultigraph<String, RelationshipEdge> directedGraph =
                new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);

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

        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));
        directedGraph.addEdge("1", "2", new RelationshipEdge("1q"));
        directedGraph.addEdge("2", "3", new RelationshipEdge("2q"));
        directedGraph.addEdge("1", "5", new RelationshipEdge("3q"));
        directedGraph.addEdge("5", "1", new RelationshipEdge("4q"));
        directedGraph.addEdge("5", "9", new RelationshipEdge("5q"));
        directedGraph.addEdge("9", "2", new RelationshipEdge("6q"));
        directedGraph.addEdge("2", "5", new RelationshipEdge("7q"));
        directedGraph.addEdge("2", "4", new RelationshipEdge("8q"));
        directedGraph.addEdge("4", "7", new RelationshipEdge("9q"));
        directedGraph.addEdge("7", "4", new RelationshipEdge("10q"));
        directedGraph.addEdge("4", "7", new RelationshipEdge("11q"));
        directedGraph.addEdge("7", "4", new RelationshipEdge("12q"));
        directedGraph.addEdge("4", "8", new RelationshipEdge("13q"));
        directedGraph.addEdge("4", "8", new RelationshipEdge("14q"));
        directedGraph.addEdge("8", "4", new RelationshipEdge("15q"));
        directedGraph.addEdge("8", "11", new RelationshipEdge("16q"));
        directedGraph.addEdge("11", "2", new RelationshipEdge("17q"));
        directedGraph.addEdge("3", "6", new RelationshipEdge("18q"));
        directedGraph.addEdge("6", "10", new RelationshipEdge("19q"));
        directedGraph.addEdge("6", "10", new RelationshipEdge("20q"));
        directedGraph.addEdge("10", "6", new RelationshipEdge("21q"));
        directedGraph.addEdge("10", "12", new RelationshipEdge("22q"));
        directedGraph.addEdge("12", "0", new RelationshipEdge("23q"));

        return directedGraph;
    }
}
