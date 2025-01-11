package br.ufrn.imd.service;

import org.mindrot.jbcrypt.BCrypt;

import br.ufrn.imd.dao.UserDAOImpl;
import br.ufrn.imd.model.User;

/**
 * Classe que implementa métodos para manipulação de senhas.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class PasswordService {

    /**
     * Método que gera um hash para uma senha.
     * 
     * @param password Senha a ser criptografada.
     * @return String com a senha criptografada.
     */
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Método que valida o login de um usuário.
     * 
     * @param username Nome de usuário.
     * @param password Senha.
     * @return true se o login for válido, false caso contrário.
     */
    public boolean validateLogin(String username, String password) {
        UserDAOImpl userDAO = new UserDAOImpl();

        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(password, user.getPassword());
    }
}
