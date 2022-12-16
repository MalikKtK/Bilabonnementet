package com.example.bilabonnement.repositories;

//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.DamageReportLine;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageReportLineRepository {

    //Opretter en damagereportline.

    public DamageReportLine create(DamageReportLine damageReportLine) {
        Connection connection = DatabaseConnectionManager.getConnection();
        String maxSQL = "SELECT max(lineNumber) AS aValue FROM bilabonnement.damageline WHERE damageReportId = ?";

        String insertSQL = "INSERT INTO bilabonnement.damageline (linenumber, damageReportId, damageNotes, price)" +
                "VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement psts = connection.prepareStatement(maxSQL);
            psts.setInt(1, damageReportLine.getDamageReportId());
            ResultSet rs = psts.executeQuery();
            rs.next();
            damageReportLine.setLineNumber(rs.getInt("aValue") + 1);

            psts = connection.prepareStatement(insertSQL);
            psts.setInt(1, damageReportLine.getLineNumber());
            psts.setInt(2, damageReportLine.getDamageReportId());
            psts.setString(3, damageReportLine.getDamageNotes());
            psts.setInt(4, damageReportLine.getPrice());

            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getDamageReportLineByLineNumberDamageReportID(damageReportLine.getLineNumber(), damageReportLine.getDamageReportId());
    }

    public boolean deleteById(int lineNumber, int damageReportId) {
        Connection connection = DatabaseConnectionManager.getConnection();
        try {
            PreparedStatement psts = connection.prepareStatement("DELETE FROM bilabonnement.damageline where lineNumber=? AND damageReportId=?");
            psts.setInt(1, lineNumber);
            psts.setInt(2, damageReportId);
            psts.execute();

            DatabaseConnectionManager.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }
    public List<DamageReportLine> getDamageReportByID(int damageReportId) {
        Connection connection = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.damageline WHERE damageReportId = ?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = connection.prepareStatement(selectSQL);
            psts.setInt(1, damageReportId);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<DamageReportLine> result = new ArrayList<>();
        if (rs != null) {
            result = makeDamageReportLinesFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();

        return result;
    }

    public List<DamageReportLine> getAllCars() {
        {
            Connection connection = DatabaseConnectionManager.getConnection();

            String selectSQL = "SELECT * FROM bilabonnement.damageline;";

            ResultSet rs = null;
            try {
                PreparedStatement psts = connection.prepareStatement(selectSQL);
                rs = psts.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            List<DamageReportLine> result = new ArrayList<>();
            if (rs != null) {
                result = makeDamageReportLinesFromResultSet(rs);
            }

            DatabaseConnectionManager.closeConnection();
            return result;
        }
    }

    public DamageReportLine getDamageReportLineByLineNumberDamageReportID(int lineNumber, int damageReportId) {
        Connection connection = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.damageline " +
                "WHERE lineNumber = ? AND damageReportId = ?;";
        ResultSet rs = null;
        try {
            PreparedStatement psts = connection.prepareStatement(selectSQL);
            psts.setInt(1, lineNumber);
            psts.setInt(2, damageReportId);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DamageReportLine result = null;
        if (rs != null) {
            result = makeDamageReportLineFromResultSet(rs);
        }
        DatabaseConnectionManager.closeConnection();
        return result;
    }


    private DamageReportLine makeDamageReportLineFromResultSet(ResultSet rs) {

        List<DamageReportLine> damageReportLines = makeDamageReportLinesFromResultSet(rs);

        if (damageReportLines.size() > 0) {
            return damageReportLines.get(0);
        }

        return null;
    }


    private List<DamageReportLine> makeDamageReportLinesFromResultSet(ResultSet rs) {
        ArrayList<DamageReportLine> damageReportLines = new ArrayList<>();

        try {
            while (rs.next()) {
                int lineNumber = rs.getInt("linenumber");
                int damageReportId = rs.getInt("damageReportId");
                String damageNotes = rs.getString("damageNotes");
                int price = rs.getInt("price");
                damageReportLines.add(new DamageReportLine(damageReportId, lineNumber, damageNotes, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return damageReportLines;
    }
    public void update(DamageReportLine damageReportLine) {
        try {
            Connection con = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = con.prepareStatement("UPDATE bilabonnement.damageline SET damageNotes = ?,  price = ? WHERE lineNumber = ? AND damageReportId = ?;");
            psts.setString(1, damageReportLine.getDamageNotes());
            psts.setInt(2, damageReportLine.getPrice());
            psts.setInt(3, damageReportLine.getLineNumber());
            psts.setInt(4, damageReportLine.getDamageReportId());

            psts.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
