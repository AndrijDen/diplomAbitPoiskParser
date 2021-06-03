package graphLogic;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import models.StudentTransitionData;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import java.util.List;

import javax.swing.*;
import java.awt.*;

public class DisplayAllCycles extends
        JApplet {
    private static final long serialVersionUID = 2202072534703043194L;

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

    public void displayFoundCycles(List<List<StudentTransitionData>> studTransDataList) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < studTransDataList.size(); i++) {
            DisplayAllCycles applet = new DisplayAllCycles();
            applet.init(studTransDataList.get(i));
            panel.add(applet);
        }

        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setPreferredSize(new Dimension(1000, 600));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame();
        frame.getContentPane().add(jScrollPane);
        frame.setTitle("found cycles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void formGraph(Graph g, List<StudentTransitionData> studTransDataList) {
        String toUniversity = "";
        for (int i = 0; i < studTransDataList.size(); i++) {
            String v1 = studTransDataList.get(i).getFromUniversity();
            String v2 = studTransDataList.get(i).getToUniversity();
            g.addVertex(v1);

            if (toUniversity != v2) {
                toUniversity = v2;
                g.addVertex(v2);
            }
            String name = studTransDataList.get(i).getName();
            g.addEdge(v1, v2,  new RelationshipEdge(name));
        }
    }

    public void init(List<StudentTransitionData> studTransDataList)
    {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g =
                new DefaultListenableGraph<>(new DirectedMultigraph<>(RelationshipEdge.class));

        jgxAdapter = new JGraphXAdapter<>(g);

        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        getContentPane().add(component);

        formGraph(g, studTransDataList);


        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
//        int radius = 100;
//        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
//        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
//        layout.setRadius(radius);
//        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}
