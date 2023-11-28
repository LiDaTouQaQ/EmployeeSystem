package com.iweb.server.DAO;

import com.iweb.server.entity.AddressEntity;

/**
 * @author LYH
 * @date 2023/11/26 15:59
 */
public interface AddressDAO {
    AddressEntity getAddress(int addressId);
    int getAddressId(String city);
}
