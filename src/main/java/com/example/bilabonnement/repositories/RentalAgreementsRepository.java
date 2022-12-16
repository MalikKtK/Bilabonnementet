package com.example.bilabonnement.repositories;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.RentalAgreement;
import com.example.bilabonnement.models.RentalType;
import com.example.bilabonnement.repositories.interfaces.IRentalAgreementRepository;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreementsRepository implements IRentalAgreementRepository {

    private static final RentalAgreementsRepository instance = new RentalAgreementsRepository();

    private RentalAgreementsRepository() {
    }

    public static RentalAgreementsRepository getInstance() {
        return instance;
    }

    //Opretter en rentalAgreement.
    @Override
    public RentalAgreement create(RentalAgreement rentalAgreement) {
        Connection conn = DatabaseConnectionManager.getConnection();
        String insertSQL = "INSERT INTO bilabonnement.rental_agreements (carId, startDate, endDate, price, type) " +
                "VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement psts = conn.prepareStatement(insertSQL);
            psts.setInt(1, rentalAgreement.getCarId());
            psts.setString(2, rentalAgreement.getStartDate().toString());
            psts.setString(3, rentalAgreement.getEndDate().toString());
            psts.setInt(4, rentalAgreement.getPrice());
            psts.setString(5, RentalType.values()[rentalAgreement.getTypeId()].toString());
            psts.execute();

            psts = conn.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet rs = psts.executeQuery();
            rs.next();

            rentalAgreement.setId(rs.getInt("LAST_INSERT_ID()"));
            return rentalAgreement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean deleteById(int id) {
        Connection conn = DatabaseConnectionManager.getConnection();
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.rental_agreements WHERE id=?");
            psts.setInt(1, id);
            psts.execute();
            DatabaseConnectionManager.closeConnection();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    @Override
    public RentalAgreement getCarByID(int id) {
        Connection conn = DatabaseConnectionManager.getConnection();
        String selectSQL = "SELECT id, carId, startDate, endDate, price, type FROM bilabonnement.rental_agreements WHERE id=?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = conn.prepareStatement(selectSQL);
            psts.setInt(1, id);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RentalAgreement result = null;
        if (rs != null) {
            result = makeRentalAgreementFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    @Override
    public List<RentalAgreement> getAllCars() {
        Connection conn = DatabaseConnectionManager.getConnection();
        String selectSQL = "SELECT id, carId, startDate, endDate, price, type FROM bilabonnement.rental_agreements;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = conn.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<RentalAgreement> result = new ArrayList<>();
        if (rs != null) {
            result = makeRentalAgreementsFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    //En funktion som får fat i alle de lejeaftaler, der er gået overtid med dagens dato.
    @Override
    public List<RentalAgreement> getAllRentalPastEndDate() {
        Connection conn = DatabaseConnectionManager.getConnection();
        String selectSQL = "SELECT id, carId, startDate, endDate, price, type " +
                "FROM bilabonnement.rental_agreements WHERE `endDate` < date(now());";

        ResultSet rs = null;
        try {
            PreparedStatement psts = conn.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<RentalAgreement> result = new ArrayList<>();
        if (rs != null) {
            result = makeRentalAgreementsFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    private List<RentalAgreement> makeRentalAgreementsFromResultSet(ResultSet rs) {
        ArrayList<RentalAgreement> rentalAgreements = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int carId = rs.getInt("carId");
                int price = rs.getInt("price");
                LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
                LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
                RentalType type = RentalType.valueOf(rs.getString("type"));
                rentalAgreements.add(new RentalAgreement(id, carId, price, startDate, endDate, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalAgreements;
    }

    private RentalAgreement makeRentalAgreementFromResultSet(ResultSet rs) {
        return makeRentalAgreementsFromResultSet(rs).get(0);
    }
    @Override
    public RentalAgreement update(RentalAgreement rentalAgreement) {
        Connection con = DatabaseConnectionManager.getConnection();
        String updateSQL = "UPDATE bilabonnement.rental_agreements " +
                "SET carId = ?, startDate = ?, endDate = ?, price = ?, type = ? " +
                "WHERE (id = ?);";


        try {
            PreparedStatement psts = con.prepareStatement(updateSQL);
            psts.setInt(1, rentalAgreement.getCarId());
            psts.setString(2, rentalAgreement.getStartDate().toString());
            psts.setString(3, rentalAgreement.getEndDate().toString());
            psts.setInt(4, rentalAgreement.getPrice());
            psts.setString(5, RentalType.values()[rentalAgreement.getTypeId()].toString());
            psts.setInt(6, rentalAgreement.getId());

            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getCarByID(rentalAgreement.getId());
    }

}
