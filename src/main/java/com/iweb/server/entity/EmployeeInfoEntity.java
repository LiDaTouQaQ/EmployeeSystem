package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 13:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoEntity {
    private Integer empId;
    private String empName;
    private String phone;
    private AddressEntity address;
    private String addressDetail;
    private String card;
    private Date hiredate;
    private String password;
    private JobEntity job;
    private DepartEntity depart;
    private Integer supId;
    private Integer workState;
    private ArrayList<BonusEntity> bonusEntities;
    private ArrayList<PunishEntity> punishEntities;
}
