package com.iweb.server.DAO;

import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/27 20:49
 */
public interface CheckWorkDAO {
    boolean addCheckWork(int empId);
    boolean isChecking(int empId, Date date);
}
