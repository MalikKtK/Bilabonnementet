package com.example.bilabonnement.repositories;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.DamageReport;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageReportRepository {

    //Opretter en DamageReport

    public DamageReport create(DamageReport damageReport) {
        Connection conn = DatabaseConnectionManager.getConnection();
        String insertSQL = "INSERT INTO bilabonnement.damage_report (id, notes, technicianId, carId)" +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement psts = conn.prepareStatement(insertSQL);
            psts.setInt(1, damageReport.getId());
            psts.setString(2, damageReport.getNotes());
            psts.setInt(3, damageReport.getTechnicianId());
            psts.setInt(4, damageReport.getCarId());
            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getCarByID(damageReport.getId());
    }
    public boolean deleteById(int id) {
        Connection conn = DatabaseConnectionManager.getConnection();
        try {
            // Sletter først alt i DamageReportLine.
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.damageline WHERE damageReportId=?");
            psts.setInt(1, id);
            psts.execute();

            // Og så sletter den alt resten.
            psts = conn.prepareStatement("DELETE FROM bilabonnement.damage_report WHERE id=?");
            psts.setInt(1, id);
            psts.execute();

            DatabaseConnectionManager.closeConnection();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public DamageReport getCarByID(int id) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT id, notes, technicianId, carId FROM bilabonnement.damage_report WHERE id = ?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            psts.setInt(1, id);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DamageReport result = null;
        if (rs != null) {
            result = makeDamageReportFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;

    }

    public List<DamageReport> getAllCars() {
        {
            Connection con = DatabaseConnectionManager.getConnection();

            String selectSQL = "SELECT * FROM bilabonnement.damage_report;";

            ResultSet rs = null;
            try {
                PreparedStatement psts = con.prepareStatement(selectSQL);
                rs = psts.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            List<DamageReport> result = new ArrayList<>();
            if (rs != null) {
                result = makeDamageReportsFromResultSet(rs);
            }

            DatabaseConnectionManager.closeConnection();
            return result;
        }
    }


    private DamageReport makeDamageReportFromResultSet(ResultSet rs) {
        List<DamageReport> damageReports = makeDamageReportsFromResultSet(rs);
        if (damageReports.size() > 0) {
            return damageReports.get(0);
        }
        return null;
    }


    private List<DamageReport> makeDamageReportsFromResultSet(ResultSet rs) {
        ArrayList<DamageReport> damageReports = new ArrayList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String notes = rs.getString("notes");
                int technicianId = rs.getInt("technicianId");
                int carId = rs.getInt("carId");

                damageReports.add(new DamageReport(id, notes, technicianId, carId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return damageReports;
    }

    public DamageReport update(DamageReport damageReport) {
        Connection con = DatabaseConnectionManager.getConnection();

        String insertSQL = "UPDATE bilabonnement.damage_report " +
                "SET notes = ?,  technicianId = ?, carId = ? " +
                "WHERE (id = ?);";

        try {
            PreparedStatement psts = con.prepareStatement(insertSQL);
            psts.setString(1, damageReport.getNotes());
            psts.setInt(2, damageReport.getTechnicianId());
            psts.setInt(3, damageReport.getCarId());

            psts.setInt(4, damageReport.getId());

            System.out.println(psts);

            psts.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
