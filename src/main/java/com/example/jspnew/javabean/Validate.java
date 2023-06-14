package com.example.jspnew.javabean;

import java.sql.*;

public class Validate {
    private static final String DB_URL = "jdbc:sqlserver://localhost:3066;databaseName=TestSystem;trustServerCertificate=true";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sjk12345";
    private final String Profession;

    public Validate(String Profession){
        this.Profession=Profession;
    }

    public boolean ValidateUser(String UserName,String Password){
        Connection connection = null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            System.out.println("正常连接数据库");
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "";
         sql = "SELECT * FROM  ? WHERE id = ? AND password = ?";
        
        try {

            if (connection != null) {
                statement = connection.prepareStatement(sql);
                statement.setString(1, Profession);
                statement.setString(2, UserName);
                statement.setString(3, Password);
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
}
