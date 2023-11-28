package com.iweb.server.DAO;

import com.iweb.server.entity.JobEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/26 15:40
 */
public interface JobDAO {
    JobEntity getJob(int jobId);
    boolean isExitJob(int jobId);
    ArrayList<JobEntity> getAllJob();
}
