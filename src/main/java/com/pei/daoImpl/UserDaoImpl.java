package com.pei.daoImpl;

import com.pei.dao.UserDao;
import com.pei.javaBean.User;
import com.pei.util.MyJDBCutil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *用户接口实现类
 */
public class UserDaoImpl implements UserDao {
    private final Class<User> c=User.class;
    @Override
    public ArrayList<User> selectByUsernameAndPassword(String username, String password) {
        String sql="select * from User where username=? and password=?";
        return MyJDBCutil.dql(sql,c, username,password);
    }

    @Override
    public int resetPassword(String username, String userPassword) {
        String sql="update user set password=? where username=?";
        return MyJDBCutil.dml(sql,userPassword,username);
    }

    @Override
    public ArrayList<User> selectMoneyByUsername(String username) {
        String sql="select money from User where username=?";
        return MyJDBCutil.dql(sql, c, username);
    }

    @Override
    public int saveMoneyByUsername(String username, Double saveMoney) {
        String sql="update User set money=money+? where username=?";
        return MyJDBCutil.dml(sql, saveMoney,username);
    }

    @Override
    public int getMoneyByUsername(String username, Double getMoney) {
        String sql="update User set money=money-? where username=?";
        return MyJDBCutil.dml(sql, getMoney,username);
    }

    @Override
    public ArrayList<User> selectInformationByUsername(String username) {
        String sql="select * from User where username=?";
        return MyJDBCutil.dql(sql, c, username);
    }

    @Override
    public boolean sengMoneyByUsername(String username, String reciprocalUserName, Double sendMoney) {
        Connection con = null;
        String sql1="update User set money=money-? where username=?";
        String sql2="update User set money=money+? where username=?";
        try {
            con=MyJDBCutil.getCon();
            MyJDBCutil.dml(con,sql1, sendMoney,username);
            MyJDBCutil.dml(con,sql2,sendMoney,reciprocalUserName);
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public int unsubscribe(String username) {
        String sql="delete from user where username=?";
        return MyJDBCutil.dml(sql,username);
    }

    @Override
    public ArrayList<User> selectAllInformation() {
        String sql="select * from user";
        return MyJDBCutil.dql(sql, c);
    }
}
