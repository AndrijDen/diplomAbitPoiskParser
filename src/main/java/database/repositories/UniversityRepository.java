package database.repositories;

import database.DBConnector;
import models.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UniversityRepository {

    private static final String selectAll = "SELECT * FROM university;";
    private static final String selectById = "SELECT * FROM university WHERE id=?;";

    public List<University> getAll() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAll);
        return listFrom(ps.executeQuery());
    }

    public List<University> getById(int id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectById);
        ps.setInt(1, id);
        List<University> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result;
    }

    private List<University> listFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(getItemFrom(resultSet));
        }
        return list;
    }

    private University getItemFrom(ResultSet resultSet) throws SQLException {
        University item = new University();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setShortName(resultSet.getString("shortName"));
        return item;
    }
}
