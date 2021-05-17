package graphLogic;

import database.repositories.FullStudentDataRepository;
import database.repositories.StudentStatementRepository;
import models.DataForGraphOperations;
import models.StudentStatement;
import org.jgrapht.Graph;

import java.sql.SQLException;
import java.util.List;

public class FindAllCycles {

    public void findAllCycles() throws SQLException {
        StudentStatementRepository studStatRepository = new StudentStatementRepository();
        List<StudentStatement> studStatList = studStatRepository.getAllOrderedByGrade();
        double lastGrade = 0;
        for (int i = 0; i < studStatList.size(); i++) {
            double grade = studStatList.get(i).getGrade();
            if (grade - lastGrade >= 1) {
//                System.out.println("grade" + grade);
                searchCyclesByGrade(grade);
                lastGrade = grade;
            }
        }
    }

    private void searchCyclesByGrade(double grade) throws SQLException {
        FullStudentDataRepository fullStudData = new FullStudentDataRepository();

        List<DataForGraphOperations> fullStudentDataList = fullStudData.selectAllStudData(grade, 1);
//        System.out.println("+++++++fullStudentDataList" + fullStudentDataList);

        if (fullStudentDataList != null && !fullStudentDataList.isEmpty()) {
            FormStudentGraph studGraph = new FormStudentGraph(fullStudentDataList);
            Graph studentGraph = studGraph.getGraph();

            GetStudentTransitionDataFromGraph getStudTransitData = new GetStudentTransitionDataFromGraph();
            getStudTransitData.getStudentTransitionData(studentGraph);
        }
    }
}
