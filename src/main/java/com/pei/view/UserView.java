package com.pei.view;

import com.pei.javaBean.User;
import com.pei.service.MainService;
import com.pei.serviceImpl.MainServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 *用户页面
 */
public class UserView {
    public static void userView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MainService mainService=new MainServiceImpl();
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名:");
        String userName=sc.next();
        System.out.println("请输入密码:");
        String userPassword=sc.next();
        User user = mainService.userLogin(userName, userPassword);
        if (user==null){
            System.out.println("登陆失败 用户名或密码错误");
        }else {
            System.out.println("欢迎! "+user.getName()+"当前时间:"+dateFormat.format(new Date()));
            ArrayList<String> log=new ArrayList<>();
            log.add(dateFormat.format(new Date())+":用户"+user.getName()+"登录   余额:"+mainService.selectMoneyByUsername(user.getUsername()));
            while (true){
                System.out.println("用户登录");
                System.out.println("请输入选择 1-修改密码 2-查询个人信息 3-查询余额 4-存款 5- 取款 6转账 7-查询明细");
                int choose= sc.nextInt();
                if (choose==1){
                    System.out.println("请输入新密码");
                    String newPassword= sc.next();
                    int i = mainService.resetPassword(user.getUsername(), newPassword);
                    System.out.println("修改成功");
                }else if (choose==2){
                    System.out.println("当前用户为普通用户");
                    System.out.println("用户名:"+user.getName()+"电话:"+user.getPhone()+"邮箱:"+user.getMail()+"身份证号:"+user.getIdCard());
                }else if (choose==3){
                    System.out.println("当前账户余额为:"+mainService.selectMoneyByUsername(user.getUsername()));
                }else if (choose==4){
                    System.out.println("请输入要存入的金额:");
                    double saveMoney= sc.nextDouble();
                    Double balance = mainService.saveMoneyByUsername(user.getUsername(), saveMoney);
                    System.out.println("存入"+saveMoney+"成功！ 当前余额为:"+balance);
                    log.add(dateFormat.format(new Date())+":用户"+user.getName()+"存入了"+saveMoney+"元"+" 余额:"+mainService.selectMoneyByUsername(user.getUsername()));                }else if (choose==5){
                    System.out.println("请输入要取出的金额:");
                    double getMoney= sc.nextDouble();
                    Double balance = mainService.getMoneyByUsername(user.getUsername(), getMoney);
                    if (balance!=null) {
                        System.out.println("取出" + getMoney + "成功！ 当前余额为:" + balance);
                        log.add(dateFormat.format(new Date())+":用户"+user.getName()+"取出了"+getMoney+"元"+" 余额:"+mainService.selectMoneyByUsername(user.getUsername()));
                    }
                    else {
                        System.out.println("取出" + getMoney + "失败! 账户余额不足");
                    }
                }else if (choose==6){
                    //转账功能
                    System.out.println("请输入转账对方的用户名:");
                    String reciprocalUserName=sc.next();
                    User reciprocalUser = mainService.selectByUserName(reciprocalUserName);
                    if (reciprocalUser==null){
                        System.out.println("此用户不存在");
                    }else {
                        System.out.println("请确认收款的用户名:"+reciprocalUserName);
                        System.out.println("y/n");
                        String choose0=sc.next();
                        if (Objects.equals(choose0, "y")){
                            System.out.println("请输入转账金额:");
                            Double sendMoney=sc.nextDouble();
                            if (sendMoney>mainService.selectMoneyByUsername(user.getUsername())){
                                System.out.println("当前余额不足!");
                            }else {
                                mainService.sendMoneyByUserName(user.getUsername(), reciprocalUserName, sendMoney);
                                System.out.println("向"+reciprocalUserName+"转账:"+sendMoney+"成功!");
                                log.add(dateFormat.format(new Date())+":用户"+user.getName()+"向"+reciprocalUserName+"转账"+sendMoney+"元"+" 余额:"+mainService.selectMoneyByUsername(user.getUsername()));
                            }
                        }
                    }

                }else if (choose==7){
                    for (String str:
                         log) {
                        System.out.println(str);
                    }
                    break;
                } else System.out.println("请输入正确数字");
            }
        }
    }
}
