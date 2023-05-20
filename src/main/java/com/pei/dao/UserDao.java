package com.pei.dao;

import com.pei.javaBean.User;

import java.util.ArrayList;

/**
 * 用户类
 */
public interface UserDao {
    /**
     * 通过用户名密码查询信息
     * @param username 用户名
     * @param password 密码
     * @return 用户集合
     */
    public ArrayList<User> selectByUsernameAndPassword(String username, String password);

    /**
     * 通过用户名新密码重设密码
     * @param username
     * @param userPassword
     * @return
     */
    public int resetPassword(String username, String userPassword);

    /**
     * 查询用户余额
     * @param username
     * @return
     */

    public ArrayList<User> selectMoneyByUsername(String username);

    /**
     * 存钱
     * @param username
     * @param saveMoney
     * @return
     */
    public int saveMoneyByUsername(String username,Double saveMoney);

    /**
     * 取钱
     * @param username
     * @param getMoney
     * @return
     */
    public int getMoneyByUsername(String username,Double getMoney);

    /**
     * 查询用户部分信息
     * @param username
     * @return
     */
    public ArrayList<User> selectInformationByUsername(String username);

    /**
     * 向指定用户转账指定金额
     * @param username
     * @param reciprocalUserName
     * @param sendMoney
     * @return
     */
    public boolean sengMoneyByUsername(String username,String reciprocalUserName,Double sendMoney);

    /**
     * 删除指定用户
     * @param username
     * @return
     */
    public int unsubscribe(String username);

    /**
     * 查找用户所有信息
     * @return
     */
    public ArrayList<User> selectAllInformation();
}
