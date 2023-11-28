package com.iweb.server.DAO;

import com.iweb.server.entity.PunishEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/26 16:10
 */
public interface PunishDAO {
    ArrayList<PunishEntity> getPunish(int empId);
    ArrayList<PunishEntity> getAllPunishMsg();
    boolean addPunish(int punishId,int empId);
}
