package br.ufrn.imd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufrn.imd.exception.DatabaseException;

/**
 * class responsible for managing the connection with the database.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class DatabaseConnection {

    private static Connection conn = null;

    /**
     * This method is responsible for connecting to the database.
     * @throws DatabaseException if an error occurs while trying to connect to the database.
     * @return
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Credenciais para conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/library_db";
                String user = "root";
                String password = "Password123#@!";

                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
        return conn;
    }

    /**
     * This method is responsible for closing the connection with the database.
     * @throws DatabaseException if an error occurs while trying to close the connection.
     * @return
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    }
    
    /**
     * This method is responsible for closing a Statement (object used to execute a SQL query).
     * @throws DatabaseException If an error occurs while trying to close a Statement.
     * @param st
     */
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    }
    
    /**
     * This method is responsible for closing a ResultSet (object that represents the result of a SQL query).
     * @throws DatabaseException If an error occurs while trying to close a ResultSet.
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    
    }
}
