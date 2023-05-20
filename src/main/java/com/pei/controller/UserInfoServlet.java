package com.pei.controller;

import com.pei.javaBean.User;
import com.pei.serviceImpl.MainServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
    MainServiceImpl mainService = new MainServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        if (user != null) {
            User user1 = mainService.selectByUserName(user.getUsername());
            req.getSession().setAttribute("user", user1);
            req.getRequestDispatcher("WEB-INF/userInfo.jsp").forward(req, resp);
        }else {
            resp.getWriter().write("<script>alert(\"登陆失效!请重新登录\")</script>");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
