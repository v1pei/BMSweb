package com.pei.view;

import com.pei.javaBean.Admin;
import com.pei.javaBean.User;
import com.pei.service.MainService;
import com.pei.serviceImpl.MainServiceImpl;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *管理员页面
 */
public class AdminView {

    public static void adminView() {
        MainService mainService=new MainServiceImpl();
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名:");
        String userName=sc.next();
        System.out.println("请输入密码:");
        String userPassword=sc.next();
        Admin admin = mainService.adminLogin(userName, userPassword);
        if (admin==null){
            System.out.println("登陆失败 用户名或密码错误");
        }else {
            System.out.println("欢迎! "+admin.getName());
            while (true){
                System.out.println("管理员登录");
                System.out.println("请输入选择1-创建账号 2-销户 3-修改密码 4查询 5-退出");
                int choose= sc.nextInt();
                if (choose==1){
                   createAccount();
                }else if (choose==2){
                    System.out.println("请输入销户的类型1-管理员 2-用户");
                    int choose2=sc.nextInt();
                    if (choose2==1){
                        System.out.println("请输入要销户的管理员用户名:");
                        String username=sc.next();
                        if (username.equals(admin.getUsername())){
                            System.out.println("不能注销当前用户!");
                        }else if (mainService.selectAdminByUserName(username)==null){
                            System.out.println("没有找到该用户!");
                        }else {
                            if (mainService.adminUnsubscribe(username)){
                                System.out.println("管理员"+username+"已被注销!");
                            }else {
                                System.out.println("销户失败!");
                            }
                        }
                    }else if (choose2==2){
                        System.out.println("请输入要销户的用户名:");
                        String username=sc.next();
                        if (username.equals(admin.getUsername())){
                            System.out.println("不能注销当前用户!");
                        }else if (mainService.selectAdminByUserName(username)==null){
                            System.out.println("没有找到该用户!");
                        }else {
                            if (mainService.userUnsubscribe(username)){
                                System.out.println("用户"+username+"已被注销!");
                            }else {
                                System.out.println("销户失败!");
                            }
                        }
                    }else System.out.println("错误的数字");
                }else if (choose==3){
                    System.out.println("请输入要修改的账户类型1-当前账户 2-客户账户");
                    int choose3=sc.nextInt();
                    if (choose3==1){
                        System.out.println("请输入新密码:");
                        String password=sc.next();
                        if (mainService.resetAdminPassword(userName, password)){
                            System.out.println("修改成功!");
                        }else {
                            System.out.println("修改失败!");
                        }
                    }else if (choose3==2){
                        System.out.println("请输入要修改的客户用户名:");
                        String username=sc.next();
                        if (mainService.selectByUserName(username)==null){
                            System.out.println("该用户不存在!");
                        }else {
                            System.out.println("请输入新密码:");
                            String password=sc.next();
                            int i = mainService.resetPassword(username, password);
                            if (i==1) System.out.println("修改成功!");
                            else System.out.println("修改失败！");
                        }
                    }else System.out.println("错误的数字!");
                }else if (choose==4){
                    System.out.println("请输入查询内容1->当前所有管理员(部分)信息 2->客户所有信息");
                    int choose4=sc.nextInt();
                    if (choose4==1){
                        ArrayList<Admin> admins = mainService.selectAdminInformation();
                        System.out.println("ID\t用户名\t姓名\t\t\t\t邮箱\t\t\t\t手机号");
                        for (Admin amn:
                             admins) {
                            System.out.println(amn.getAid()+"\t"+amn.getUsername()+"\t"+amn.getName()+"\t\t"+amn.getMail()+"\t\t"+amn.getPhone());
                        }
                    }else if (choose4==2){
                        ArrayList<User> users = mainService.selectUserInformation();
                        System.out.println("ID\t\t用户名\t\t密码\t\t\t姓名\t\t手机号\t\t\t\t邮箱\t\t\t\t\t\t身份证号\t\t\t\t\t\t\t余额");
                        for (User user:
                                users) {
                            System.out.println(user.getUid()+"\t\t"+user.getUsername()+"\t\t"+user.getPassword()+"\t\t"+user.getName()+"\t\t"+user.getPhone()+"\t\t\t"+user.getMail()+"\t\t\t"+user.getIdCard()+"\t\t\t\t"+user.getMoney());
                        }
                    }else System.out.println("无效的数字!");
                }else if (choose==5){
                    break;
                }else System.out.println("请输入正确数字");
            }
        }
    }
    public static void createAccount(){
        MainService mainService=new MainServiceImpl();
        Scanner sc=new Scanner(System.in);
        System.out.println("选择创建账号类型 1-管理员 2-用户");
        int choose1= sc.nextInt();
        if (choose1==1){
            System.out.println("请输入用户名:");
            String username=sc.next();
            if (mainService.selectByUserName(username)!=null){
                System.out.println("此管理员用户已存在!");
                return;
            }
            System.out.println("请输入密码:");
            String password= sc.next();
            System.out.println("请输入真实姓名:");
            String name=sc.next();
            System.out.println("请输入手机号:");
            String phone= sc.next();
            System.out.println("请输入邮箱:");
            String mail= sc.next();
            if (mainService.createAdminAccount(username, password, name, phone, mail)){
                System.out.println("添加成功!");
            }else {
                System.out.println("添加失败!");
            }
        }else if (choose1==2){
            System.out.println("请输入用户名:");
            String username=sc.next();
            if (mainService.selectByUserName(username)!=null){
                System.out.println("此用户名已存在");
                return;
            }
            System.out.println("请输入密码:");
            String password= sc.next();
            System.out.println("请输入真实姓名:");
            String name=sc.next();
            System.out.println("请输入手机号:");
            String phone= sc.next();
            System.out.println("请输入邮箱:");
            String mail= sc.next();
            System.out.println("请输入身份证号:");
            String idCard=sc.next();
            if (mainService.createUserAccount(username, password, name, phone, mail,idCard)){
                System.out.println("添加成功!");
            }else {
                System.out.println("添加失败!");
            }
        }else System.out.println("错误的选项");
    }
}
