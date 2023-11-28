package com.iweb.server.DAO;

import com.iweb.server.entity.AnnouncementEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/27 15:50
 */
public interface AnnouncementDAO {
    ArrayList<AnnouncementEntity> getAllAnnouncement();
    ArrayList<AnnouncementEntity> getAnnouncementToEmployee(int empId);

    void addAnnouncement(int empId,String aObject,String aMsg);
}
