package com.beta.replyservice;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class RestServiceApplicationTest {

	@Autowired
	private ReplyController controller;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testValidRuleAndString(){
		ReplyMessage reply = controller.replyingV2("12-kbzw9ru");
		assertEquals("5a8973b3b1fafaeaadf10e195c6e1dd4", reply.getMessage());
	}


	@Test
	public void testInvalidRule() {
		ReplyMessage reply = controller.replyingV2("13-kbzw9ru");
		assertEquals("Invalid input", reply.getMessage());
	}
	@Test
	public void testInvalidString() {
		ReplyMessage reply = controller.replyingV2("11-invalid@string");
		assertEquals("Invalid input", reply.getMessage());
	}


}
