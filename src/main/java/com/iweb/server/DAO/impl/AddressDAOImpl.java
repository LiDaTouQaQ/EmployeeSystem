package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.AddressDAO;
import com.iweb.server.entity.AddressEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author LYH
 * @date 2023/11/26 16:00
 */
public class AddressDAOImpl implements AddressDAO {
    @Override
    public AddressEntity getAddress(int addressId) {
        String sql = "select * from address where address_id=?";
        AddressEntity address = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1,addressId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                address = new AddressEntity(addressId,
                        rs.getString("city"),
                        rs.getString("province"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return address;
    }

    @Override
    public int getAddressId(String city) {
        String sql = "select address_id from address where city=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        int addressId = -1;
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,city);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                addressId = rs.getInt("address_id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return addressId;
    }
}
