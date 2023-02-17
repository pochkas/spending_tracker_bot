package org.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ExpenseResponse implements Serializable {

   public  Expense[] expenses;

    public Expense[] getExpenses() {
        return expenses;
    }

    public void setExpenses(Expense[] expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseResponse that = (ExpenseResponse) o;
        return Arrays.equals(expenses, that.expenses);
    }


}

