use company_db;

-- 내부조인: 
-- 사원아이디, 사원명, 부서명 조회하기
SELECT e.emp_id, e.emp_name, d.dept_name
FROM departments d
INNER JOIN employees e
ON d.dept_id = e.dept_id;

SELECT e.emp_id AS a
, e.emp_name AS b
, d.dept_name AS c
FROM departments d
INNER JOIN employees e
ON d.dept_id = e.dept_id;

-- '대구'에 근무하는 사원 조회하기
select e.emp_name
from employees e
join departments d
on d.dept_id = e.dept_id
where d.location =  '대구';

-- 지역별로 근무 중인 사원 수 조회하기
SELECT d.location, COUNT(*) AS emp_count
from employees e
join departments d
on d.dept_id = e.dept_id
group by d.location;
-- 지역별로 사원 이름 나열해서 확인
SELECT d.location, e.emp_name
FROM employees e
JOIN departments d 
ON d.dept_id = e.dept_id
ORDER BY d.location;

-- ---------------------------------------------
-- 외부 조인: 두 테이블 중 한 곳에만 있는 데이터도 함께 조회하기
-- 사원아이디, 사원명, 부서명 조회하기(근무 중인 사원이 없는 부서도 함께 조회하기)
SELECT e.emp_id, e.emp_name, d.dept_name
FROM departments d
LEFT OUTER JOIN employees e
ON d.dept_id = e.dept_id;

-- 부서별 사원 수 조회하기 (근무 중인 사원이 없으면 0으로 조회)
-- -> * 로 넣으면 안됨, 외부조인은 특히 안됨
select d.dept_name, count(emp_id)
from departments d
left outer join employees e
on d.dept_id = e.dept_id
group by d.dept_id, d.dept_name;

-- ---------------------------------------------

-- 서브 쿼리
-- 중첩 서브쿼리 (결과가 1개인 단일 행 서브쿼리)

SELECT * 
FROM employees 
WHERE salary > (SELECT AVG(salary) 
                FROM employees);

-- 영업부에 근무하는 사원 조회
-- 원래 다중행 서브쿼리라서 '=' 쓰면 안되고, in 을 써야 한다. (근데 여기는 '='도 가능. 영업부가 1개라서)
select *
from employees
where dept_id in (select dept_id 
                from departments 
                where dept_name = '영업부');
-- 에러 확인 -> Error Code: 1242. Subquery returns more than 1 row	0.016 sec

select *
from employees
where dept_id = (select dept_id 
                from departments 
                where dept_name = '영업부' or dept_name = '인사부');
