package br.ufrn.imd.dao;

import java.util.List;

import br.ufrn.imd.model.Bill;

/**
 * Interface que define os métodos que devem ser implementados por uma classe que realiza operações de CRUD em uma conta.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public interface BillDAO {

    void create(Bill bill);
    Bill findById(Long id);
    List<Bill> findAll();
    void update(Long id, Bill bill);
    void delete(Long id);
    
}