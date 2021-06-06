package graphLogic;

import models.StudentTransitionData;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.cycle.SzwarcfiterLauerSimpleCycles;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.graph.DirectedMultigraph;

import java.util.*;

public class GetStudentTransitionDataFromGraph {
    private ArrayList<String> usedNames = new ArrayList();
    private ArrayList<List<String>> uniqueCycles = new ArrayList();
    private ArrayList<List<String>> cyclesWithEvenEdges = new ArrayList();
    private boolean displayGraphs = false;

    public void getStudentTransitionData(Graph graph, boolean dispGraphs) {
        displayGraphs = dispGraphs;
        findStrongConnectedGraphs(graph);
    }

    private void findStrongConnectedGraphs(Graph graph) {
//        System.out.println("graph" + graph);
        StrongConnectivityAlgorithm strongConGraphs = new  KosarajuStrongConnectivityInspector(graph);
//        System.out.println("GetStrConComp" + strongConGraphs.getStronglyConnectedComponents());
        findAllCycles(strongConGraphs.getStronglyConnectedComponents());
    }

    private void findAllCycles(List<Graph> strongConnectedGraphs) {

        System.out.println(strongConnectedGraphs.size() + " strongConnectedGraphs" + strongConnectedGraphs);

        for (int i = 0; i < strongConnectedGraphs.size(); i++) {
            if (strongConnectedGraphs.get(i).vertexSet().size() > 1) {
                findCycles(strongConnectedGraphs.get(i));
                usedNames.clear();
            }
        }
    }

    private void findCycles(Graph graphWithCycles) {
        SzwarcfiterLauerSimpleCycles graph = new SzwarcfiterLauerSimpleCycles(graphWithCycles);
        List cycles = graph.findSimpleCycles();

        findOptimalResFromCycles(cycles, graphWithCycles);
        addCycleIfEvenEdges(cycles);
        System.out.println("--------------------------------------------");
        System.out.println("cycles" + cycles);

        if (displayGraphs) {
            DisplayAllCycles displayAllCycles = new DisplayAllCycles();
            displayAllCycles.displayFoundCycles(getStudentsTransitionsData(cycles, graphWithCycles));
        }
    }

    private List findOptimalResFromCycles(List<List> cycles, Graph graph) {

        sortList(cycles);
        ArrayList<Integer> elementsToRemove = new ArrayList<Integer>();

        for (int i = 0; i < cycles.size(); i++) {
            List element = cycles.get(i);
            if (isUniqueCycle(element, graph)) {
                if (i != cycles.size()) {
                    for (int j = i + 1; j < cycles.size(); j++) {
                        if (isSameCycles(element, (List) cycles.get(j))) {
                            elementsToRemove.add(j);
                        }
                    }
                }
            } else {
                elementsToRemove.add(i);
                if (i == cycles.size() - 2) {
                    i = i - 1;
                }
            }
            if (elementsToRemove.size() > 0) {
                Collections.reverse(elementsToRemove);
                for (int k = 0; k < elementsToRemove.size(); k++) {
                    int q = elementsToRemove.get(k);
                    cycles.remove(q);
                }
                elementsToRemove.clear();
            }
        }
//        System.out.println("findOptimalResFromCycles" + cycles);
        return cycles;
    }

    private List getStudentsTransitionsData(List<List<String>> cycles, Graph graph) {
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
                    fromUniversity = cycles.get(i).get(j).toString();
                    toUniversity = cycles.get(i).get(0).toString();
                }

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
                if (cycle.contains(uniqueCycles.get(i).get(0)) && cycle.contains(uniqueCycles.get(i).get(1))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSameCycles(List Arr1, List Arr2)
    {
        int arr1Length = Arr1.size();
        int arr2Length = Arr2.size();
        int i = 0, j = 0;
        if (arr1Length == arr2Length) {
            while (i < arr1Length && j < arr2Length) {
                if (Arr1.get(i) == Arr2.get(j)) {
                    i++;
                    j++;
                    if (i == arr1Length || j == arr2Length)
                        return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private void sortList(List<List> cycles) {
        cycles.sort((o1, o2) -> {
            if (o1.size() > o2.size()) {
                return 1;
            } else if (o1.size() < o2.size()) {
                return -1;
            }
            return 0;
        });
    }

    private List findOptimalRes(List cycles) {
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
                    int q = elementsToRemove.get(k);
                    cycles.remove(q);
                }
                elementsToRemove.clear();
            }
        }
        System.out.println("cycles test" + cycles);
        return cycles;
    }

    private boolean isSubArray(List Arr1, List Arr2) {
        int arr1Length = Arr1.size();
        int arr2Length = Arr2.size();
        int i = 0, j = 0;
        while (i < arr1Length && j < arr2Length) {
            if (Arr1.get(i) == Arr2.get(j)) {
                i++;
                j++;
                if (i == arr1Length || j == arr2Length)
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private Graph testDataFindingStrongConnectivitynoLable() {
        DirectedMultigraph<String, RelationshipEdge> directedGraph =
                new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);

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

        System.out.println("directedGraph" + directedGraph);
        return directedGraph;
    }

    private Graph testDataCyclesgr5ML() {
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
