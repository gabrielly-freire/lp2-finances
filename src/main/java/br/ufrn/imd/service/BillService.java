package br.ufrn.imd.service;

import br.ufrn.imd.dao.BillDAOImpl;
import br.ufrn.imd.exception.ResourceNotFoundException;
import br.ufrn.imd.model.Bill;

import java.util.List;

/**
 * Classe de serviço para operações relacionadas a faturas.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class BillService {

    private final BillDAOImpl billDAO;

    public BillService() {
        this.billDAO = new BillDAOImpl();
    }

    /**
     * Cria uma nova fatura no banco de dados.
     * 
     * @param bill Fatura a ser inserida.
     * @throws IllegalArgumentException Se a fatura for nula, se a descrição, valor,
     *                                  data de vencimento ou categoria forem nulos ou inválidos.
     */
    public void createBill(Bill bill) {
        validateBill(bill);
        billDAO.create(bill);
    }

    /**
     * Busca uma fatura pelo ID.
     * 
     * @param id ID da fatura.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     * @throws ResourceNotFoundException Se a fatura não for encontrada.
     * @return Fatura encontrada.
     */
    public Bill findBillById(Long id) {
        validateId(id);
        Bill bill = billDAO.findById(id);

        if (bill == null) {
            throw new ResourceNotFoundException("Fatura não encontrada.");
        }

        return bill;
    }

    /**
     * Retorna todas as faturas no banco de dados.
     * 
     * @throws ResourceNotFoundException Se não existirem faturas.
     * @return Lista de faturas.
     */
    public List<Bill> findAllBills() {
        List<Bill> bills = billDAO.findAll();

        if (bills == null || bills.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma fatura encontrada.");
        }

        return bills;
    }

    /**
     * Atualiza os dados de uma fatura.
     * 
     * @param id   ID da fatura a ser atualizada.
     * @param bill Dados atualizados da fatura.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero, se
     *                                  a fatura for nula, se a descrição, valor, data de
     *                                  vencimento ou categoria forem nulos ou inválidos.
     * @throws ResourceNotFoundException Se a fatura não for encontrada.
     */
    public void updateBill(Long id, Bill bill) {
        validateId(id);
        validateBill(bill);
        findBillById(id);

        billDAO.update(id, bill);
    }

    /**
     * Deleta uma fatura pelo ID.
     * 
     * @param id ID da fatura a ser deletada.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     * @throws ResourceNotFoundException Se a fatura não for encontrada.
     */
    public void deleteBill(Long id) {
        validateId(id);
        findBillById(id);

        billDAO.delete(id);
    }

    /**
     * Valida os dados de uma fatura.
     * 
     * @param bill Fatura a ser validada.
     * @throws IllegalArgumentException Se a fatura for nula, se a descrição, valor,
     *                                  data de vencimento ou categoria forem nulos ou inválidos.
     */
    private void validateBill(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("A fatura não pode ser nula.");
        }
        if (bill.getDescription() == null || bill.getDescription().isEmpty()) {
            throw new IllegalArgumentException("A descrição da fatura é obrigatória.");
        }
        if (bill.getValue() == null || bill.getValue() <= 0) {
            throw new IllegalArgumentException("O valor da fatura deve ser positivo.");
        }
        if (bill.getDueDate() == null) {
            throw new IllegalArgumentException("A data de vencimento da fatura é obrigatória.");
        }
        if (bill.getCategory() == null) {
            throw new IllegalArgumentException("A categoria da fatura é obrigatória.");
        }
    }

    /**
     * Valida o ID de uma fatura.
     * 
     * @param id ID da fatura.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da fatura inválido.");
        }
    }
}
