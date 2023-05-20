package com.pei.service;

import com.pei.javaBean.Admin;
import com.pei.javaBean.User;

import java.util.ArrayList;

/**
 * 主模块服务接口
 */
public interface MainService {
    /**
     * 用户登录服务
     * @param username
     * @param password
     * @return
     */
    public User userLogin(String username,String password);

    /**
     * 通过用户名查询余额
     * @param username
     * @return 余额
     */
    public Double selectMoneyByUsername(String username);

    /**
     * 管理员登录服务
     * @param username
     * @param password
     * @return
     */
    public Admin adminLogin(String username, String password);

    /**
     * 根据用户名重置密码
     * @param username
     * @return
     */
    public int resetPassword(String username,String userPassword);

    /**
     * 忘记密码
     * @param username
     * @return 验证码
     */
    public String forgetPassword(String username);

    /**
     * 通过用户名存入
     * @param username
     * @return
     */
    public Double saveMoneyByUsername(String username,Double saveMoney);

    /**
     * 通过用户名取出
     * @param username
     * @param getMoney
     * @return 余额
     */
    public Double getMoneyByUsername(String username,Double getMoney);

    /**
     * 通过用户名查询除账号密码外信息
     * @param username
     * @return
     */
    public User selectByUserName(String username);

    /**
     * 通过用户名查找管理员用户
     * @param username
     * @return
     */
    public Admin selectAdminByUserName(String username);

    /**
     * 通过用户名实现转账
     * @param username
     * @param reciprocalUserName
     * @param sendMoney
     * @return
     */
    public boolean sendMoneyByUserName(String username,String reciprocalUserName,Double sendMoney);

    /**
     * 创建管理员用户
     * @param username
     * @param password
     * @param name
     * @param phone
     * @param mail
     * @return
     */
    public boolean createAdminAccount(String username,String password,String name,String phone,String mail);

    /**
     * 创建用户账户
     * @param username
     * @param password
     * @param name
     * @param phone
     * @param mail
     * @return
     */
    public boolean createUserAccount(String username,String password,String name,String phone,String mail,String idCard);

    /**
     * 删除用户账户
     * @param username
     * @return
     */
    public boolean userUnsubscribe(String username);

    /**
     * 删除管理员账户
     * @param username
     * @return
     */
    public boolean adminUnsubscribe(String username);

    /**
     * 修改管理员密码
     * @param username
     * @param password
     * @return
     */
    public boolean resetAdminPassword(String username,String password);

    /**
     * 查询管理员部分信息
     * @return
     */
    public ArrayList<Admin> selectAdminInformation();

    /**
     * 查询用户所有信息
     * @return
     */
    public ArrayList<User> selectUserInformation();
}
