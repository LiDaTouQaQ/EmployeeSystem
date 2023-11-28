package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 部门类
 * @author LYH
 * @date 2023/11/26 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartEntity {
    private Integer departId;
    private String departName;
    private Integer departSon;
}
