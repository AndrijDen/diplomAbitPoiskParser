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

        studentsData.forEach((DataForGraphOperations data) -> {
            String vertexFrom = data.getFromUniversity() + " | " + data.getFromFaculty() + " | " + data.getFromDirection() + " | " + data.getFromDirectionId();
            String vertexTo = data.getToUniversity() + " | " + data.getToFaculty() + " | " + data.getToDirection() + " | " + data.getToDirectionId();
            String studentName = data.getStudName();

            if (!directedGraph.containsVertex(vertexFrom)) {
                directedGraph.addVertex(vertexFrom);
            }
            if (!directedGraph.containsVertex(vertexTo)) {
                directedGraph.addVertex(vertexTo);
            }
            if (vertexFrom != vertexTo) {
                directedGraph.addEdge(vertexFrom, vertexTo, new RelationshipEdge(studentName));
            }
        });

//        System.out.println("+++++++++++++directedGraph" + directedGraph);

        return directedGraph;
    }
}
