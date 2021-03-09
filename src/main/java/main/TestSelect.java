package main;

import database.DBConnector;
import models.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestSelect {
    private List<University> listAlbumsFrom(ResultSet resultSet) throws SQLException {
        List list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(albumFrom(resultSet));
        }

        return list;
    }

    private University albumFrom(ResultSet resultSet) throws SQLException {
        University album = new University();
        album.setId(resultSet.getInt("id"));
        album.setName(resultSet.getString("name"));
        album.setShortName(resultSet.getString("shortName"));
        return album;
    }


    public List<University> all() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        System.out.print( "C" + c);
        PreparedStatement ps = c.prepareStatement("SELECT * FROM university;");
        ResultSet result = ps.executeQuery();
        return listAlbumsFrom(result);
    }
}
