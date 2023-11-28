package com.iweb.client.controller;

import com.iweb.server.entity.EmployeeInfoEntity;
import com.iweb.server.entity.ExpenseEntity;
import com.iweb.server.service.ServerStart;
import com.iweb.server.view.MainView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author LYH
 * @date 2023/11/26 14:38
 */
public class MainController {
    private static Scanner sc;
    private static EmployeeInfoEntity employeeInfo;
    public MainController(){
        sc = new Scanner(System.in);
        employeeInfo = new EmployeeInfoEntity();
    }

    public EmployeeInfoEntity getEmployeeInfo(){
        return employeeInfo;
    }

    public void startController(){
        MainView.log("输入你的选择");
        String empId,password;
        switch (sc.nextLine()){
            case "1":{
                MainView.log("输入工号");
                empId = sc.nextLine();
                MainView.log("输入密码");
                password = sc.nextLine();
                employeeInfo = ServerStart.Login(empId,password);
                if(employeeInfo != null){
                    MainView.log("成功登录");
                    MainView.loginView();
                }else{
                    MainView.log("登录失败");
                    MainView.startView();
                }
                break;
            }
            case "2":{
                break;
            }
            default:{
                break;
            }
        }
    }
    public boolean loginController() throws Exception{
        MainView.log("输入你的选择");
        if(employeeInfo.getJob().getJobGrade()>8){
            MainView.loginHideView();
        }
        switch (sc.nextLine()){
            case "1":{
                ServerStart.showEmployee(employeeInfo);
                break;
            }
            case "2":{
                MainView.log("可以修改的字段有: 姓名,电话,详细地址,银行卡,密码");
                MainView.log("输入修改字段");
                String str = sc.nextLine();
                MainView.log("输入修改后的值");
                String newstr = sc.nextLine();
                ServerStart.updateEmployeeInfo(str,newstr,employeeInfo);
                break;
            }
            case "3":{
                MainView.log("当前月份为: "+new SimpleDateFormat("yyyy-MM").format(new Date()));
                MainView.log("奖励");
                ServerStart.getEmployeeBonus(employeeInfo);
                MainView.log("惩罚");
                ServerStart.getEmployeePunish(employeeInfo);
                MainView.log("是否要打印奖金条");
                if("y".equalsIgnoreCase(sc.nextLine())){
                    ServerStart.writeExcle(employeeInfo.getBonusEntities());
                }
                MainView.log("是否打印惩罚条");
                if("y".equalsIgnoreCase(sc.nextLine())){
                    ServerStart.writeExcle(employeeInfo.getPunishEntities());
                }
            }
            case "4":{
                ExpenseEntity expense = new ExpenseEntity();
                MainView.log("输入审批人姓名");
                int empId = ServerStart.getEmployeeIdByName(sc.nextLine());
                if(empId==-1){
                    MainView.log("姓名存在错误");
                    return true;
                }
                MainView.log("输入报销内容");
                String empContent = sc.nextLine();
                if(empContent == null){
                    MainView.log("报销内容不能为空");
                    return true;
                }
                MainView.log("输入报销金额");
                BigDecimal decimal = new BigDecimal(sc.nextLine());
                expense.setExpId(0);
                expense.setInEmpId(employeeInfo.getEmpId());
                expense.setOutEmpId(empId);
                expense.setExpDate(new Date());
                expense.setExpContent(empContent);
                expense.setExpMoney(decimal);
                ServerStart.addExpense(expense);
                break;
            }
            case "5":{
                ServerStart.showAnnouncement(employeeInfo.getEmpId());
                break;
            }
            case "6":{
                BigDecimal sum = ServerStart.showSalary(employeeInfo);
                MainView.log("是否打印工资条");
                if("y".equalsIgnoreCase(sc.nextLine())){
                    ServerStart.writeExcleSalary(employeeInfo,sum);
                }
                break;
            }
            case "7":{
                ServerStart.checkWork(employeeInfo.getEmpId());
                break;
            }
            case "8":{
                MainView.log("再见");
                return false;
            }
            case "a":{
                MainView.log("输入公告对象(部门/员工/公司)");
                String str1 = sc.nextLine();
                String aObject = "";
                int empId = -1;
                switch (str1){
                    case "部门":{
                        aObject = "D";
                        MainView.log("输入部门编号");
                        empId = Integer.parseInt(sc.nextLine());
                    }
                    case "员工":{
                        aObject = "E";
                        MainView.log("输入员工id");
                        empId = Integer.parseInt(sc.nextLine());
                    }
                    case "公司":{
                        aObject = "C";
                    }
                }
                MainView.log("输入公告内容");
                String aMsg = sc.nextLine();
                if(aObject.isEmpty() | aMsg.isEmpty()){
                    MainView.log("输入有误");
                    return true;
                }
                ServerStart.addAnnouncement(empId,aObject,aMsg);
                MainView.log("发布成功");
                break;
            }
            case "b":{
                MainView.log("输入员工姓名");
                String empName = sc.nextLine();
                MainView.log("输入员工手机号");
                String phone = sc.nextLine();
                MainView.log("输入员工所在城市");
                String address = sc.nextLine();
                MainView.log("输入员工详细地址");
                String addressDetail = sc.nextLine();
                MainView.log("输入员工银行卡号");
                String card = sc.nextLine();
                MainView.log("输入员工职业编号");
                int jobId = Integer.parseInt(sc.nextLine());
                MainView.log("输入员工所在部门编号");
                int departId = Integer.parseInt(sc.nextLine());
                MainView.log("输入该员工领导姓名");
                String supName = sc.nextLine();
                if(ServerStart.addEmployee(supName,empName,phone,address,addressDetail,card,jobId,departId)){
                    MainView.log("添加成功");
                }else {
                    MainView.log("添加失败");
                }
                break;
            }
            case "c":{
                MainView.log("输入员工姓名");
                String empName = sc.nextLine();
                MainView.log("真的要解雇吗");
                if("y".equalsIgnoreCase(sc.nextLine())){
                    int empId = ServerStart.getEmployeeIdByName(empName);
                    if(ServerStart.fireEmployee(empId)){
                        MainView.log("解雇成功");
                    }else {
                        MainView.log("解雇失败");
                    }
                }
                break;
            }
            case "d":{
                ServerStart.showAllWorkingEmployee();
                break;
            }
            case "e":{
                if(ServerStart.showExpressByOutEmpId(employeeInfo)){
                    do{
                        MainView.log("输入报销单编号");
                        int expId = Integer.parseInt(sc.nextLine());
                        if(ServerStart.passExpenseByOutEmpId(expId)){
                            MainView.log("审批通过");
                        }else {
                            MainView.log("审批失败");
                        }
                        MainView.log("是否继续审批");
                        if(!"y".equalsIgnoreCase(sc.nextLine())){
                            break;
                        }
                    }while (true);
                }
            }
            default:{
                break;
            }
        }
        return true;
    }

}
