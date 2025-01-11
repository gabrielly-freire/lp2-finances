package br.ufrn.imd.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.ufrn.imd.database.DatabaseConnection;
import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;

/**
 * Classe que implementa os métodos de CRUD para uma fatura no banco de dados.
 * 
 * @see BillDAO
 * @autor Gabrielly Freire
 * @versão 1.0
 */
public class BillDAOImpl implements BillDAO {
    private final Connection connection;

    public BillDAOImpl() {
        this.connection = DatabaseConnection.getConnection();

    }

    /**
     * Método que cria uma nova fatura no banco de dados.
     * 
     * @param bill A fatura a ser criada.
     * @throws DatabaseException Se ocorrer um erro ao criar a fatura.
     */
    @Override
    public void create(Bill bill) {
        String sql = "INSERT INTO bills (description, value, due_date, payment_date, is_paid, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bill.getDescription());
            stmt.setDouble(2, bill.getValue());
            stmt.setDate(3, Date.valueOf(bill.getDueDate()));
            stmt.setDate(4, bill.getPaymentDate() != null ? Date.valueOf(bill.getPaymentDate()) : null);
            stmt.setBoolean(5, bill.getIsPaid());
            stmt.setString(6, bill.getCategory().name());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bill.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar fatura: " + e.getMessage());
        }
    }

    /**
     * Método que busca uma fatura pelo seu ID.
     * 
     * @param id O ID da fatura a ser encontrada.
     * @return A fatura encontrada.
     * @throws DatabaseException Se ocorrer um erro ao buscar a fatura.
     */
    @Override
    public Bill findById(Long id) {
        Bill bill = null;
        String sql = "SELECT * FROM bills WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bill = new Bill(
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getDouble("value"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null,
                        rs.getBoolean("is_paid"),
                        Category.valueOf(rs.getString("category")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar fatura por ID: " + e.getMessage());
        }
        return bill;
    }

    /**
     * Método que busca todas as faturas no banco de dados.
     * 
     * @return Uma lista com todas as faturas encontradas.
     * @throws DatabaseException Se ocorrer um erro ao buscar as faturas.
     */
    @Override
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getDouble("value"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null,
                        rs.getBoolean("is_paid"),
                        Category.valueOf(rs.getString("category")));
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todas as faturas: " + e.getMessage());
        }
        return bills;
    }

    /**
     * Método que atualiza uma fatura no banco de dados.
     * 
     * @param id   O ID da fatura a ser atualizada.
     * @param bill A fatura com os novos dados.
     * @throws DatabaseException Se ocorrer um erro ao atualizar a fatura.
     */
    @Override
    public void update(Long id, Bill bill) {
        String sql = "UPDATE bills SET description = ?, value = ?, due_date = ?, payment_date = ?, is_paid = ?, category = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bill.getDescription());
            stmt.setDouble(2, bill.getValue());
            stmt.setDate(3, Date.valueOf(bill.getDueDate()));
            stmt.setDate(4, bill.getPaymentDate() != null ? Date.valueOf(bill.getPaymentDate()) : null);
            stmt.setBoolean(5, bill.getIsPaid());
            stmt.setString(6, bill.getCategory().name());
            stmt.setLong(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar fatura: " + e.getMessage());
        }
    }

    /**
     * Método que deleta uma fatura do banco de dados.
     * 
     * @param id O ID da fatura a ser deletada.
     * @throws DatabaseException Se ocorrer um erro ao deletar a fatura.
     */
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM bills WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar fatura: " + e.getMessage());
        }
    }
}
