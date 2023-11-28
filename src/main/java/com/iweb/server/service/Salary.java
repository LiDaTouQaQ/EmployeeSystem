package com.iweb.server.service;

import com.alibaba.excel.annotation.ExcelProperty;
import com.iweb.server.entity.BonusEntity;
import com.iweb.server.entity.PunishEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/27 20:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @ExcelProperty("员工编号")
    private Integer empId;
    @ExcelProperty("员工姓名")
    private String empName;
    @ExcelProperty("职业")
    private String jobName;
    @ExcelProperty("总薪水")
    private BigDecimal sum;
    @ExcelProperty("基本薪资")
    private BigDecimal baseSalary;
    @ExcelProperty("奖金")
    private String bonus;
    @ExcelProperty("惩罚")
    private String punish;


    public Salary(int empId,String empName,String jobName,BigDecimal sum,BigDecimal baseSalary,ArrayList<BonusEntity> bonusEntities,ArrayList<PunishEntity> punishEntities){
        this.empName = empName;
        this.empId = empId;
        this.jobName = jobName;
        this.sum = sum;
        this.baseSalary = baseSalary;
        this.bonus = "";
        this.punish = "";
        for (BonusEntity b : bonusEntities) {
            this.bonus += b.toString()+"\n";
        }
        for (PunishEntity p : punishEntities) {
            this.punish += p.toString()+"\n";
        }
    }
}
