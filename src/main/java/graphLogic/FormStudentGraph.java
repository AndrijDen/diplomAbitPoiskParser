package graphLogic;

import models.DataForGraphOperations;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import java.util.List;

public class FormStudentGraph {
    private DirectedMultigraph<String, RelationshipEdge> directedGraph =
            new DirectedMultigraph<String, RelationshipEdge>(RelationshipEdge.class);
    private List<DataForGraphOperations> studentsData;

    public FormStudentGraph(List<DataForGraphOperations> data) {
        this.studentsData = data;
    }


    public Graph getGraph() {

        directedGraph.addVertex("0");
        directedGraph.addEdge("0", "1", new RelationshipEdge("0q"));

        return directedGraph;
    }
}
