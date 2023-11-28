package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.DepartDAO;
import com.iweb.server.entity.DepartEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author LYH
 * @date 2023/11/26 15:35
 */
public class DepartDAOImpl implements DepartDAO {
    @Override
    public DepartEntity getDepart(int departId) {
        String sql = "select * from depart where depart_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        DepartEntity depart = null;
        Connection c = cp.getConnection();
        try (PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,departId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                depart = new DepartEntity(departId,
                        rs.getString("depart_name"),
                        rs.getInt("depart_father_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return depart;
    }

    @Override
    public boolean isExitDepartId(int departId) {
        if(getDepart(departId)==null){
            return false;
        }
        return true;
    }
}
