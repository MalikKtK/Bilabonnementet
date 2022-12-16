package com.example.bilabonnement.repositories;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.Location;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {


    public List<Location> getAllCars() {
        Connection con = DatabaseConnectionManager.getConnection();

        PreparedStatement psts = null;
        try {
            psts = con.prepareStatement("SELECT * FROM bilabonnement.Locations;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Location> locations = new ArrayList<>();
        try {
            assert psts != null;
            ResultSet rs = psts.executeQuery();
            while (rs.next()) {
                locations.add(new Location(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectionManager.closeConnection();
        return locations;
    }

}
