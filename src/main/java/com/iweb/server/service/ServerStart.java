package com.iweb.server.service;

import com.alibaba.excel.EasyExcel;
import com.iweb.server.DAO.*;
import com.iweb.server.DAO.impl.*;
import com.iweb.server.entity.*;
import com.iweb.server.view.MainView;
import sun.applet.Main;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 14:34
 */
public class ServerStart {
    static EmployDAO employDAO = new EmployDAOImpl();
    static ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    static AnnouncementDAO announcementDAO = new AnnouncementDAOImpl();
    static CheckWorkDAO checkWorkDAO = new CheckWorkDAOImpl();
    static PunishDAO punishDAO = new PunishDAOImpl();
    static BonusDAO bonusDAO = new BonusDAOImpl();
    static JobDAO jobDAO = new JobDAOImpl();
    public static EmployeeInfoEntity Login(String empId,String password){
        return employDAO.getEmploy(empId,password);
    }

    public static void showEmployee(EmployeeInfoEntity employeeInfo){
        MainView.log("员工编号: "+employeeInfo.getEmpId());
        MainView.log("员工姓名: "+employeeInfo.getEmpName());
        MainView.log("员工手机号: "+employeeInfo.getPhone());
        MainView.log("员工地址： 城市: "+employeeInfo.getAddress().getCity()+"\t省份: "+employeeInfo.getAddress().getProvince());
        MainView.log("详细地址: "+employeeInfo.getAddressDetail());
        MainView.log("银行卡号: "+employeeInfo.getCard());
        MainView.log("入职时间: "+new SimpleDateFormat("yyyy-MM-dd").format(employeeInfo.getHiredate()));
        MainView.log("密码: "+employeeInfo.getPassword());
    }

    public static void updateEmployeeInfo(String str,String newStr,EmployeeInfoEntity employeeInfo){
        switch (str){
            case "姓名":{
                employeeInfo.setEmpName(newStr);
                break;
            }
            case "电话":{
                employeeInfo.setPhone(newStr);
                break;
            }
            case "详细地址":{
                employeeInfo.setAddressDetail(newStr);
                break;
            }
            case "银行卡":{
                employeeInfo.setCard(newStr);
                break;
            }
            case "密码":{
                employeeInfo.setPassword(newStr);
                break;
            }
            default:{
                System.out.println("字段值有误");
                return;
            }
        }
        employDAO.updateEmploy(employeeInfo);
    }

    public static void getEmployeeBonus(EmployeeInfoEntity employeeInfo){
        MainView.log("奖励编号 \t 奖项名称 \t 奖金 \t 奖励时间");
        for (BonusEntity b : employeeInfo.getBonusEntities()) {
            MainView.log(b.toString());
            // if(b.getBonusDate().getMonth() == new Date().getMonth()){
            // }
        }
    }
    public static void getEmployeePunish(EmployeeInfoEntity employeeInfo){
        MainView.log("惩罚编号 \t 惩罚名称 \t 奖金 \t 奖励时间");
        for (PunishEntity p : employeeInfo.getPunishEntities()) {
            MainView.log(p.toString());
        }
    }

    public static void writeExcle(ArrayList arrayList){
        String name = "";
        if(arrayList.isEmpty()){
            MainView.log("数据量为空");
            return;
        }else{
            if(arrayList.get(0) instanceof BonusEntity){
                name = "奖金";
            }
            if(arrayList.get(0) instanceof PunishEntity){
                name = "惩罚";
            }
        }
        String fileName = "E:/Eclipse-workspace/EmploySystem/"+ name + System.currentTimeMillis()+ ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, arrayList.get(0).getClass())
                .sheet("sheet1")
                .doWrite(() -> {
                    // 分页查询数据
                    return arrayList;
                });
        MainView.log("打印完成");
    }

    public static void addExpense(ExpenseEntity expense){
        if(expenseDAO.addExpense(expense)){
            MainView.log("报销已提交,等待结果");
        }else{
            MainView.log("报销提交失败");
        }
    }

    public static int getEmployeeIdByName(String name){
        return employDAO.getEmployIdByName(name);
    }

    public static void showAnnouncement(int empId){
        ArrayList<AnnouncementEntity> arrayList = announcementDAO.getAnnouncementToEmployee(empId);
        MainView.log("公告类型 \t 公告内容 \t 公告时间");
        for (AnnouncementEntity a : arrayList) {
            MainView.log(a.toString());
        }
    }

    public static void showAllJob(){
        MainView.log("职位编号\t职位名称\t职位等级\t职位薪水");
        for (JobEntity j : jobDAO.getAllJob()) {
            MainView.log(j.toString());
        }
    }

    public static BigDecimal showSalary(EmployeeInfoEntity employeeInfo){
        BigDecimal sum = new BigDecimal(employeeInfo.getJob().getSalary().intValue());
        for (BonusEntity b :employeeInfo.getBonusEntities()) {
            if(b.getBonusDate().getMonth() == new Date().getMonth()){
                sum.add(b.getBonusMoney());
            }
        }
        for (PunishEntity p: employeeInfo.getPunishEntities()) {
            if(p.getPunishDate().getMonth() == new Date().getMonth()){
                sum.subtract(p.getPunishMoney());
            }
        }
        MainView.log("本月薪资为: "+sum);
        return sum;
    }

    public static void writeExcleSalary(EmployeeInfoEntity employeeInfo,BigDecimal sum){
        try {
            ArrayList<Salary> salaries = new ArrayList<>();
            Salary salary = new Salary(employeeInfo.getEmpId(),
                    employeeInfo.getEmpName(),
                    employeeInfo.getJob().getJobName(),
                    sum,
                    employeeInfo.getJob().getSalary(),
                    employeeInfo.getBonusEntities(),
                    employeeInfo.getPunishEntities());
            salaries.add(salary);
            String fileName = "E:/Eclipse-workspace/EmploySystem/" +employeeInfo.getEmpName()+ "的工资条" + System.currentTimeMillis() + ".xlsx";
            EasyExcel.write(fileName, Salary.class)
                    .sheet("sheet1")
                    .doWrite(() -> {
                        // 分页查询数据
                        return salaries;
                    });
            MainView.log("打印完成");
        }catch (Exception e){
            MainView.log("打印失败");
        }
    }

    public static void checkWork(int empId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(checkWorkDAO.isChecking(empId,date)){
           MainView.log("今天已经打过卡了");
        }else{
            if(checkWorkDAO.addCheckWork(empId)){
                MainView.log("打卡成功");
            }else {
                MainView.log("打卡失败");
            }
        }
    }

    public static void addAnnouncement(int empId,String aObject,String aMsg){
        announcementDAO.addAnnouncement(empId,aObject,aMsg);
    }

    public static boolean addEmployee(String supName,String empName,String phone,String address,String addressDetail,String card,int jobId,int departId){
        return employDAO.addEmployee(supName, empName, phone, address, addressDetail, card, jobId, departId);
    }

    public static boolean fireEmployee(int empId){
        return employDAO.fireEmployee(empId);
    }

    public static void showAllWorkingEmployee(){
        ArrayList<EmployeeInfoEntity> arrayList = employDAO.getAllWorkingEmployee();
        for (EmployeeInfoEntity e :arrayList) {
            showEmployee(e);
        }
    }

    public static boolean showExpressByOutEmpId(EmployeeInfoEntity employeeInfo){
        ArrayList<ExpenseEntity> arrayList = expenseDAO.getExpenseByOutEmpId(employeeInfo.getEmpId());
        MainView.log("报销单编号\t申请人id\t审批人id\t报销内容\t报销金额\t报销时间\t是否通过");
        if (arrayList!=null){
            for (ExpenseEntity e :arrayList) {
                MainView.log(e.toString());
            }
            return true;
        }else {
            MainView.log("没有属于你审批的报销");
            return false;
        }
    }

    public static boolean passExpenseByOutEmpId(int expId){
        return expenseDAO.passExpenseByOutEmpId(expId);
    }

    public static void showAllPunish(){
        ArrayList<PunishEntity> punishEntities = punishDAO.getAllPunishMsg();
        MainView.log("惩罚编号\t惩罚内容\t惩罚金额");
        for (PunishEntity p :punishEntities) {
            MainView.log(p.toString());
        }
    }

    public static void showAllBonus(){
        ArrayList<BonusEntity> bonusEntities = bonusDAO.getAllBonus();
        MainView.log("惩罚编号\t惩罚内容\t惩罚金额");
        for (BonusEntity b :bonusEntities) {
            MainView.log(b.toString());
        }
    }

    public static void addPunishToEmployee(int punishId,int empId){
        if(punishDAO.addPunish(punishId,empId)){
            MainView.log("惩罚成功");
        }else {
            MainView.log("惩罚失败");
        }
    }

    public static void addBonusToEmployee(int bonusId,int empId){
        if(bonusDAO.addBonus(bonusId,empId)){
            MainView.log("奖励成功");
        }else {
            MainView.log("奖励失败");
        }
    }

    public static void main(String[] args) {
        String path = "E:\\Eclipse-workspace\\EmploySystem\\李小头的工资条1701089229617.xlsx";
        File file = new File("E:\\Eclipse-workspace\\EmploySystem\\李小头的工资条1701089229617.xlsx");
        // EasyExcel.read(path,Salary.class)
    }
}
