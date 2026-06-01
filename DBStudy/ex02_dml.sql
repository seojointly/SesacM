use member_db;

-- INSERT
-- now(), sysdate() = 함수임. 현재 시간을 표시해주는 것.
insert into members (mem_id, mem_name, mem_number, addr, phone1, phone2, height, debut_date)
values ('12345678', '홍길동', 1, 'KR', '010', '00001111', 100, now());


insert into visit_history (mem_id, visited_at)
values ('12345678', now());

-- UPDATE
update members
set mem_name = '김철수', phone2 = '98765432'
where mem_id = '12345678'; -- 조건은 PK를 사용할 것.

-- DELETE
delete from members
where mem_id = '12345678';

delete from visit_history
where visit_id = '1';