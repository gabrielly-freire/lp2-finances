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
 * Classe responsável por implementar os métodos definidos pela interface PersonDAO.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class PersonDAOImpl implements PersonDAO {

    private final Connection connection;

    /**
     * Construtor padrão que inicializa a conexão com o banco de dados.
     */
    public PersonDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Método responsável por criar uma nova pessoa no banco de dados.
     * 
     * @param person Objeto Person a ser criado.
     * @return Long ID da pessoa criada.
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
     * Método responsável por encontrar uma pessoa no banco de dados pelo ID.
     * 
     * @param id ID da pessoa a ser encontrada.
     * @return Objeto Person encontrado.
     * @throws DatabaseException se ocorrer um erro ao buscar a pessoa.
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
