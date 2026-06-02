package ex01_db_connect;

import java.sql.Connection;

public class Main {
  public static void main(String[] args) {
    try {
      Connection conn = DBConnect.getConnection();
      System.out.println("DB 접속 성공");
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("DB 연결 실패");
    }
  }
}
