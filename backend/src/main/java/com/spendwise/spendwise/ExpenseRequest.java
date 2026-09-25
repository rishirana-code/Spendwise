package com.spendwise.spendwise;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NonNull;

public class ExpenseRequest {
      @NotNull(message = "Amount is req")
      @Positive(message = "Amount must be greater than zero")
      private Double amount;

      @NotBlank(message = "Note is req...")
      private String note;


       private String category;

      private java.time.LocalDate date;

      public java.time.LocalDate getDate(){return date;}
      public void setDate(java.time.LocalDate date){this.date =  date;}

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
