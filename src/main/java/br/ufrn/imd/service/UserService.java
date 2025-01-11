package br.ufrn.imd.service;

import br.ufrn.imd.dao.PersonDAOImpl;
import br.ufrn.imd.dao.UserDAOImpl;
import br.ufrn.imd.model.Person;
import br.ufrn.imd.model.User;

/**
 * Classe responsável por fornecer serviços relacionados à gestão de usuários.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class UserService {
    private final UserDAOImpl userDAO;
    private final PersonDAOImpl personDAO;
    private final PasswordService passwordService;

    /**
     * Construtor padrão que inicializa os DAOs e o serviço de senhas.
     */
    public UserService() {
        this.userDAO = new UserDAOImpl();
        this.personDAO = new PersonDAOImpl();
        this.passwordService = new PasswordService();
    }

    /**
     * Cria um novo usuário no sistema, associando-o a uma pessoa.
     * 
     * @param user   Objeto User a ser criado.
     * @param person Objeto Person associado ao usuário.
     * @throws RuntimeException se já existir um usuário com o mesmo nome de usuário.
     */
    public void create(User user, Person person) {
        validateUsername(user.getUsername());

        Long idPerson = personDAO.create(person);
        String password = passwordService.hashPassword(user.getPassword());
        user.setIdPerson(idPerson);
        user.setPassword(password);

        userDAO.create(user);
    }

    /**
     * Redefine a senha de um usuário existente.
     * 
     * @param username Nome de usuário cujo a senha será redefinida.
     * @param password Nova senha a ser definida.
     */
    public void resetPassword(String username, String password) {
        User user = userDAO.findByUsername(username);
        String criptPassword = passwordService.hashPassword(password);

        user.setPassword(criptPassword);
        userDAO.update(user.getId(), user);
    }

    /**
     * Localiza um usuário pelo nome de usuário.
     * 
     * @param username Nome de usuário a ser pesquisado.
     * @return Objeto User correspondente ao nome de usuário fornecido.
     */
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * Valida se o nome de usuário já existe no sistema.
     * 
     * @param username Nome de usuário a ser validado.
     * @throws RuntimeException se já existir um usuário com o mesmo nome de usuário.
     */
    private void validateUsername(String username) {
        if (userDAO.findByUsername(username) != null) {
            throw new RuntimeException("Já existe um usuário com esse username");
        }
    }
}
