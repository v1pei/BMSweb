package com.pei.daoImpl;

import com.pei.dao.AdminDao;
import com.pei.javaBean.Admin;
import com.pei.util.MyJDBCutil;

import java.util.ArrayList;

/**
 *管理员接口实现类
 */
public class AdminDaoImpl implements AdminDao {
    private final Class<Admin> c=Admin.class;
    @Override
    public ArrayList<Admin> selectByUsernameAndPassword(String username, String password) {
        String sql="select * from Admin where username=? and password=?";
        return MyJDBCutil.dql(sql, c,username,password);
    }

    @Override
    public int createAdminAccount(String username, String password, String name, String phone, String mail) {
        String sql="insert into admin(username, password, name, phone, mail) VALUE (?,?,?,?,?);";
        return MyJDBCutil.dml(sql, username,password,name,phone,mail);
    }

    @Override
    public int createUserAccount(String username, String password, String name, String phone, String mail,String idCard) {
        String sql="insert into user(username, password, name, phone, mail,idCard) VALUE (?,?,?,?,?,?);";
        return MyJDBCutil.dml(sql, username,password,name,phone,mail,idCard);
    }
    @Override
    public ArrayList<Admin> selectInformationByUsername(String username) {
        String sql="select name,phone,mail from admin where username=?";
        return MyJDBCutil.dql(sql, c, username);
    }

    @Override
    public int unsubscribe(String username) {
        String sql="delete from admin where username=?";
        return MyJDBCutil.dml(sql,username);
    }

    @Override
    public int resetPasswordByUsername(String username,String password) {
        String sql="update admin set password=? where username=?";
        return MyJDBCutil.dml(sql,password,username);
    }

    @Override
    public ArrayList<Admin> selectSomeInformation() {
        String sql="select aid,username,name,mail,phone from Admin";
        return MyJDBCutil.dql(sql, c);
    }
}
