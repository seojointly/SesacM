DROP DATABASE IF EXISTS company_db;
CREATE DATABASE IF NOT EXISTS company_db;

USE company_db;

DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

CREATE TABLE IF NOT EXISTS departments
(
    dept_id     INT NOT NULL AUTO_INCREMENT COMMENT '부서아이디',
    dept_name   VARCHAR(30) COMMENT '부서명',
    location    VARCHAR(50) COMMENT '위치',
    CONSTRAINT pk_dept PRIMARY KEY(dept_id)
) ENGINE=InnoDB COMMENT '부서';

CREATE TABLE IF NOT EXISTS employees
(
    emp_id      INT NOT NULL AUTO_INCREMENT COMMENT '사원아이디',
    dept_id     INT COMMENT '부서아이디',
    emp_name    VARCHAR(15) COMMENT '사원명',
    position    CHAR(10) COMMENT '직급',
    gender      CHAR(1) COMMENT '성별',
    hire_date   DATE COMMENT '입사일자',
    salary      INT COMMENT '연봉',
    CONSTRAINT pk_emp PRIMARY KEY(emp_id),
    CONSTRAINT fk_dept_emp FOREIGN KEY(dept_id) 
      REFERENCES departments(dept_id)
) ENGINE=InnoDB COMMENT '사원';

ALTER TABLE employees AUTO_INCREMENT = 1001;

INSERT INTO departments(dept_name, location) VALUES ('영업부', '대구');
INSERT INTO departments(dept_name, location) VALUES ('인사부', '서울');
INSERT INTO departments(dept_name, location) VALUES ('총무부', '대구');
INSERT INTO departments(dept_name, location) VALUES ('기획부', '서울');

INSERT INTO employees VALUES (NULL, 1, '구창민', '과장', 'M', '95-05-01', 5000000);
INSERT INTO employees VALUES (NULL, 1, '김민서', '사원', 'M', '17-09-01', 2500000);
INSERT INTO employees VALUES (NULL, 2, '이은영', '부장', 'F', '90-09-01', 5500000);
INSERT INTO employees VALUES (NULL, 2, '한성일', '과장', 'M', '93-04-01', 5000000);


