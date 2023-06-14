package com.example.jspnew.servlet;

import com.example.jspnew.javabean.Validate;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", value = "/Administrator/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:sqlserver://localhost:3066;databaseName=TestSystem;trustServerCertificate=true";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sjk12345";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset = UTF-8");
        String action = request.getParameter("action");
        Validate validate=new Validate(action);

        if (action != null) {
            System.out.println("用户登录:"+action);
            String username = request.getParameter("id");
            String password = request.getParameter("pwd");

            if (validate.ValidateUser(username, password)) {
                // 用户验证成功，进行相应的操作
                //response.getWriter().println("登录成功");
                PrintWriter out = response.getWriter();
                out.print("登录成功");
                int type=validate.getProfessionNum();
                switch (type){
                    case 1->{
                        RequestDispatcher dispatcher=request.getRequestDispatcher("AdminMain.jsp");
                        dispatcher.forward(request,response);
                    }case 2->{
                        RequestDispatcher dispatcher=request.getRequestDispatcher("AdminMain.jsp");
                        dispatcher.forward(request,response);
                    }case 3->{
                        RequestDispatcher dispatcher=request.getRequestDispatcher("AdminMain.jsp");
                        dispatcher.forward(request,response);
                    }
                }

            } else {
                // 用户验证失败，显示错误信息
               // response.getWriter().println("登录");
                PrintWriter out = response.getWriter();

                out.println("登录失败");
            }
        }
    }


    private boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "INSERT INTO users (name, pwd) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // 如果有行受到影响，则注册成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void dispatch(String pro){
        Validate validate=new Validate(pro);

    }
}
