package com.pei.controller;

import com.pei.javaBean.User;
import com.pei.serviceImpl.MainServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@WebServlet(name = "Login",value = "/login")
public class LoginServlet extends HttpServlet {
    MainServiceImpl mainService = new MainServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        String accountType = req.getParameter("accountType");
        if (user!=null&&accountType.equals("1")){
            req.getRequestDispatcher("WEB-INF/userhome.jsp").forward(req,resp);
        } else if (user!=null&&accountType.equals("2")) {
            req.getRequestDispatcher("WEB-INF/adminhome.jsp").forward(req,resp);
        }else if (user!=null&&accountType.equals("3")){
            req.getSession().invalidate();
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String accountType = req.getParameter("accountType");
        Object loginAccount=null;
        if ("1".equals(accountType)){
            loginAccount= mainService.userLogin(username, password);
            if (loginAccount != null){
                req.getSession().setAttribute("user", loginAccount);
                List<String> userLog=new ArrayList<String>();
                req.getSession().setAttribute("userLog",userLog);
            req.getRequestDispatcher("WEB-INF/userhome.jsp").forward(req,resp);
            }else {
                req.setAttribute("msg", "登陆失败!");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }else if ("2".equals(accountType)){
            loginAccount= mainService.adminLogin(username, password);
            if (loginAccount != null){
                resp.getWriter().write("登陆成功");
            }else {
                req.setAttribute("msg", "登陆失败!");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }
    }
}
