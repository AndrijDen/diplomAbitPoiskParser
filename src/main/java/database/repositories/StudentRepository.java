package database.repositories;

import com.google.gson.Gson;
import database.DBConnector;
import models.Student;
import models.ZnoMark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentRepository {

    private Gson gson = new Gson();
    private static final String selectAll = "SELECT * FROM students;";
    private static final String selectById = "SELECT * FROM students WHERE id=?;";
    private final static String insertInto = "INSERT INTO students(name, searchLink, priority, grade, direction_id, averageSchoolMark, znoMarks) VALUES(?,?,?,?,?,?,?);";

    public List<Student> getAll() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAll);
        return listFrom(ps.executeQuery());
    }

    public Student getById(int id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectById);
        ps.setInt(1, id);
        List<Student> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    public boolean insert(Student student) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(insertInto);
        ps.setString(1, student.getName());
        ps.setString(2, student.getSearchLink());
        ps.setInt(3, student.getPriority());
        ps.setDouble(4, student.getGrade());
        ps.setInt(5, student.getDirection_id());
        ps.setDouble(6, student.getAverageSchoolMark());
        String znoMarksJson = gson.toJson(student.getZnoMarks());
        ps.setString(7, znoMarksJson);
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
        item.setAverageSchoolMark(resultSet.getDouble("averageSchoolMark"));
        item.setZnoMarks(gson.fromJson(resultSet.getString("znoMarks"), ZnoMark[].class));
        return item;
    }
}
