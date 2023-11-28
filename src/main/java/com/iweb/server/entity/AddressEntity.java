package com.iweb.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LYH
 * @date 2023/11/26 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    private Integer addressId;
    private String city;
    private String province;
}
