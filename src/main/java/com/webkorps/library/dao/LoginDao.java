package com.webkorps.library.dao;

import com.webkorps.library.models.User;
import com.webkorps.library.util.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    private static final String SQL_CHECKEMAIL_QUERY = "select * from users where Email=?";
    private static final String SQL_LOGIN_QUERY = "select * from users where MemberId=? and Password=?";
    private static final String SQL_SAVE_ADMIN_QUERY = "insert into users (MemberId, Name, NameOfLibrary, Address, Email, Role, Password) "
            + "values(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SAVE_USER_QUERY = "insert into users (MemberId, Name, Email, Role, Password) "
            + "values(?, ?, ?, ?, ?)";

    public static User getUser(String memberId, String password) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_LOGIN_QUERY);) {

            statement.setString(1, memberId);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setMemberId(rs.getString("MemberId"));
                user.setRole(rs.getString("Role").toLowerCase());
                user.setPassword(rs.getString("Password"));
                user.setUserId(rs.getInt("UserId"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int saveAdmin(String memberId, String name, String libraryName, String address,
            String email, String role, String password) {

        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_SAVE_ADMIN_QUERY);) {

            statement.setString(1, memberId);
            statement.setString(2, name);
            statement.setString(3, libraryName);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setString(6, role);
            statement.setString(7, password);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int saveUser(String memberId, String name, String email, String role, String password) {

        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_SAVE_USER_QUERY);) {

            statement.setString(1, memberId);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, role);
            statement.setString(5, password);
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean doesEmailExist(String email) {
        try (Connection con = DbUtil.getDataSource().getConnection(); PreparedStatement statement = con.prepareStatement(SQL_CHECKEMAIL_QUERY);) {
            statement.setString(1, email);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
