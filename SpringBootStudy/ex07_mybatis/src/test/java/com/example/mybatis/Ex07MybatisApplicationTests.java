package com.example.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mybatis.mapper.PostMapper;


//  이걸로 DB단 Test를 하는 것.
@SpringBootTest
class Ex07MybatisApplicationTests {
	
	@Autowired
	PostMapper postMapper;

	@Test
	void contextLoads() {
		assertEquals(3, postMapper.countAll());
	}

}
