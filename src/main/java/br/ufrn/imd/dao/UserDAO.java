package br.ufrn.imd.dao;

import java.util.List;

import br.ufrn.imd.model.User;

/**
 * Interface that defines the methods that must be implemented by a UserDAO.
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
