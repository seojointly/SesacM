drop database if exists member_db;
create schema if not exists member_db;
--  보통 drop, create 는 별도로 저장. (schema.sql)로 저장

use member_db;

drop table if exists visit_history;
Drop Table if exists member_db.members;

create table members (
    mem_id char(8) not null primary key,
    mem_name varchar(10) not null,
    mem_number tinyint not null,
    addr char(2) not null,
    phone1 char (3),
    phone2 char(8),
    height tinyint unsigned,
    debut_date date -- datetime 으로 바꾸면 시간 등록 가능함.
);

CREATE TABLE visit_history (
    visit_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    mem_id CHAR(8),
    visited_at DATE,
    FOREIGN KEY (mem_id)
        REFERENCES members (mem_id)
        ON DELETE SET NULL
);

-- show tables;

-- 실습
create database if not exists db_ddb;

create table if not exists db_ddb.customers (
    cust_id int primary key auto_increment,
    cust_name varchar(30) not null,
    phone varchar(30) unique, 
    age smallint check(age between 0 and 100),
    join_dt date default (current_date)
);
drop table db_ddb.customers;
drop database db_ddb;
