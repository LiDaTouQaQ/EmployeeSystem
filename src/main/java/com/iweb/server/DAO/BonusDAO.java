package com.iweb.server.DAO;

import com.iweb.server.entity.BonusEntity;

import java.util.ArrayList;

/**
 * @author LYH
 * @date 2023/11/26 16:09
 */
public interface BonusDAO {
    ArrayList<BonusEntity> getBonus(int empId);
    ArrayList<BonusEntity> getAllBonus();
    boolean addBonus(int bonusId,int empId);
}
