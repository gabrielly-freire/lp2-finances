package br.ufrn.imd.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufrn.imd.exception.DatabaseException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 * 
 * @autor Gabrielly Freire
 * @versão 1.0
 */
public class DatabaseConnection {

    private static Connection conn = null;

    /**
     * Este método é responsável por estabelecer a conexão com o banco de dados.
     * @throws DatabaseException se ocorrer um erro ao tentar conectar ao banco de dados.
     * @return uma instância da conexão estabelecida.
     */
    public static Connection getConnection() {
        if (conn == null || isClosed()) {
            try {
                // Credenciais para conexão com o banco de dados
                String url = "jdbc:mysql://localhost:3306/myfinances";
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
     * Este método é responsável por verificar se a conexão com o banco de dados está fechada.
     * @throws DatabaseException se ocorrer um erro ao tentar verificar se a conexão está fechada.
     * @return true se a conexão estiver fechada, caso contrário false.
     */
    private static boolean isClosed() {
        try {
            return conn.isClosed();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
    
    /**
     * Este método é responsável por fechar a conexão com o banco de dados.
     * @throws DatabaseException se ocorrer um erro ao tentar fechar a conexão.
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
     * Este método é responsável por fechar um Statement (objeto usado para executar uma consulta SQL).
     * @throws DatabaseException se ocorrer um erro ao tentar fechar um Statement.
     * @param st o Statement a ser fechado.
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
     * Este método é responsável por fechar um ResultSet (objeto que representa o resultado de uma consulta SQL).
     * @throws DatabaseException se ocorrer um erro ao tentar fechar um ResultSet.
     * @param rs o ResultSet a ser fechado.
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
