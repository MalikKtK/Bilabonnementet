package com.example.bilabonnement.repositories;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.Car;
import com.example.bilabonnement.models.CarStatus;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    //Opretter en bil
    public Car create(Car car) {
        Connection conn = DatabaseConnectionManager.getConnection();
        String insertSQL = "INSERT INTO bilabonnement.cars (id, carNumber, status, make, model, carPrice, registrationFee, co2Emission, kilometersDriven, damages, colour, fuelType, locationId)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?);";

        try {
            PreparedStatement psts = conn.prepareStatement(insertSQL);
            psts.setInt(1, car.getId());
            psts.setString(2, car.getCarNumber());
            psts.setString(3, car.getStatus().toString());
            psts.setString(4, car.getMake());
            psts.setString(5, car.getModel());
            psts.setInt(6, car.getCarPrice());
            psts.setInt(7, car.getRegistrationFee());
            psts.setInt(8, car.getCo2Emission());
            psts.setInt(9, car.getKilometersDriven());
            psts.setString(10, car.getDamage());
            psts.setString(11, car.getColour());
            psts.setString(12, car.getFuelType());
            psts.setInt(13, car.getLocationId());
            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getCarByCarnumber(car.getCarNumber());
    }


    public boolean deleteById(int id) {
        Connection conn = DatabaseConnectionManager.getConnection();
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.cars WHERE id=?");
            psts.setInt(1, id);
            psts.execute();
            DatabaseConnectionManager.closeConnection();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public Car getCarByID(int id) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.cars " +
                "WHERE id = ?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            psts.setInt(1, id);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Car result = null;
        if (rs != null) {
            result = makeCarFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    public List<Car> getAllCars() {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.cars;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Car> result = new ArrayList<>();
        if (rs != null) {
            result = makeCarsFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }


    //Finder en bil af bilnummer. Bilnummer oprettede vi, så vi kunne finde biler ved et nummer i stedet for id.
    public Car getCarByCarnumber(String carNumber) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.cars " +
                "WHERE carNumber = '" + carNumber + "';";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Car result = null;
        if (rs != null) {
            result = makeCarFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    public List<Car> getCarByStatus(CarStatus status) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.cars " +
                "WHERE status = ?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            psts.setString(1, status.toString());
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Car> result = new ArrayList<>();
        if (rs != null) {
            result = makeCarsFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    public void update(Car car) {
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = con.prepareStatement("UPDATE bilabonnement.cars SET carNumber = ?,  status = ?, make = ?, model = ?, carPrice = ?, registrationFee = ?, co2Emission = ?, kilometersDriven = ?, damages = ?, colour = ?, fuelType = ?, locationId = ? WHERE (id = ?);");
            psts.setString(1, car.getCarNumber());
            psts.setString(2, car.getStatus().toString());
            psts.setString(3, car.getMake());
            psts.setString(4, car.getModel());
            psts.setInt(5, car.getCarPrice());
            psts.setInt(6, car.getRegistrationFee());
            psts.setInt(7, car.getCo2Emission());
            psts.setInt(8, car.getKilometersDriven());
            psts.setString(9, car.getDamage());
            psts.setString(10, car.getColour());
            psts.setString(11, car.getFuelType());
            psts.setInt(12, car.getLocationId());
            psts.setInt(13, car.getId());

            psts.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Car makeCarFromResultSet(ResultSet rs) {
        List<Car> cars = makeCarsFromResultSet(rs);
        if (cars.size() > 0) {
            return cars.get(0);
        }
        return null;
    }


    private List<Car> makeCarsFromResultSet(ResultSet rs) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            while (rs.next()) {
                int carId = rs.getInt("id");
                String carNumber = rs.getString("carNumber");
                CarStatus status = CarStatus.valueOf(rs.getString("status"));
                String make = rs.getString("make");
                String model = rs.getString("model");
                int carPrice = rs.getInt("carprice");
                int registrationFee = rs.getInt("registrationfee");
                int co2Emission = rs.getInt("co2emission");
                int kilometersDriven = rs.getInt("kilometersdriven");
                String damage = rs.getString("damages");
                String colour = rs.getString("colour");
                String fuelType = rs.getString("fueltype");
                int locationId = rs.getInt("locationId");
                cars.add(new Car(carId, carNumber, status, make, model,
                        carPrice, registrationFee, co2Emission, kilometersDriven,
                        damage, colour, fuelType, locationId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
