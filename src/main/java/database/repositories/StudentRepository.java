package database.repositories;

import database.DBConnector;
import models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static final String selectAll = "SELECT * FROM students;";
    private static final String selectById = "SELECT * FROM students WHERE id=?;";
    private final static String insertInto = "INSERT INTO students(name, searchLink, priority, grade, direction_id) VALUES(?,?,?,?,?);";

    public List<Student> getAll() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAll);
        return listFrom(ps.executeQuery());
    }

    public List<Student> getById(int id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectById);
        ps.setInt(1, id);
        List<Student> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    public boolean insert(Student student) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(insertInto);
        ps.setString(1, student.getName());
        ps.setString(2, student.getSearchLink());
        ps.setInt(3, student.getPriority());
        ps.setDouble(4, student.getGrade());
        ps.setInt(5, student.getDirection_id());
//        System.out.print("qweqweqwewq" + student.getAverageSchoolMark());
//        ps.setDouble(6, student.getAverageSchoolMark());
//        ps.setString(7, student.getZnoMarks().toString());
        return ps.executeUpdate() == 1;
    }

    private List<Student> listFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(getItemFrom(resultSet));
        }
        return list;
    }

    private Student getItemFrom(ResultSet resultSet) throws SQLException {
        Student item = new Student();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setSearchLink(resultSet.getString("searchLink"));
        item.setPriority(resultSet.getInt("priority"));
        item.setGrade(resultSet.getInt("grade"));
        item.setDirection_id(resultSet.getInt("direction_id"));
        return item;
    }
}
