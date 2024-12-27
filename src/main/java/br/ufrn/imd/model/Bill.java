package br.ufrn.imd.model;

import java.time.LocalDate;

import br.ufrn.imd.model.enums.Category;

public class Bill {
    
    private Long id;
    private String description;
    private Double value;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private Boolean isPaid;
    private Category category;

    public Bill() {
    }

    public Bill(Long id, String description, Double value, LocalDate dueDate, LocalDate paymentDate, Boolean isPaid,
            Category category) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.category = category;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Bill [description=" + description + ", value=" + value + ", dueDate=" + dueDate + ", paymentDate="
                + paymentDate + ", isPaid=" + isPaid + ", category=" + category + "]";
    }
    
}
