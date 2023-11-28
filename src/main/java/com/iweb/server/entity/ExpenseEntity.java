package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 14:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {
    private Integer expId;
    private Integer inEmpId;
    private Integer outEmpId;
    private String expContent;
    private BigDecimal expMoney;
    private Date expDate;
    private boolean pass;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = expId+"\t"+inEmpId+"\t"+outEmpId+"\t"+expContent+"\t"+expMoney+"\t"+
                sdf.format(expDate)+"\t"+(pass?"已通过":"未通过");
        return str;
    }
}
