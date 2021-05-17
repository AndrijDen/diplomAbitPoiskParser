package database.repositories;

import database.DBConnector;
import models.Student;
import models.StudentStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentStatementRepository {

    private static final String selectAll = "SELECT * FROM studentStatements;";
    private static final String selectAllOrderBy = "SELECT * FROM studentStatements ORDER BY grade;";
    private static final String selectById = "SELECT * FROM studentStatements WHERE id=?;";
    private static final String selectByGrade = "SELECT * FROM studentStatements WHERE grade=?;";
    private static final String selectByUniversityShortName = "SELECT * FROM studentStatements WHERE universityShortName=?;";
    private final static String insertInto = "INSERT INTO studentStatements(grade, priority, universityShortName, directionDataId, students_id, direction_id, facultyShortName) VALUES(?,?,?,?,?,?,?);";
    private static final String updateDirectionIdByIdAndPriority = "UPDATE studentStatements SET direction_id=? WHERE id=? AND priority=?;";

    public List<StudentStatement> getAll() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAll);
        return listFrom(ps.executeQuery());
    }

    public List<StudentStatement> getAllOrderedByGrade() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAllOrderBy);
        return listFrom(ps.executeQuery());
    }

    public List<StudentStatement> getByUniversityShortName(String name) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectByUniversityShortName);
        ps.setString(1, name);
        List<StudentStatement> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    public StudentStatement getById(int id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectById);
        ps.setInt(1, id);
        List<StudentStatement> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    public List<StudentStatement> getByGrade(double grade) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectByGrade);
        ps.setDouble(1, grade);
        List<StudentStatement> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    public boolean insert(StudentStatement student) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(insertInto);
        ps.setDouble(1, student.getGrade());
        ps.setInt(2, student.getPriority());
        ps.setString(3, student.getUniversityShortName());
        ps.setInt(4, student.getDirectionDataId());
        ps.setInt(5, student.getStudents_id());
        ps.setInt(6, student.getDirection_id());
        ps.setString(7, null);
        return ps.executeUpdate() == 1;
    }

    public boolean updateDirectionId(int id, int directionId, int priority) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(updateDirectionIdByIdAndPriority);
        ps.setInt(1, directionId);
        ps.setInt(2, id);
        ps.setInt(3, priority);
        return ps.executeUpdate() == 1;
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
        item.setDirection_id(resultSet.getInt("direction_id"));
        item.setFacultyShortName(resultSet.getString("facultyShortName"));
        return item;
    }
}
