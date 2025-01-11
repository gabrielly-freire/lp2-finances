package br.ufrn.imd.model.enums;

/**
 * Enum que representa as categorias de uma conta.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public enum Category {
    FOOD("Alimentação"),
    TRANSPORT("Transporte"),
    HEALTH("Saúde"),
    EDUCATION("Educação"),
    LEISURE("Lazer"),
    OTHERS("Outros");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
