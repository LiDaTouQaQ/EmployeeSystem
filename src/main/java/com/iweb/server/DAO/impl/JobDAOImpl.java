package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.JobDAO;
import com.iweb.server.entity.JobEntity;
import com.iweb.server.service.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/26 15:40
 */
public class JobDAOImpl implements JobDAO {
    @Override
    public JobEntity getJob(int jobId) {
        String sql = "select * from job where job_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        JobEntity job = null;
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,jobId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                job = new JobEntity(jobId,rs.getString("job_name"),
                        rs.getInt("job_grade"),
                        rs.getBigDecimal("salary"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return job;
    }

    @Override
    public boolean isExitJob(int jobId) {
        if (getJob(jobId) == null) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<JobEntity> getAllJob() {
        ArrayList<JobEntity> jobEntities = new ArrayList<>();
        String sql = "select * from job";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                jobEntities.add(new JobEntity(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBigDecimal(4)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return jobEntities.isEmpty()?null:jobEntities;
    }
}
