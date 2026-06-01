-- SELECT 절만 필수
select 1+1; -- 확인 가능, orcale 의 경우 from절까지 필수. mySQL은 select 만 필수
select NOW();

select now() as 지금; --  Java에서 인식하는 이름 = 지금 이 되는 것. (중복되는 tbl의 경우 as 로 이름 변경. X시 충돌)

-- FROM 절 : 테이블 조회
SELECT * FROM members; -- * 실무 사용 금지.alter

-- 테이블 별명 (주로 join/sub query에서 필요)
SELECT m.mem_id, m.mem_name
FROM members m;

-- 중복 제거
select distinct addr from members;

-- Where 절: 조건 작성 (조건절 column 다름)
select mem_id, mem_name
from members
where mem_id = 12345678;

select mem_id, mem_name
from members
where mem_name = '홍길동';

-- ---------------------------------------------

-- WHERE절 실습
select * from departments;
select * from employees;

-- 1. 대구에 있는 부서 조회하기
select dept_name, location
from departments
where location = '대구';

-- 2. 부서번호 = 1, 급여 = 3,000,000 이상인 사원 조회
select dept_id, emp_name, salary
from employees
where dept_id = 1 and salary >= 3000000;

-- 3. 급여가 3백만 ~ 5백만 사이인 사원 조회하기
select emp_name, salary
from employees
where salary between 3000000 and 5000000;

-- 4. 직급이 '과장', '부장'인 사원 조회하기
select emp_name, position
from employees
where position in('과장','부장');

-- 5. 직급이 '과장', '부장'이 아닌 사원 조회하기
select emp_name, position
from employees
where position not in('과장', '부장');

-- 6. 이름이 '한'으로 사작하는 사원 조회하기
select emp_name
from employees
where emp_name like('한%');

select emp_name
from employees
where emp_name like concat('한', '%');

-- where emp_name like concat('%', '한', '%'); -- 편한 concat, concat의 파라미터 개수가 2개로 제한되는 경우가 있음. 그럴 땐 concat을 2개사용할 것.
-- where emp_name like concat('%', concat('한', '%')); -- mysql은 필요없음. (DB벤더마다 다름)

-- ---------------------------------------------

-- GROUP BY절 / HAVING절
SELECT position, avg(salary)
FROM employees
GROUP BY position;

-- 부서별 사원 수 조회하기
select dept_id, count(*) -- 모든 칼럼 중 어느 한 컬럼이라도 값을 가지고 있으면 개수에 포함
from employees
group by dept_id;

-- 직급이 '과장'인 사원 수 조회하기
select position, count(*)
from employees
where position = '과장'
group by position;

-- 급여 평균이 500만 이상인 직급, 급여 평균 조회하기
select position, avg(salary) as salary_avg
from employees
group by position
having salary_avg >= 5000000; -- MySQL은 예외적으로 SELECT절의 별명 사용 가능

-- ---------------------------------------------

-- ORDER BY절 + LIMIT절
--  1. 높은 급여 순
select emp_id, dept_id, emp_name, position, gender, hire_date, salary
from employees
order by salary desc; -- salary 변수처리, desc 변수처리 (전부 파라미터 처리해야함)

-- 2. 가장 급여가 높은 사원
select emp_id, dept_id, emp_name, position, gender, hire_date, salary
from employees
order by salary desc
limit 1; -- 기존은 0, 1로 작성 (limit이 걸리지 않은 order by는 이상한 order by)
