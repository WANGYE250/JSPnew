package com.example.jspnew;

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

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Java_Test;trustServerCertificate=true";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sjk12345";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset = UTF-8");
        String action = request.getParameter("action");

        if (action != null && action.equals("login")) {
            System.out.println("login");
            String username = request.getParameter("name");
            String password = request.getParameter("pwd");

            if (validateUser(username, password)) {
                // 用户验证成功，进行相应的操作
                //response.getWriter().println("登录成功");
                PrintWriter out = response.getWriter();

                out.print("登录成功");
            } else {
                // 用户验证失败，显示错误信息
               // response.getWriter().println("登录");
                PrintWriter out = response.getWriter();

                out.println("登录失败");
            }
        } else if (action != null && action.equals("register")) {
            String username = request.getParameter("name");
            String password = request.getParameter("pwd");
            System.out.println("register");

            if (registerUser(username, password)) {
                // 用户注册成功，进行相应的操作
                response.getWriter().println("注册成功");
            } else {
                // 用户注册失败，显示错误信息
                response.getWriter().println("注册失败");
            }
        }
    }

    private boolean validateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("正常连接数据库");
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        try {

            String sql = "SELECT * FROM users WHERE name = ? AND pwd = ?";
            if (connection != null) {
                statement = connection.prepareStatement(sql);
            }
            if (statement != null) {
                statement.setString(1, username);
            }
            if (statement != null) {
                statement.setString(2, password);
            }

            if (statement != null) {
                resultSet = statement.executeQuery();
            }
            if (resultSet != null) {
                return resultSet.next(); // 如果存在匹配的用户记录，则验证成功
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
}
