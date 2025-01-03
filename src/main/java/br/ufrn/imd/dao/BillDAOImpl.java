package br.ufrn.imd.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.ufrn.imd.database.DatabaseConnection;
import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.Bill;
import br.ufrn.imd.model.enums.Category;

/**
 * Class that implements the methods of the BillDAO interface.
 * 
 * @see BillDAO
 * @author Gabrielly Freire
 * @version 1.0
 */
public class BillDAOImpl implements BillDAO {
    private final Connection connection;

    public BillDAOImpl() {
        this.connection = DatabaseConnection.getConnection();

    }

    /**
     * Method that creates a new bill in the database.
     * 
     * @param bill The bill to be created.
     * @throws DatabaseException If an error occurs while creating the bill.
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
     * Method that finds a bill by its ID.
     * 
     * @param id The ID of the bill to be found.
     * @return The bill found.
     * @throws DatabaseException If an error occurs while searching for the bill.
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
     * Method that finds all bills in the database.
     * 
     * @return A list with all bills found.
     * @throws DatabaseException If an error occurs while searching for the bills.
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
     * Method that updates a bill in the database.
     * 
     * @param id   The ID of the bill to be updated.
     * @param bill The bill with the new data.
     * @throws DatabaseException If an error occurs while updating the bill.
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
     * Method that deletes a bill from the database.
     * 
     * @param id The ID of the bill to be deleted.
     * @throws DatabaseException If an error occurs while deleting the bill.
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
