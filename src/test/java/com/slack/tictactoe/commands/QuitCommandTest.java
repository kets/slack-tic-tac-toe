package com.slack.tictactoe.commands;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

public class QuitCommandTest {
	private Map<String, TicTacToe> testGameMap;
	private SlackInput testSlackInput;
	private final String testChannelID = "C12345";
	private final String testUserId = "@U12345";
	private Command qc;
	private SlackResponse testResponse;
	
	/**
	 * Set up the mocks
	 */
	@Before
	public void setup() {
		testGameMap = new ConcurrentHashMap<String, TicTacToe>();
		testSlackInput = new SlackInput();
		qc = new QuitCommand();		
		testSlackInput.setChannel_id(testChannelID);
		testSlackInput.setUser_id(testUserId);		
	}
	
	@Test
	public void quitCommandTest() {
		String testCommand = "quit";
		
		testSlackInput.setText(testCommand);
		testResponse = qc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", LogMessage.getLogMsg(Messages.TTT5000E), testResponse.getText());
		
		testGameMap.put(testChannelID, new TicTacToe("user1", "user2"));
		testSlackInput.setUser_id("user3");
		testResponse = qc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", LogMessage.getLogMsg(Messages.TTT5020E), testResponse.getText());
		
		testSlackInput.setUser_id("user2");
		testResponse = qc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test3 ", LogMessage.getLogMsg(Messages.TTT5012I, 
				TTTUtils.formatUserId(testSlackInput.getUser_id())), testResponse.getText());
		
		
	}

}
