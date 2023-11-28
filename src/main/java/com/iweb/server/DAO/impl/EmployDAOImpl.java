package com.iweb.server.DAO.impl;

import com.iweb.server.DAO.*;
import com.iweb.server.entity.*;
import com.iweb.server.service.ConnectionPool;
import com.iweb.server.view.MainView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author LYH
 * @date 2023/11/26 15:08
 */
public class EmployDAOImpl implements EmployDAO {

    @Override
    public boolean isExitEmpId(int empId) {
        String sql = "select count(*) from employee_info where emp_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int num = rs.getInt("count(*)");
                if(num == 1){
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

    @Override
    public EmployeeInfoEntity getEmploy(String empId, String password) {
        String sql = "select i.*,c.job_id,c.depart_id,c.work_state,c.sup_id from employee_info i join employeeincompany c on i.emp_id=c.emp_id  " +
                "where i.emp_id=? and password=?";
        EmployeeInfoEntity employeeInfo = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try (PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,empId);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                employeeInfo = new EmployeeInfoEntity();
                employeeInfo.setEmpId(Integer.parseInt(empId));
                employeeInfo.setEmpName(rs.getString("i.emp_name"));
                employeeInfo.setPhone(rs.getString("i.phone"));
                AddressDAO addressDAO = new AddressDAOImpl();
                AddressEntity address = addressDAO.getAddress(rs.getInt("i.address_id"));
                if(address != null){
                    employeeInfo.setAddress(address);
                }
                employeeInfo.setAddressDetail(rs.getString("i.address_detail"));
                employeeInfo.setCard(rs.getString("i.card"));
                employeeInfo.setHiredate(rs.getDate("i.hiredate"));
                employeeInfo.setPassword(password);
                employeeInfo.setWorkState(rs.getInt("c.work_state"));
                employeeInfo.setSupId(rs.getInt("c.sup_id"));
                JobDAO jobDAO = new JobDAOImpl();
                JobEntity job = jobDAO.getJob(rs.getInt("c.job_id"));
                if(job!=null){
                    employeeInfo.setJob(job);
                }
                DepartDAO departDAO = new DepartDAOImpl();
                DepartEntity depart = departDAO.getDepart(rs.getInt("c.depart_id"));
                if(depart!=null){
                    employeeInfo.setDepart(depart);
                }
                BonusDAO bonusDAO = new BonusDAOImpl();
                ArrayList<BonusEntity> bonusEntities = bonusDAO.getBonus(Integer.parseInt(empId));
                if(bonusEntities!=null){
                    employeeInfo.setBonusEntities(bonusEntities);
                }
                PunishDAO punishDAO = new PunishDAOImpl();
                ArrayList<PunishEntity> punishEntities = punishDAO.getPunish(Integer.parseInt(empId));
                if(punishEntities!=null){
                    employeeInfo.setPunishEntities(punishEntities);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return employeeInfo;
    }

    @Override
    public void updateEmploy(EmployeeInfoEntity employeeInfo) {
        String sql = "update employee_info set emp_name=?,phone=?,address_detail=?,card=?,password=? where emp_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,employeeInfo.getEmpName());
            ps.setString(2,employeeInfo.getPhone());
            ps.setString(3,employeeInfo.getAddressDetail());
            ps.setString(4,employeeInfo.getCard());
            ps.setString(5,employeeInfo.getPassword());
            ps.setInt(6,employeeInfo.getEmpId());
            int num = ps.executeUpdate();
            if(num==1){
                System.out.println("修改成功");
            }else{
                System.out.println("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
    }

    @Override
    public int getEmployIdByName(String name) {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        int id = -1;
        String sql = "select emp_id from employee_info where emp_name=?";
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt("emp_id");
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return id;
    }

    @Override
    public boolean addEmployee(String supName,String empName, String phone, String city, String addressDetail, String card, int jobId, int departId) {
        String sql = "insert into employee_info(emp_name,phone,address_id,address_detail,card,hiredate) " +
                "values(?,?,?,?,?,?);";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DepartDAO departDAO = new DepartDAOImpl();
        JobDAO jobDAO = new JobDAOImpl();
        int empId = -1;
        int sup_id = getEmployIdByName(supName);
        if(!departDAO.isExitDepartId(departId) | !jobDAO.isExitJob(jobId) | sup_id==-1){
            MainView.log("输入的部门,职位,领导姓名出错");
            return false;
        }
        String sql1 = "insert into employeeincompany(emp_id,depart_id,job_id,sup_id)" +
                "values(?,?,?,?)";
        AddressDAO addressDAO = new AddressDAOImpl();
        int addressId = addressDAO.getAddressId(city);
        if(addressId == -1){
            System.out.println("地址有误");
            return false;
        }
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,empName);
            ps.setString(2,phone);
            ps.setInt(3,addressId);
            ps.setString(4,addressDetail);
            ps.setString(5,card);
            ps.setString(6,sdf.format(new Date()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                empId = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try(PreparedStatement ps = c.prepareStatement(sql1)) {
            ps.setInt(1,empId);
            ps.setInt(2,departId);
            ps.setInt(3,jobId);
            ps.setInt(4,sup_id);
            ps.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }

    @Override
    public boolean fireEmployee(int empId) {
        String sql = "update employeeincompany set work_state=0 where emp_id=?";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,empId);
            int num = ps.executeUpdate();
            if(num == 1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return false;
    }

    @Override
    public ArrayList<EmployeeInfoEntity> getAllWorkingEmployee() {
        String sql = "select i.emp_id,password from employee_info i join employeeincompany c on i.emp_id=c.emp_id" +
                " where c.work_state=1";
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection c = cp.getConnection();
        ArrayList<EmployeeInfoEntity> arrayList = new ArrayList<>();
        String empId = "";
        String password = "";
        try(PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                empId = rs.getString("i.emp_id");
                password = rs.getString("password");
                arrayList.add(getEmploy(empId,password));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cp.returnConnection(c);
        }
        return arrayList.isEmpty() ? null : arrayList;
    }
}
