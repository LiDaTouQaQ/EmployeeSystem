package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 14:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckWorkEntity {
    private Integer cwId;
    private Integer empId;
    private Date checkDate;
    private Integer checkState;
}
