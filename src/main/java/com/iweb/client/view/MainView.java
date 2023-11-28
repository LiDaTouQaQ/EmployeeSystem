package com.iweb.client.view;

import com.iweb.server.controller.MainController;

/**
 * @author LYH
 * @date 2023/11/26 14:36
 */
public class MainView {
    static MainController mainController = new MainController();
    public static String allLog = "";
    public static String log(String str){
        allLog += str +"\n";
        System.out.println(str);
        return str;
    }
    public static void startView(){
        log("欢迎进入员工管理系统");
        log("1.登录");
        log("2.退出");
        mainController.startController();
    }
    public static void loginView(){
        log("欢迎"+mainController.getEmployeeInfo().getEmpName());
        boolean flag = true;
        while (flag){
            log("1.查看基本信息");
            log("2.修改基本信息");
            log("3.查看本月的奖惩罚");
            log("4.申请报销");
            log("5.查看公司公告");
            log("6.查看本月工资");
            log("7.本日打卡");
            log("8.退出系统");
            try {
                flag = mainController.loginController();
            } catch (Exception e) {
                System.out.println("输入有误");
            }
        }
    }
    public static void loginHideView(){
        log("管理员功能");
        log("a.发布公告");
        log("b.添加员工");
        log("c.解雇员工");
        log("d.查询当前在职员工");
        log("e.通过报销");
    }
}
