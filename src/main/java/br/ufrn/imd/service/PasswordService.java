package br.ufrn.imd.service;

import org.mindrot.jbcrypt.BCrypt;

import br.ufrn.imd.dao.UserDAOImpl;
import br.ufrn.imd.model.User;

public class PasswordService {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean validateLogin(String username, String password) {
        UserDAOImpl userDAO = new UserDAOImpl();

        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(password, user.getPassword());

    }
}
