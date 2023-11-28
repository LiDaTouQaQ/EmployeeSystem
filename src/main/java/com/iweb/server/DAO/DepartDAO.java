package com.iweb.server.DAO;

import com.iweb.server.entity.DepartEntity;

/**
 * @author LYH
 * @date 2023/11/26 15:32
 */
public interface DepartDAO {
    DepartEntity getDepart(int departId);
    boolean isExitDepartId(int departId);
}
