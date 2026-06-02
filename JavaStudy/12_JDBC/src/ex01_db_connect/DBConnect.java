package ex01_db_connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 1. java.sql.Connection
// 1) DB와의 연결(세션)을 관리하는 인터페이스
// 2) DB 연결, 트랜잭션 관리 (commit(), rollback(), setAutoCommit(boolean))등

// 2. java.sql.DriverManager
// 1) JDBC 드라이버를 관리하는 클래스
// 2) URL, USER, PASSWORD를 이용해 커넥션을 반환하는 기능

public class DBConnect {

  // 클래스 메서드 (커넥션 반환)
  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    // 드라이버 클래스 로드
    Class.forName("com.mysql.cj.jdbc.Driver");

    // 커넥션 받아 오기 (localhost 대신, 127.0.0.1 사용 가능)
    Connection conn = DriverManager.getConnection(
      "jdbc:mysql://127.0.0.1:3306/company_db?serverTimezone=UTC&characterEncoding=UTF-8", 
      "---", 
      "---");

    // 커넥션 반환
    return conn;
  }
}
