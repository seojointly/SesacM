package ex02_dml;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ex01_db_connect.DBConnect;

// java.sql.PreparedStatement
// 1. 쿼리문(문자열로 준비)을 실행하는 인터페이스
// 2. 미리 쿼리문을 컴파일 해 둔 뒤, 실행 직전 필요한 값을 전달하는 방식을 사용 
// 3. Placeholder(?) 를 이용한 파라미터 바인딩 지원 (SQL Injection 방지)

// DML (INSERT, UPDATE, DELETE) 실행 경과
// 영향을 받은 행의 갯수 반환 (int) -> 0이면 입력 안된 것.

public class Main {
  public static void insert() throws Exception {

    // 1. 커넥션 받아오기 (접속)
    Connection conn = DBConnect.getConnection(); // 예외: 2개. classnotfound, sql exception

    // 2. 쿼리문 만들기
    // String sql = "INSERT INTO departments (dept_name, location) VALUES ('개발부', '문래동')";

    String sql = "INSERT INTO departments (dept_name, location) VALUES (?, ?)";

    // 3. 쿼리문 실행 객체 받아오기
    PreparedStatement ps = conn.prepareStatement(sql);

    // 3-2. 파라미터 바인딩 (?에 값 채우기): 채울 때 문자열은 작은 따옴표 ('')로 자동 감쌈 처리
    ps.setString(1, "엔지니어부");
    ps.setString(2, "공릉동");

    // 4. 쿼리문 실행하기 (실행 결과는 int type) -> 현재 성공하면 1, 실패하면 0
    int result = ps.executeUpdate(); // insert, update, delete 모두 메서드 동일

    // 5. 결과확인
    System.out.println(result + "행이 등록되었습니다.");
    
    // 6. 자원반납(생성의 역순으로 반납) -> Connection 객체, 실행 객체 (PreparedStatement) 닫기
    if(ps != null) ps.cancel();
    if(conn != null) conn.close();
  }

    public static void update () {

    // Connection conn = DBConnect.getConnection();
    // conn.setAutoCommit(false); // --> 그래서 이걸 수동 커밋하겠다고 넣어줘야함.
    // 2개로 나눈 이유: 이걸로 tranjection하려고  (  // 트랜잭션 코드 추가해서 넣어야함)
    
    // sql1, 2 전부 1개의 작업으로 생각할 것.
    // 그래서 자동 커밋하면 안되고, 수동 커밋으로 변경해야 함. (excuteUpdate X)
  
    // String sql1 = "UPDATE departmens SET dept_name = ? WHERE dept_id = ?";
    // PreparedStatement ps1 = conn.prepareStatement(sql1);
    // ps1.setString(1, "Development");
    // ps1.setInt(2, 5);
    // ps1.executeUpdate(); // 커밋 1 (영구저장)
  
    // String sql2 = "UPDATE departmens SET location = ? WHERE dept_id = ?";
    // PreparedStatement ps2 = conn.prepareStatement(sql2);
    // ps2.setString(1, "Seoul");
    // ps2.setInt(2, 5);
    // ps2.executeUpdate(); // 커밋 2 (영구저장)

    // conn.commit(); // 둘 다 성공했을 때 커밋하겠다는 의미

    Connection conn = null;
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;

    try {
      conn = DBConnect.getConnection();
      conn.setAutoCommit(false); // 현재 커넥션은 수동으로 커밋

      // 트랜잭션의 첫 번째 작업
      String sql1 = "UPDATE departmens SET dept_name = ? WHERE dept_id = ?";
      ps1 = conn.prepareStatement(sql1);
      ps1.setString(1, "Development");
      ps1.setInt(2, 5);
      ps1.executeUpdate(); // 커밋 1 (영구저장)

      // 만약 첫 번째 작업 이후 예외 발생했다면 (네트워크 오류)
      if(Math.random() > 0.0001) { // 강제 발생
        throw new SQLException("네트워크 예외 발생");
      }

      // 트랜잭션 두 번째 작업
      String sql2 = "UPDATE departmens SET location = ? WHERE dept_id = ?";
      ps2 = conn.prepareStatement(sql2);
      ps2.setString(1, "Seoul");
      ps2.setInt(2, 5);
      ps2.executeUpdate(); // 커밋 2 (영구저장)
      
      // 커밋 완료
      conn.commit(); // 둘 다 성공했을 때 커밋하겠다는 의미
      System.out.println("트랜잭션이 성공했습니다.");

    } catch (Exception e) {
      
      // 예외 발생 시 모든 작업 취소
      if(conn != null) {
        try {
          conn.rollback();
          System.err.println("트랜잭션을 롤백했습니다.");
          } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } finally {

      // 자원반납 (권장: try, catch를 각각 사용. 그러나 이번엔 한번만)
      try {
        if (conn != null) {
          conn.setAutoCommit(true); // 기본 세팅으로 변경 , 현 시점에서는 없어도 되는 코드임.
          conn.close();
        }
        if (ps1 != null) ps1.close();
        if (ps2 != null) ps2.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
  }
  
  public static void delete () {

  }

  public static void main(String[] args) {
    try {
      // insert();

    } catch (Exception e) {
      e.printStackTrace();
    }
    update();
  }
}
