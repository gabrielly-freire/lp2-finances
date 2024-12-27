package br.ufrn.imd.exception;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 * @see RuntimeException
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    
}
