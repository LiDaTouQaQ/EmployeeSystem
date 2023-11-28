package com.iweb.server.DAO;

import com.iweb.server.entity.ExpenseEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/27 15:18
 */
public interface ExpenseDAO {
    boolean addExpense(ExpenseEntity expense);
    ArrayList<ExpenseEntity> getExpenseByOutEmpId(int empId);
    boolean passExpenseByOutEmpId(int expId);
}
