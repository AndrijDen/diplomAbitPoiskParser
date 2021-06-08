package database.repositories;

import database.DBConnector;
import models.DataForGraphOperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FullStudentDataRepository {
    private static final String selectAllStudDataByGrade = "SELECT studStat.id AS studStatId, studStat.grade, studStat.priority, studName, studId, toUniversity, toDirection, toDirectionId, toFaculty, fromUniversity, fromDirection, fromDirectionId, fromFaculty FROM abitpoisk.studentStatements studStat \n" +
            "INNER JOIN \n" +
            "(SELECT unIn.shortName AS toUniversity, dirIn.directionId, dirIn.name AS toDirection, dirIn.facultyShortName AS toFaculty, dirIn.id AS toDirectionId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "AS dirJoinUn\n" +
            "ON studStat.direction_id = toDirectionId\n" +
            "INNER JOIN\n" +
            "(SELECT stud.name AS studName, stud.id AS studId, dirJoinUn.shortName AS fromUniversity, dirJoinUn.name AS fromDirection, dirJoinUn.facultyShortName AS fromFaculty, stud.direction_id AS fromDirectionId FROM abitpoisk.students AS stud INNER JOIN \n" +
            "\t(SELECT unIn.shortName, dirIn.directionId, dirIn.name, dirIn.facultyShortName, dirIn.id AS directionSpecId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "\tAS dirJoinUn\n" +
            " ON stud.direction_id = directionSpecId)\n" +
            "AS studJoinDirJoinUn\n" +
            "ON studId = studStat.students_id\n" +
            "WHERE studStat.grade BETWEEN ? AND ?";

    private static final String selectAllStudData = "SELECT studStat.id AS studStatId, studStat.grade, studStat.priority, studName, studId, toUniversity, toDirection, toDirectionId, toFaculty, fromUniversity, fromDirection, fromDirectionId, fromFaculty FROM abitpoisk.studentStatements studStat \n" +
            "INNER JOIN \n" +
            "(SELECT unIn.shortName AS toUniversity, dirIn.directionId, dirIn.name AS toDirection, dirIn.facultyShortName AS toFaculty, dirIn.id AS toDirectionId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "AS dirJoinUn\n" +
            "ON studStat.direction_id = toDirectionId\n" +
            "INNER JOIN\n" +
            "(SELECT stud.name AS studName, stud.id AS studId, dirJoinUn.shortName AS fromUniversity, dirJoinUn.name AS fromDirection, dirJoinUn.facultyShortName AS fromFaculty, stud.direction_id AS fromDirectionId FROM abitpoisk.students AS stud INNER JOIN \n" +
            "\t(SELECT unIn.shortName, dirIn.directionId, dirIn.name, dirIn.facultyShortName, dirIn.id AS directionSpecId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "\tAS dirJoinUn\n" +
            " ON stud.direction_id = directionSpecId)\n" +
            "AS studJoinDirJoinUn\n" +
            "ON studId = studStat.students_id";


    public List<DataForGraphOperations> selectAllStudData(double grade, double gap) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAllStudDataByGrade);
        if (gap < 1) {
            ps.setDouble(1, grade - 0.001);
            ps.setDouble(2, grade + 0.001);
        } else {
            ps.setDouble(1, grade - 1);
            ps.setDouble(2, grade + gap);
        }
        List<DataForGraphOperations> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    public List<DataForGraphOperations> selectAllStud() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAllStudData);
        List<DataForGraphOperations> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    private List<DataForGraphOperations> listFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(getItemFrom(resultSet));
        }
        return list;
    }

    private DataForGraphOperations getItemFrom(ResultSet resultSet) throws SQLException {
        DataForGraphOperations item = new DataForGraphOperations();
        item.setStudStatId(resultSet.getInt("studStatId"));
        item.setGrade(resultSet.getDouble("grade"));
        item.setPriority(resultSet.getInt("priority"));
        item.setStudName(resultSet.getString("studName"));
        item.setStudId(resultSet.getInt("studId"));
        item.setToUniversity(resultSet.getString("toUniversity"));
        item.setToDirection(resultSet.getString("toDirection"));
        item.setToDirectionId(resultSet.getInt("toDirectionId"));
        item.setToFaculty(resultSet.getString("toFaculty"));
        item.setFromUniversity(resultSet.getString("fromUniversity"));
        item.setFromDirection(resultSet.getString("fromDirection"));
        item.setFromFaculty(resultSet.getString("fromFaculty"));
        item.setFromDirectionId(resultSet.getInt("fromDirectionId"));
        return item;
    }
}
