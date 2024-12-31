package br.ufrn.imd.service;

import br.ufrn.imd.dao.PersonDAOImpl;
import br.ufrn.imd.dao.UserDAOImpl;
import br.ufrn.imd.model.Person;
import br.ufrn.imd.model.User;

public class UserService {
    private final UserDAOImpl userDAO;
    private final PersonDAOImpl personDAO;
    private final PasswordService passwordService;

    public UserService() {
        this.userDAO = new UserDAOImpl();
        this.personDAO = new PersonDAOImpl();
        this.passwordService = new PasswordService();
    }


    public void create(User user, Person person){
        System.out.println("Validando");
        validateUsername(user.getUsername());
        System.out.println("Validou");

        Long idPerson = personDAO.create(person);
        System.out.println("Id pessoa: "+idPerson);
        String password = passwordService.hashPassword(user.getPassword());
        user.setIdPerson(idPerson);
        user.setPassword(password);

        userDAO.create(user);
        System.out.println("criou");
    }

    public void resetPassword(String username, String password){
        User user = userDAO.findByUsername(username);
        String criptPassword = passwordService.hashPassword(password);

        user.setPassword(criptPassword);
    }

    public User findByUsername(String username){
        return userDAO.findByUsername(username);
    }

    private void validateUsername(String username){
        if (userDAO.findByUsername(username) != null) {
            throw new RuntimeException("Já existe um usuário com esse username");
        }
    }
    
}
