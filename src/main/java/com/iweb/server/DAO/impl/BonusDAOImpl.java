package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.BonusDAO;
import com.iweb.server.entity.BonusEntity;
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
 * @date 2023/11/26 16:11
 */
public class BonusDAOImpl implements BonusDAO {
    @Override
    public ArrayList<BonusEntity> getBonus(int empId) {
        String sql = "select b.bonus_id,b.bonus_date,i.bonus_name,i.bonus_money from bonus b join bonus_info i on b.bonus_info_id=i.bonus_info_id" +
                " where b.emp_id=?";
        ArrayList<BonusEntity> bonusEntities = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Date date = new Date(rs.getDate("b.bonus_date").getTime());
                bonusEntities.add(new BonusEntity(rs.getInt("b.bonus_id"),
                        rs.getString("i.bonus_name"),
                        rs.getBigDecimal("i.bonus_money"),
                        date));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return bonusEntities;
    }

    @Override
    public ArrayList<BonusEntity> getAllBonus() {
        ArrayList<BonusEntity> bonusEntities = new ArrayList<>();
        String sql = "select * from bonus_info";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                bonusEntities.add(new BonusEntity(rs.getInt("bonus_info_id"),
                        rs.getString("bonus_name"),
                        rs.getBigDecimal("bonus_money"),
                        null));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return bonusEntities.isEmpty()?null:bonusEntities;
    }

    @Override
    public boolean addBonus(int bonusId,int empId) {
        String sql = "insert into bonus(bonus_info_id,bonus_date,emp_id) " +
                "values(?,?,?);";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try(PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,bonusId);
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
