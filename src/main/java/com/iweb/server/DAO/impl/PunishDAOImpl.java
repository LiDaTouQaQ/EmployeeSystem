package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.PunishDAO;
import com.iweb.server.entity.PunishEntity;
import com.iweb.server.entity.PunishEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 16:19
 */
public class PunishDAOImpl implements PunishDAO {
    @Override
    public ArrayList<PunishEntity> getPunish(int empId) {
        String sql = "select p.punish_id,p.punish_date,i.punish_name,i.punish_money from punish p join punish_info i on p.punish_info_id=i.punish_info_id" +
                " where p.emp_id=?";
        ArrayList<PunishEntity> punishEntities = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Date date = new Date(rs.getDate("p.punish_date").getTime());
                punishEntities.add(new PunishEntity(rs.getInt("p.punish_id"),
                        rs.getString("i.punish_name"),
                        rs.getBigDecimal("i.punish_money"),
                        date));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return punishEntities;
    }

    @Override
    public ArrayList<PunishEntity> getAllPunishMsg() {
        ArrayList<PunishEntity> punishEntities = new ArrayList<>();
        String sql = "select * from punish_info";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                punishEntities.add(new PunishEntity(rs.getInt("punish_info_id"),
                        rs.getString("punish_name"),
                        rs.getBigDecimal("punish_money"),
                        null));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return punishEntities.isEmpty()?null:punishEntities;
    }

    @Override
    public boolean addPunish(int punishId, int empId) {
        String sql = "insert into punish(punish_info_id,punish_date,emp_id) " +
                "values(?,?,?);";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try(PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,punishId);
            ps.setString(2,sdf.format(new Date()));
            ps.setInt(3,empId);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                if(rs.getInt(1)>0){
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
