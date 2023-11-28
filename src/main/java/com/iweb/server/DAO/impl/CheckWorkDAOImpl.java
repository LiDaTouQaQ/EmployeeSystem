package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.CheckWorkDAO;
import com.iweb.server.DAO.PunishDAO;
import com.iweb.server.entity.CheckWorkEntity;
import com.iweb.server.service.ConnectionPool;
import com.iweb.server.view.MainView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/27 20:50
 */
public class CheckWorkDAOImpl implements CheckWorkDAO {
    PunishDAO punishDAO = new PunishDAOImpl();
    @Override
    public boolean addCheckWork(int empId) {
        CheckWorkEntity checkWork = new CheckWorkEntity();
        String sql = "insert into check_work(emp_id,check_date,check_state) values(?,?,?)";
        ConnectionPool cp = ConnectionPool.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ps.setString(2, sdf.format(new Date()));
            if(new Date().getHours()>=9){
                MainView.log("迟到拉");
                punishDAO.addPunish(2,empId);
                ps.setInt(3,-1);
            }else{
                ps.setInt(3,1);
            }
            int num = ps.executeUpdate();
            if(num == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }

    @Override
    public boolean isChecking(int empId, Date date) {
        String sql = "select count(*) from check_work where emp_id=? and DATE(check_date)=DATE(?)";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ps.setString(2, sdf.format(date));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int num = rs.getInt("count(*)");
                if(num >= 1 ){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }
}
