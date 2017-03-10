package com.slack.tictactoe.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.tictactoe.Constants;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.responses.EphemeralResponse;

/**
 * JUnit Tests for TicTacToeService.java
 *
 */
public class TicTacToeServiceTest {
	private TicTacToeService tttService = new TicTacToeService();
	private Response result;
	private String invalidToken = "1232324";
	
	@Test
	public void testHelloWorld(){
		Response hwResult = tttService.helloWorld();
		assertEquals("Hello World!",  Response.Status.OK.getStatusCode(), hwResult.getStatus());
		
	}
	
	@Test
	public void testPost() {

		@SuppressWarnings("unchecked")
		MultivaluedMap<String, String> map=Mockito.mock(MultivaluedMap.class);
		Mockito.when(map.getFirst(Constants.TOKEN)).thenReturn(null);
		assertNull(map.getFirst(Constants.TOKEN));
		
		List<String> value = new ArrayList<String>();
		value.add(invalidToken);
		Mockito.when(map.get(Constants.TOKEN)).thenReturn(value);
		result = tttService.processTTTCommand(map);		
		assertNotNull(result);
		assertEquals("Should return 200", 200, result.getStatus());
		Object errMsg = result.getEntity();
		JsonParser parser = new JsonParser();
		JsonObject resObj = parser.parse(errMsg.toString()).getAsJsonObject();
		System.out.println(resObj.get("text").getAsString());
		System.out.println(new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT4001E)));
		assertEquals("", LogMessage.getLogMsg(Messages.TTT4001E), resObj.get("text").getAsString());
		
		
	}
}
