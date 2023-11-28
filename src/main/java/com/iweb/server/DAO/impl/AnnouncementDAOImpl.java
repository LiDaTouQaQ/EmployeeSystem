package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.AnnouncementDAO;
import com.iweb.server.entity.AnnouncementEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/27 15:52
 */
public class AnnouncementDAOImpl implements AnnouncementDAO {
    @Override
    public ArrayList<AnnouncementEntity> getAllAnnouncement() {
        ArrayList<AnnouncementEntity> arrayList = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        String sql = "select * from announcement order by a_date desc";
        try(PreparedStatement ps = c.prepareStatement(sql)){
            arrayList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                arrayList.add(new AnnouncementEntity(rs.getInt("a_id"),
                        new Date(rs.getDate("a_date").getTime()),
                        rs.getString("a_object"),
                        rs.getString("a_msg"),
                        rs.getInt("o_id")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return arrayList;
    }

    @Override
    public ArrayList<AnnouncementEntity> getAnnouncementToEmployee(int empId) {
        ArrayList<AnnouncementEntity> arrayList = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        String sql = "select * from announcement where (o_id=? and a_object='E') or a_object in ('C','D') order by a_date desc";
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            arrayList = new ArrayList<>();
            while (rs.next()){
                arrayList.add(new AnnouncementEntity(rs.getInt("a_id"),
                        new Date(rs.getDate("a_date").getTime()),
                        rs.getString("a_object"),
                        rs.getString("a_msg"),
                        rs.getInt("o_id")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return arrayList;
    }

    @Override
    public void addAnnouncement(int empId, String aObject, String aMsg) {
        String sql = "insert into announcement(a_date,a_object,a_msg,o_id) values(?,?,?,?)";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try(PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,sdf.format(new Date()));
            ps.setString(2,aObject);
            ps.setString(3,aMsg);
            ps.setInt(4,empId);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
    }
}
