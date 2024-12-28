package br.ufrn.imd.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.database.DatabaseConnection;
import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.User;

/**
 * Class that implements the UserDAO interface.
 * 
 * @see UserDAO
 * @author Gabrielly Freire
 * @version 1.0
 */
public class UserDAOImpl implements UserDAO {

    private final Connection connection;

    public UserDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Method that creates a user in the database.
     * 
     * @param user User to be created
     * @throws DatabaseException if an error occurs while creating the user
     */
    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    /**
     * Method that finds a user by ID in the database.
     * 
     * @param id ID of the user to be found
     * @return User found
     * @throws DatabaseException if an error occurs while searching for the user
     */
    @Override
    public User findById(Long id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return user;
    }

    /**
     * Method that finds a user by username in the database.
     * 
     * @param username Username of the user to be found
     * @return User found
     * @throws DatabaseException if an error occurs while searching for the user
     */
    public User findByUsername(String username) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar usuário: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Method that finds all users in the database.
     * 
     * @return List of users found]
     * @throws DatabaseException if an error occurs while searching for users
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return users;
    }

    /**
     * Method that updates a user in the database.
     * 
     * @param id ID of the user to be updated
     * @param user User to be updated
     * @throws DatabaseException if an error occurs while updating the user
     */
    @Override
    public void update(Long id, User user) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    /**
     * Method that deletes a user in the database.
     * 
     * @param id ID of the user to be deleted
     * @throws DatabaseException if an error occurs while deleting the user
     */
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

}
