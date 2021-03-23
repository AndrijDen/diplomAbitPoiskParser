package database.repositories;

import database.DBConnector;
import models.Direction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectionRepository {

    private static final String selectAll = "SELECT * FROM direction;";
    private static final String selectById = "SELECT * FROM direction WHERE id=?;";

    public List<Direction> getAll() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectAll);
        return listFrom(ps.executeQuery());
    }

    public Direction getById(int id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(selectById);
        ps.setInt(1, id);
        List<Direction> result = listFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    private List<Direction> listFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(getItemFrom(resultSet));
        }
        return list;
    }

    private Direction getItemFrom(ResultSet resultSet) throws SQLException {
        Direction item = new Direction();
        item.setId(resultSet.getInt("id"));
        item.setName(resultSet.getString("name"));
        item.setDirectionId(resultSet.getInt("directionId"));
        item.setUniversity_id(resultSet.getInt("university_id"));
        return item;
    }
}
