package com.pei.javaBean;

/**
 *用户类
 */
public class User {
    private int uid;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String mail;
    private String idCard;
    private double money;

    public User() {
    }

    public User(int uid, String username, String password, String name, String phone, String mail, String idCard, double money) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.idCard = idCard;
        this.money = money;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", idCard='" + idCard + '\'' +
                ", money=" + money +
                '}';
    }

}
