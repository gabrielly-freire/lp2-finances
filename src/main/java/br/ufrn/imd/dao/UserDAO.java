package br.ufrn.imd.dao;

import java.util.List;

import br.ufrn.imd.model.User;

/**
 * Interface que define os métodos que devem ser implementados por uma classe que realiza operações de CRUD em um usuário.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public interface UserDAO {
    void create(User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    void update(Long id, User user);
    void delete(Long id);
}
