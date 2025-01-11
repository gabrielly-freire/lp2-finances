package br.ufrn.imd.dao;

import br.ufrn.imd.model.Person;

/**
 * Interface que define os métodos que devem ser implementados por uma classe que realiza operações de CRUD em uma pessoa.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public interface PersonDAO {
    Long create(Person person);
    Person findById(Long id);
}
