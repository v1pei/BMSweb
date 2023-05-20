package com.pei.view;

import com.pei.service.MainService;
import com.pei.serviceImpl.MainServiceImpl;

import java.util.Scanner;

/**
 *主页面
 */
public class MainView {
    public static void main(String[] args) {
        MainService mainService=new MainServiceImpl();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("欢迎登录银行管理系统 请输入选择 1-登录 2-忘记密码");
            int choose1 = sc.nextInt();
            if (choose1 == 1) {
                System.out.println("请选择登录账号类型 1-管理员 2-用户");
                int choose2 = sc.nextInt();
                if (choose2 == 1) AdminView.adminView();
                else if (choose2 == 2) UserView.userView();
                else System.out.println("没有该类型");
                break;
            } else if (choose1 == 2) {
                System.out.println("请输入账号");
                String username= sc.next();
                System.out.println("验证码已发送至邮箱");
                String pwd = mainService.forgetPassword(username);
                System.out.println("请输入收到的验证码");
                String uPwd=sc.next();
                if (pwd.equals(uPwd)){
                    System.out.println("请输入设置的新密码:");
                    String password=sc.next();
                    int i = mainService.resetPassword(username,password);//重置密码
                    if (i==1) {
                        System.out.println("修改成功");
                    }else System.out.println("修改失败");
                    continue;
                }else {
                    System.out.println("验证码错误 正在返回主页");
                    continue;
                }

            } else {
                System.out.println("请输入正确的数字");
            }
        }
    }
}
