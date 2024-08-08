package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // 데이터베이스 URL
        String user = "scott"; // 데이터베이스 사용자 이름
        String password = "tiger"; // 데이터베이스 비밀번호

        try {
            // Oracle JDBC 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // 데이터베이스 연결
            Connection connection = DriverManager.getConnection(url, user, password);
            
            if (connection != null) {
                System.out.println("연결 성공!");
                connection.close();
            } else {
                System.out.println("연결 실패!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("연결 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
