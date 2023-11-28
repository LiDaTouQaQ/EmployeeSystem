package com.iweb.server.DAO;

import com.iweb.server.entity.EmployeeInfoEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/26 15:07
 */
public interface EmployDAO {
    EmployeeInfoEntity getEmploy(String empId,String password);
    void updateEmploy(EmployeeInfoEntity employeeInfo);
    int getEmployIdByName(String name);
    boolean addEmployee(String supName,String empName,String phone,String address,String addressDetail,String card,int jobId,int departId);
    boolean isExitEmpId(int empId);
    boolean fireEmployee(int empId);

    ArrayList<EmployeeInfoEntity> getAllWorkingEmployee();
}
