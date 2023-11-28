package com.iweb.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 员工惩罚类
 * @author LYH
 * @date 2023/11/26 14:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PunishEntity {
    @ExcelProperty("编号")
    private Integer punishId;
    @ExcelProperty("惩罚内容")
    private String punishMsg;
    @ExcelProperty("惩罚金额")
    private BigDecimal punishMoney;

    @DateTimeFormat("yyyy年MM月dd日")
    @ExcelProperty("惩罚时间")
    private Date punishDate;

    @Override
    public String toString() {
        String date = "";
        if(punishDate != null) {
            date = new SimpleDateFormat("yyyy-MM-dd").format(punishDate);
        }
        return punishId+"     "+ punishMsg+"     "+punishMoney+"     "+date+"\n";
    }
}
