package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufrn.imd.database.DatabaseConnection;
import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.Person;

/**
 * Interface responsible for defining the methods that must be implemented by
 * the PersonDAOImpl class.
 * 
 * @version 1.0
 */
public class PersonDAOImpl implements PersonDAO {

    private final Connection connection;

    public PersonDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Method responsible for creating a new person in the database.
     * 
     * @param person Person object to be created.
     * @return Long ID of the created person.
     */
    @Override
    public Long create(Person person) {
        String sql = "INSERT INTO people (name, cpf, birth_date, address, phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, person.getName());
            pstmt.setString(2, person.getCpf());
            pstmt.setDate(3, java.sql.Date.valueOf(person.getBirthDate()));
            pstmt.setString(4, person.getAddress());
            pstmt.setString(5, person.getPhone());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method responsible for finding a person in the database by ID.
     * 
     * @param id ID of the person to be found.
     * @return Person object found.
     * @throws DatabaseException if an error occurs while searching for the person.
     */
    @Override
    public Person findById(Long id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setCpf(rs.getString("cpf"));
                person.setBirthDate(rs.getDate("birth_date").toLocalDate());
                person.setAddress(rs.getString("address"));
                person.setPhone(rs.getString("phone"));
                return person;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar pessoa por ID: " + e.getMessage());
        }
        return null;
    }
}
