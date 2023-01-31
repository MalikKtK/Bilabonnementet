package com.example.bilabonnement.repositories;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.User;
import com.example.bilabonnement.models.UserRole;
import com.example.bilabonnement.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    //Opretter en user. Bruger toString på role, da det en enum vi har at gøre med.
    public User create(User user) {
        Connection con = DatabaseConnectionManager.getConnection();

        String insertSQL = "INSERT INTO bilabonnement.users(name, password, role, locationId) " +
                "VALUES (?, ?, ?, ?);";

        try {
            PreparedStatement psts = con.prepareStatement(insertSQL);
            psts.setString(1, user.getUsername());
            psts.setString(2, user.getPassword());
            psts.setString(3, user.getRole().toString());
            psts.setInt(4, user.getLocationId());
            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getUserByUserName(user.getUsername());
    }

    public User create (User user){

    }

    public boolean deleteById(int id) {
        Connection conn = DatabaseConnectionManager.getConnection();
        try {
            PreparedStatement psts = conn.prepareStatement("DELETE FROM bilabonnement.Users WHERE id=?");
            psts.setInt(1, id);
            psts.execute();
            DatabaseConnectionManager.closeConnection();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public User getCarByID(int id) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.Users WHERE id = ?;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            psts.setInt(1, id);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User result = null;
        if (rs != null) {
            result = makeUserFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    public User getUserByUserName(String username) {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.Users " +
                "WHERE name = '" + username + "';";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User result = null;
        if (rs != null) {
            result = makeUserFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }

    public List<User> getAllCars() {
        Connection con = DatabaseConnectionManager.getConnection();

        String selectSQL = "SELECT * FROM bilabonnement.Users;";

        ResultSet rs = null;
        try {
            PreparedStatement psts = con.prepareStatement(selectSQL);
            rs = psts.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<User> result = new ArrayList<>();
        if (rs != null) {
            result = makeUsersFromResultSet(rs);
        }

        DatabaseConnectionManager.closeConnection();
        return result;
    }


//makeUseFromResultSet bruger vi i de fleste funktioner. Vi laver en liste med user, og derefter hvis den er større en 0, oprettes der en user.

    private User makeUserFromResultSet(ResultSet rs) {
        List<User> users = makeUsersFromResultSet(rs);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    //Og her bliver den kørt i en while loop hvor den læser de input vi indtaster og sendt til database.
    private List<User> makeUsersFromResultSet(ResultSet rs) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                int userId = rs.getInt("id");
                String userName = rs.getString("name");
                String userPassword = rs.getString("password");
                UserRole userRole = UserRole.valueOf(rs.getString("role"));
                int locationID = rs.getInt("locationId");
                users.add(new User(userId, userName, userPassword, userRole, locationID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public User update(User user) {
        Connection con = DatabaseConnectionManager.getConnection();

        String insertSQL = "UPDATE bilabonnement.Users " +
                "SET name = ?, password = ?, role = ?, locationId = ? " +
                "WHERE (id = ?);";

        try {
            PreparedStatement psts = con.prepareStatement(insertSQL);
            psts.setString(1, user.getUsername());
            psts.setString(2, user.getPassword());
            psts.setString(3, user.getRole().toString());
            psts.setInt(4, user.getLocationId());
            psts.setInt(5, user.getId());
            psts.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getUserByUserName(user.getUsername());
    }

}
