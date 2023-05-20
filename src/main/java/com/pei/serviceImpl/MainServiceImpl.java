package com.pei.serviceImpl;

import com.pei.dao.AdminDao;
import com.pei.dao.UserDao;
import com.pei.daoImpl.AdminDaoImpl;
import com.pei.daoImpl.UserDaoImpl;
import com.pei.javaBean.Admin;
import com.pei.javaBean.User;
import com.pei.service.MainService;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *主服务实现类
 */
public class MainServiceImpl implements MainService {
    AdminDao adminDao=new AdminDaoImpl();
    UserDao userDao=new UserDaoImpl();
    @Override
    public User userLogin(String username, String password) {
        ArrayList<User> users = userDao.selectByUsernameAndPassword(username, password);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }

    @Override
    public Double selectMoneyByUsername(String username) {
        ArrayList<User> users = userDao.selectMoneyByUsername(username);
        if (users.size()==1) {
            return users.get(0).getMoney();
        }
        return null;
    }

    @Override
    public Admin adminLogin(String username, String password) {
        ArrayList<Admin> admins = adminDao.selectByUsernameAndPassword(username, password);
        if (admins.size()==1){
            return admins.get(0);
        }
        return null;
    }

    @Override
    public int resetPassword(String username, String userPassword) {
        return userDao.resetPassword(username, userPassword);
    }

    @Override
    public String forgetPassword(String username) {
        Random ran=new Random();
        Scanner sc=new Scanner(System.in);
        StringBuilder pwd= new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pwd.append(ran.nextInt(10));
        }
        //模拟邮箱发送
        System.out.println("(邮箱)验证码为:"+pwd);
        return pwd.toString();
    }

    @Override
    public Double saveMoneyByUsername(String username, Double saveMoney) {
        userDao.saveMoneyByUsername(username, saveMoney);
        return selectMoneyByUsername(username);
    }

    @Override
    public Double getMoneyByUsername(String username, Double getMoney) {
        userDao.getMoneyByUsername(username, getMoney);
        if (selectMoneyByUsername(username)<0){
            saveMoneyByUsername(username, getMoney);
            return null;
        }
        return selectMoneyByUsername(username);
    }

    @Override
    public User selectByUserName(String username) {
        ArrayList<User> users = userDao.selectInformationByUsername(username);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }

    @Override
    public Admin selectAdminByUserName(String username) {
        ArrayList<Admin> Admin = adminDao.selectInformationByUsername(username);
        if (Admin.size()==1){
            return Admin.get(0);
        }
        return null;
    }

    @Override
    public boolean sendMoneyByUserName(String username, String reciprocalUserName, Double sendMoney) {
        return userDao.sengMoneyByUsername(username,reciprocalUserName ,sendMoney );
    }

    @Override
    public boolean createAdminAccount(String username, String password, String name, String phone, String mail) {
        int adminAccount = adminDao.createAdminAccount(username, password, name, phone, mail);
        return adminAccount == 1;
    }

    @Override
    public boolean createUserAccount(String username, String password, String name, String phone, String mail, String idCard) {
        int userAccount = adminDao.createUserAccount(username, password, name, phone, mail,idCard);
        return userAccount == 1;
    }

    @Override
    public boolean userUnsubscribe(String username) {
        int userAccount = userDao.unsubscribe(username);
        return userAccount == 1;
    }

    @Override
    public boolean adminUnsubscribe(String username) {
        int userAccount = adminDao.unsubscribe(username);
        return userAccount == 1;
    }

    @Override
    public boolean resetAdminPassword(String username, String password) {
        int userAccount = adminDao.resetPasswordByUsername(username,password);
        return userAccount == 1;
    }

    @Override
    public ArrayList<Admin> selectAdminInformation() {
        return adminDao.selectSomeInformation();
    }

    @Override
    public ArrayList<User> selectUserInformation() {
        return userDao.selectAllInformation();
    }
}
