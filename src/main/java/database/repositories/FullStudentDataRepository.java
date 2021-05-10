package database.repositories;

import database.DBConnector;
import models.Direction;
import models.StudentStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FullStudentDataRepository {
    private static final String selectAllStudDataByGrade = "SELECT studStat.id AS studStatId, studStat.grade, studStat.priority, studName, studId,toUn, toDir, fromUn, fromDir FROM abitpoisk.studentStatements studStat \n" +
            "INNER JOIN \n" +
            "(SELECT unIn.shortName AS toUn, dirIn.directionId, dirIn.name AS toDir, dirIn.id AS directionSpecId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "AS dirJoinUn\n" +
            "ON toUn = studStat.universityShortName\n" +
            "INNER JOIN\n" +
            "(SELECT stud.name AS studName, stud.id AS studId, dirJoinUn.shortName AS fromUn, dirJoinUn.name AS fromDir FROM abitpoisk.students AS stud INNER JOIN \n" +
            "\t(SELECT unIn.shortName, dirIn.directionId, dirIn.name, dirIn.id AS directionSpecId FROM abitpoisk.university unIn INNER JOIN abitpoisk.direction dirIn ON unIn.id = dirIn.university_id) \n" +
            "\tAS dirJoinUn\n" +
            " ON stud.direction_id = dirJoinUn.directionSpecId)\n" +
            "AS studJoinDirJoinUn\n" +
            "ON studId = studStat.students_id\n" +
            "WHERE studStat.grade = 192\n" +
            "GROUP BY studStat.id";


    public List<StudentStatement> selectAllStudData(double grade) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAllStudDataByGrade);
        ps.setDouble(1, grade);
        List<StudentStatement> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    private List<StudentStatement> listFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(getItemFrom(resultSet));
        }
        return list;
    }

    private StudentStatement getItemFrom(ResultSet resultSet) throws SQLException {
        StudentStatement item = new StudentStatement();
        item.setId(resultSet.getInt("id"));
        item.setGrade(resultSet.getDouble("grade"));
        item.setPriority(resultSet.getInt("priority"));
        item.setUniversityShortName(resultSet.getString("universityShortName"));
        item.setDirectionDataId(resultSet.getInt("directionDataId"));
        item.setStudents_id(resultSet.getInt("students_id"));
        return item;
    }
}
