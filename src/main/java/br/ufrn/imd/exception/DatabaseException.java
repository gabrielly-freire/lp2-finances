package br.ufrn.imd.exception;

/**
 * Exception para erros de banco de dados.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 * @see RuntimeException
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String msg) {
        super(msg);
    }
    
}
