package com.iweb.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 员工奖金类
 * @author LYH
 * @date 2023/11/26 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusEntity {
    @ExcelProperty("编号")
    private Integer bonusId;
    @ExcelProperty("奖项名称")
    private String bonusMsg;
    @ExcelProperty("奖项金额")
    private BigDecimal bonusMoney;

    @DateTimeFormat("yyyy年MM月dd日")
    @ExcelProperty("奖项日期")
    private Date bonusDate;

    @Override
    public String toString() {
        String date = "";
        if(bonusDate != null){
            date = new SimpleDateFormat("yyyy-MM-dd").format(bonusDate);
        }
        return bonusId+"     "+ bonusMsg+"     "+bonusMoney+"     "+date+"\n";
    }
}
