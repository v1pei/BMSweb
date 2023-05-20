package com.pei.controller;

import com.pei.javaBean.User;
import com.pei.serviceImpl.MainServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
@WebServlet("/usersave")
public class userSaveServlet extends HttpServlet {
    MainServiceImpl mainService = new MainServiceImpl();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/userSaveMoney.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Double saveMoney = Double.valueOf(req.getParameter("saveMoney"));
        User user = (User)req.getSession().getAttribute("user");
        Double aDouble = mainService.saveMoneyByUsername(user.getUsername(), saveMoney);
        user.setMoney(aDouble);
        req.getSession().setAttribute("user",user);
        req.setAttribute("saveMsg","存入成功!");
        ArrayList<String> userLog =(ArrayList<String>) req.getSession().getAttribute("userLog");
        userLog.add(dateFormat.format(new Date())+":用户"+user.getName()+"存入了"+saveMoney+"元"+" 余额:"+aDouble);
        req.getRequestDispatcher("WEB-INF/userSaveMoney.jsp").forward(req,resp);
    }
}
