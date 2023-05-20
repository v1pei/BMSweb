package com.pei.controller;

import com.pei.javaBean.User;
import com.pei.serviceImpl.MainServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
@WebServlet("/userGet")
public class userGetServlet extends HttpServlet {
    MainServiceImpl mainService = new MainServiceImpl();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/userGetMoney.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Double getMoney = Double.valueOf(req.getParameter("getMoney"));
        User user = (User)req.getSession().getAttribute("user");
        if (getMoney>user.getMoney()){
            req.setAttribute("getMsg","余额不足!");
            req.getRequestDispatcher("WEB-INF/userGetMoney.jsp").forward(req,resp);
        }else {
            Double newMoney = mainService.getMoneyByUsername(user.getUsername(), getMoney);
            user.setMoney(newMoney);
            req.getSession().setAttribute("user",user);
            req.setAttribute("getMsg","取款成功!");
            ArrayList<String> userLog =(ArrayList<String>) req.getSession().getAttribute("userLog");
            userLog.add(dateFormat.format(new Date())+":用户"+user.getName()+"取出了"+newMoney+"元"+" 余额:"+newMoney);
            req.getRequestDispatcher("WEB-INF/userGetMoney.jsp").forward(req,resp);
        }

    }
}
