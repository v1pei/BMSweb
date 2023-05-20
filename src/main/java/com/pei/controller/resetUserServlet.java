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
@WebServlet("/resetUser")
public class resetUserServlet extends HttpServlet {
    MainServiceImpl mainService = new MainServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/userPassword.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        if (newPassword.equals(confirmPassword)) {
            User user = (User)req.getSession().getAttribute("user");
            if (oldPassword.equals(user.getPassword())){
            mainService.resetPassword(user.getUsername(), newPassword);
            req.setAttribute("passwordMsg","修改成功!");
                User user1 = mainService.selectByUserName(user.getUsername());
                req.getSession().setAttribute("user", user1);
            req.getRequestDispatcher("WEB-INF/userPassword.jsp").forward(req,resp);
            }else {
                req.setAttribute("passwordMsg1","原密码错误!");
                req.getRequestDispatcher("WEB-INF/userPassword.jsp").forward(req,resp);
            }
        }else {
            req.setAttribute("passwordMsg2","两次输入的密码不一致!");
            req.getRequestDispatcher("WEB-INF/userPassword.jsp").forward(req,resp);
        }
    }
}
