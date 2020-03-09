package com.database;

import java.sql.*;
import java.util.List;

import com.dispatcher.Account;
import com.sister.backend.*;

public class DBUtils {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/accounts";
    static final String USER = "root";
    static final String PASS = "root";
    static final String SELECT_QUERY = "SELECT * FROM sister_Accounts";
    private static final String INSERT_QUERY = "INSERT INTO sister_Accounts (username, password) VALUES ('%s', '%s')";
    private static final String DELETE_QUERY = "DELETE FROM sister_Accounts WHERE username='%s'";

    public static boolean insert(Account a) {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format(INSERT_QUERY, a.getUsername(), a.getPassword()));
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("Account :\n" + a.toString() + "\nduplicated, skipping..");
            System.out.println("Operations on database completed, with errors !");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {

            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se3) {

            }
        }

        System.out.println("Operations on database completed with no errors !");
        return true;

    }

    public static boolean delete(Account a) {
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format(DELETE_QUERY, a.getUsername()));
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("Account :\n" + a.toString() + "\nnot valid, skipping..");
            System.out.println("Operations on database completed, with errors !");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {

            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se3) {

            }
        }

        System.out.println("Operations on database completed with no errors !");
        return true;

    }

    public static void getAccounts(List<Account> accs) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_QUERY);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                accs.add(new SisterMockAccount(username, password));
            }
            st.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        System.out.println("Operations on database completed with no errors !");
    }

    public static void main(String[] args) {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/accounts", "root", "root");
            Statement st = conn.createStatement();    
        } catch (SQLException e) {
            e.printStackTrace();
        }}
}