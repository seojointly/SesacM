package com.example.jpa;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex07_jpql.Department;
import com.example.jpa.ex07_jpql.Employee;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@SpringBootTest
class Ex07Jpql {

  // 엔티티 매니저 (영속성 컨텍스트 관리자)
  private EntityManager em;

  // 엔티티 트랜잭션
  private EntityTransaction tx;

  // 테스트 시작 전 엔티티 매니저를 만들기 위해 팩토리(공장)부터 지어둠
  @BeforeAll
  static void setUpBeforeClass() {
    JpaUtil.initFactory();
  }

  // 각 테스트 시작 전 엔티티 매니저를 생성
  @BeforeEach
  void setUp() {
    em = JpaUtil.getEntityManager();
    tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함
    tx.begin();
  }

  // 각 테스트 종료 후 엔티티 메니저를 닫아줌
  @AfterEach
  void tearDown() {
    if (tx != null && tx.isActive()) {
      tx.rollback();
    }
    if (em != null && em.isOpen()) {
      em.close();
    }
  }

  // 전체 테스트 종료 후 엔티티 매니저 팩토리를 닫아줌
  @AfterAll
  static void tearDownAfterClass() {
    JpaUtil.closeFactory();
  }

  // 이제부터 테스트 진행
  @Test // [1]
  @DisplayName("반환 타입이 Query인 JPQL")
  void queryTest() {
    // 부서 및 사원 등록
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica", 5000);
    Employee emp2 = new Employee("tom", 4000);

    // 부서에 사원 등록하기
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // emp1.getDepartment() = dept;

    // 사원에 부서 정보 등록하기
    emp1.setDepertment(dept);
    emp2.setDepertment(dept);

    // 영속화 (영속성 전이): (cascade 없이, 직접 다 데리고 가기)
    em.persist(dept); //cascade있으면 이것만 해줘도 됨.
    em.persist(emp1);
    em.persist(emp2);
    
    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // find를 DB로부터 조회하기 위해 영속화된 Entity를 준 영속상태로 변경한 것이었음. (JPQL은 필요하지 않음)9
    em.clear();

    Query query = em.createQuery("select e.name, e.salary from Employee e"); // JPQL - 반드시 별명 줘야 함.
    /*
    * 이름과 급여를 합쳐서 Object로 반환. 
    * 근데 2개잖아. 그래서 배열로 해야 함
    * => Object Array가 여러가지 나오는 것. 
    */
    List<Object[]> results = query.getResultList();
    results.stream().forEach(obj -> {
      Object[] row = (Object[])obj;
      System.out.println("이름: " + row[0] + "급여: " + row[1]);
    });
  }


  @Test // [2]
  @DisplayName("반환 타입이 TypedQuery인 JPQL") 
  void TypedQueryTest() {
    TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class); // 타입이 전환된 쿼리 (TypedQuery<Employee> 로 지정됨. )
    List<Employee> employees = query.getResultList();
    employees.stream().forEach(emp -> System.out.println("Name: " + emp.getName()));
  }

  
  @Test // [3]
  @DisplayName("N + 1 문제 JPQL")
  void nPlusOneTest() {
    
    // 부서 및 사원 등록
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica", 5000);
    Employee emp2 = new Employee("tom", 4000);

    // 부서에 사원 등록하기
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // emp1.getDepartment() = dept;

    // 사원에 부서 정보 등록하기
    emp1.setDepertment(dept);
    emp2.setDepertment(dept);

    // 영속화 (영속성 전이): (cascade 없이, 직접 다 데리고 가기)
    em.persist(dept); //cascade있으면 이것만 해줘도 됨.
    em.persist(emp1);
    em.persist(emp2);
    
    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // find를 DB로부터 조회하기 위해 영속화된 Entity를 준 영속상태로 변경한 것이었음. (JPQL은 필요하지 않음)9
    em.clear();

    // 사원 조회 쿼리 (1)
    TypedQuery<Employee> query = em.createQuery("select e from Employee e", Employee.class);
    List<Employee> employees = query.getResultList();

    // 사원마다 부서를 조회하는 쿼리 (N)
    for(Employee emp : employees) {
      System.out.println("Department Name: " + emp.getDepartment().getDeptName());
    /*
    * 사원 조회, <사원 목록 class>, 사원마다 쿼리확인 (총 3번) -> N + 1 조회 상태, 해결하려면 Join 사용
    * 지연 로딩의 경우 proxy객체로 채워서 가져옴 => N+1
    */
    }
  }


  @Test // [4]
  @DisplayName("N + 1 문제 해결 JPQL") //지연로딩때문에 생기는
  void fetchJoinTest() {
    // 부서 및 사원 등록
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica", 5000);
    Employee emp2 = new Employee("tom", 4000);

    // 부서에 사원 등록하기
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // emp1.getDepartment() = dept;

    // 사원에 부서 정보 등록하기
    emp1.setDepertment(dept);
    emp2.setDepertment(dept);

    // 영속화 (영속성 전이): (cascade 없이, 직접 다 데리고 가기)
    em.persist(dept); //cascade있으면 이것만 해줘도 됨.
    em.persist(emp1);
    em.persist(emp2);
    
    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // find를 DB로부터 조회하기 위해 영속화된 Entity를 준 영속상태로 변경한 것이었음. (JPQL은 필요하지 않음)9
    em.clear();

    String jpql = "select e from Employee e join fetch e.department";

    List<Employee> employees = em.createQuery(jpql, Employee.class).getResultList();
    for(Employee emp : employees) {
      System.out.println("Department Name: " + emp.getDepartment().getDeptName());
    }
  }
}