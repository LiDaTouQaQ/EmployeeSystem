package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.ExpenseDAO;
import com.iweb.server.entity.ExpenseEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/27 15:18
 */
public class ExpenseDAOImpl implements ExpenseDAO {
    @Override
    public boolean addExpense(ExpenseEntity expense) {
        if(expense == null){
            return false;
        }
        String sql = "insert into expense(in_emp_id,out_emp_id,exp_content,exp_money," +
                "exp_date) values(?,?,?,?,?)";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,expense.getInEmpId());
            ps.setInt(2,expense.getOutEmpId());
            ps.setString(3,expense.getExpContent());
            ps.setBigDecimal(4,expense.getExpMoney());
            ps.setDate(5,new Date(expense.getExpDate().getTime()));
            int num = ps.executeUpdate();
            if(num == 1){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }

    @Override
    public ArrayList<ExpenseEntity> getExpenseByOutEmpId(int empId) {
        String sql = "select * from expense where out_emp_id=?";
        ArrayList<ExpenseEntity> arrayList = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                arrayList.add(new ExpenseEntity(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getBigDecimal(5),
                        rs.getDate(6),
                        (rs.getInt(7) != 0)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return arrayList.isEmpty() ? null : arrayList;
    }

    @Override
    public boolean passExpenseByOutEmpId(int expId) {
        String sql = "update expense set is_pass = 1 where exp_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,expId);
            int num = ps.executeUpdate();
            if(num == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }
}
