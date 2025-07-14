/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Asus
 */
public class DBContext {
    private static DBContext instance = new DBContext();
    protected Connection connection;

    public static DBContext getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public DBContext() {
        try {
            if (connection == null || connection.isClosed()) {
                String user = "sa";
                String password = "123";
                String url = "jdbc:sqlserver://HOANG_HA:1433;databaseName=QuizPractice;encrypt=true;TrustServerCertificate=true;";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Kết nối thành công đến cơ sở dữ liệu!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy driver SQL Server!");
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
            connection = null;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
            connection = null;
        }
    }

    public static void main(String[] args) {
        DBContext dbContext = DBContext.getInstance();
        Connection conn = dbContext.getConnection();

        if (conn != null) {
            System.out.println("Test kết nối thành công!");
            try {
                conn.close(); // Đóng kết nối sau khi test
                System.out.println("Đóng kết nối thành công!");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        } else {
            System.out.println("Test kết nối thất bại!");
        }
    }
}

