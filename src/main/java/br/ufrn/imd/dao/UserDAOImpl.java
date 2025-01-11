package br.ufrn.imd.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.database.DatabaseConnection;
import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.User;

/**
 * Classe que implementa a interface UserDAO.
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
     * Método que cria um usuário no banco de dados.
     * 
     * @param user Usuário a ser criado
     * @throws DatabaseException se ocorrer um erro ao criar o usuário
     */
    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username, password, id_person) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getIdPerson());
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
     * Método que busca um usuário pelo ID no banco de dados.
     * 
     * @param id ID do usuário a ser encontrado
     * @return Usuário encontrado
     * @throws DatabaseException se ocorrer um erro ao buscar o usuário
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
                        rs.getString("password"),
                        rs.getLong("id_person"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return user;
    }

    /**
     * Método que busca um usuário pelo nome de usuário no banco de dados.
     * 
     * @param username Nome de usuário a ser encontrado
     * @return Usuário encontrado
     * @throws DatabaseException se ocorrer um erro ao buscar o usuário
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
                user.setIdPerson(rs.getLong("id_person"));

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar usuário: " + e.getMessage());
        } 
        
        return null;
    }

    /**
     * Método que busca todos os usuários no banco de dados.
     * 
     * @return Lista de usuários encontrados
     * @throws DatabaseException se ocorrer um erro ao buscar os usuários
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
                        rs.getString("password"),
                        rs.getLong("id_person"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return users;
    }

    /**
     * Método que atualiza um usuário no banco de dados.
     * 
     * @param id   ID do usuário a ser atualizado
     * @param user Usuário a ser atualizado
     * @throws DatabaseException se ocorrer um erro ao atualizar o usuário
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
     * Método que exclui um usuário no banco de dados.
     * 
     * @param id ID do usuário a ser excluído
     * @throws DatabaseException se ocorrer um erro ao excluir o usuário
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
