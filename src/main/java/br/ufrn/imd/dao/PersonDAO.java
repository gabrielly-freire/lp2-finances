package br.ufrn.imd.dao;

import br.ufrn.imd.model.Person;

/**
 * Interface responsible for defining the methods that must be implemented by
 * the PersonDAOImpl class.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public interface PersonDAO {
    Long create(Person person);
    Person findById(Long id);
}
