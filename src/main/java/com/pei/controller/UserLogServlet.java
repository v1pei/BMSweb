package com.pei.controller;

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
@WebServlet("/userLog")
public class UserLogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       List<String> userLogs = (ArrayList<String>)req.getSession().getAttribute("userLog");
       if (userLogs.size()==0){
           req.setAttribute("logMsg", "没有找到日志信息！");
       }else {
           req.removeAttribute("userMsg");
       }
        req.getRequestDispatcher("WEB-INF/userLog.jsp").forward(req,resp);
    }
}
