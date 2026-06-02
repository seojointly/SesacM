package ex03_dql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ex01_db_connect.DBConnect;

public class Main {

  // // 부서 목록 반환 메서드
  public static List<Department> findDepartments() throws Exception {
    List<Department> departments = new ArrayList<>();

    Connection conn = DBConnect.getConnection();

    // + 보다 StringBuilder 가 성능이 더 좋음. -> 문자열 '+'는 성능이 안좋음
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT dept_id, ");
    sb.append("dept_name, ");
    sb.append("location ");
    sb.append("FROM departments ");
    sb.append("limit 0, 10");
    String sql = sb.toString();

    PreparedStatement ps = conn.prepareStatement(sql);

    ResultSet rs = ps.executeQuery(); // rs가 가리키고 있는 것은 제목 행임. 그래서 rs.next() 를 해줘야 함. (그러면 다음줄로 넘어가는 것.)

    while (rs.next()) { // 마지막 줄까지 찾기 위해 while 문 사용
      // ResultSet을 DTO로 변환, 꺼내서 Department의 필드값으로 채운 것.
      Department dept = Department.builder() // 이건 build 로 만든 것. setter 로 만들 수도 있음. ((주로 builder 사용))
          .deptId(rs.getInt("dept_id"))
          .deptName(rs.getString("dept_name"))
          .location(rs.getString("location"))
          .build();

      // 하나씩 꺼내. Department에 저장해. 그리고 이걸 departments의 list 에 저장해야함.
      departments.add(dept);
    }

    if (rs != null) rs.close();
    if (ps != null) ps.close();
    if (conn != null) conn.close();

    return departments;
  }

  // 부서 수 반환 메서드
  public static int getDepartmentsCount() throws Exception {
    Connection conn = DBConnect.getConnection();

    String sql = "SELECT COUNT(*) AS dept_count FROM departments";

    PreparedStatement ps = conn.prepareStatement(sql);

    ResultSet rs = ps.executeQuery(); //ResultSet 커서 타입

    // 첫번째 줄을 포인팅 하고, 다음 행으로 내려가는 것.
    
    int deptCount = 0;
    if (rs.next()) {
      deptCount = rs.getInt("dept_count");
      System.out.println(deptCount + "개 부서가 조회되었습니다.");
    }
    if (rs != null) rs.close();
    if (ps != null) ps.close();
    if (conn != null) conn.close();

    return deptCount;
  }
  
  public static void main(String[] args) {

    try {
      int deptCount = getDepartmentsCount();
      System.out.println("받아 온 부서 수: " + deptCount);

      List<Department> departments = findDepartments();
      departments.stream()
        .forEach(d -> System.out.println(d)); // d 라는 파라미터가 메서드에서 사용되는 것이 전부다. 이거 전달 안하기로 짤 수 있음. -> 참조라고 할 수 있어요 . 그래서 그걸 어떻게 하냐면
        // .forEach(System.out::println)
      
      // json으로 객체를 바꿔보자.! (직렬화)
      Gson gson = new Gson();
      String jsonResult = gson.toJson(departments);
      System.out.println(jsonResult);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
