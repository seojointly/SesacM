-- schema.sql (테이블 정의)
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, -- DB 자동생성
  email VARCHAR(100) NOT NULL UNIQUE, -- java 생성 필요
  nickname VARCHAR(50) NOT NULL, -- java 생성 필요
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP --java에서는 localdatetime, timestamp 2가지가 있음. -- DB 자동생성
);

CREATE TABLE IF NOT EXISTS posts (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, -- DB 자동생성
  user_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL, -- VARCHER 최대값: 255
  content TEXT,-- 글자만 넣는다는 가정 하에 적용
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, --java에서는 localdatetime, timestamp 2가지가 있음. -- DB 자동생성
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
  -- 수정일자도 있으면 좋음
);