package org.example.service;

import org.example.response.GetByCategory;
import org.example.response.GroupByCategory;
import org.example.response.GroupByCategoryAndMonth;
import org.example.model.Expense;
import org.example.model.ExpenseResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    public List<Expense> getAll(UUID userid);

    public void addExpense(UUID userid, String category, Double price, LocalDateTime date);

    public ExpenseResponse update(UUID userid, Long id);

    public ExpenseResponse delete(UUID userid, Long id);

    public ExpenseResponse getExpense(UUID userid, Long id);

    public List<GetByCategory> findAllByCategory(UUID userid, String category);

    public List<GroupByCategory> groupByCategory(UUID userid);

    public List<GroupByCategoryAndMonth> groupByCategoryAndMonth(UUID userid);
}
