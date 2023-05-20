package com.pei.dao;

import com.pei.javaBean.Admin;

import java.util.ArrayList;

/**
 * 管理员接口
 */
public interface AdminDao {
    /**
     * 通过用户名和密码查询信息
     * @param username
     * @param password
     * @return
     */
    public ArrayList<Admin> selectByUsernameAndPassword(String username, String password);

    /**
     * 创建管理员账户
     * @param username
     * @param password
     * @param name
     * @param phone
     * @param mail
     * @return
     */
    public int createAdminAccount(String username,String password,String name,String phone,String mail);

    /**
     * 创建客户账户
     * @param username
     * @param password
     * @param name
     * @param phone
     * @param mail
     * @param idCard
     * @return
     */
    public int createUserAccount(String username,String password,String name,String phone,String mail,String idCard);

    /**
     * 查询管理员信息
     * @param username
     * @return
     */
    public ArrayList<Admin> selectInformationByUsername(String username);

    /**
     * 删除指定管理员用户
     * @param username
     * @return
     */
    public int unsubscribe(String username);

    /**
     * 重置管理员密码
     * @param username
     * @param password
     * @return
     */
    public int resetPasswordByUsername(String username,String password);

    /**
     * 查询管理员部分信息
     * @return
     */
    public ArrayList<Admin> selectSomeInformation();
}
