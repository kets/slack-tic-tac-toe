package com.slack.tictactoe.service;

import static org.junit.Assert.assertEquals;
import javax.ws.rs.core.Response;
import org.junit.Test;

/**
 * JUnit Tests for TicTacToeService.java
 *
 */
public class TicTacToeServiceTest {

	@Test
	public void testHelloWorld(){
		TicTacToeService tttService = new TicTacToeService();
		Response hwResult = tttService.helloWorld();
		assertEquals("Hello World!",  Response.Status.OK.getStatusCode(), hwResult.getStatus());
		
	}
}
