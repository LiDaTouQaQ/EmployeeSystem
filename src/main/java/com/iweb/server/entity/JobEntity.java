package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** 职位类
 * @author LYH
 * @date 2023/11/26 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {
    private Integer jobId;
    private String jobName;
    private Integer jobGrade;
    private BigDecimal salary;

    @Override
    public String toString() {
        String str = jobId+"\t"+jobName+"\t"+jobGrade+"\t"+salary+"\t";
        return str;
    }
}
