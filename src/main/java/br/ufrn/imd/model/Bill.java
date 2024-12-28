package br.ufrn.imd.model;

import java.time.LocalDate;

import br.ufrn.imd.model.enums.Category;

/**
 * Class that represents a bill.
 * 
 * @version 1.0
 */
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
        result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
        result = prime * result + ((isPaid == null) ? 0 : isPaid.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bill other = (Bill) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        if (dueDate == null) {
            if (other.dueDate != null)
                return false;
        } else if (!dueDate.equals(other.dueDate))
            return false;
        if (paymentDate == null) {
            if (other.paymentDate != null)
                return false;
        } else if (!paymentDate.equals(other.paymentDate))
            return false;
        if (isPaid == null) {
            if (other.isPaid != null)
                return false;
        } else if (!isPaid.equals(other.isPaid))
            return false;
        if (category != other.category)
            return false;
        return true;
    }
    
}
