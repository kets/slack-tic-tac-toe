package com.slack.tictactoe.commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;


/**
 * JUnit tests for play command
 *
 */
public class PlayCommandTest {
	private Map<String, TicTacToe> testGameMap;
	private SlackInput testSlackInput;
	private final String testChannelID = "C12345";
	private final String testUserId = "@U12345";
	private Command pc;
	private SlackResponse testResponse;
	
	/**
	 * Set up the mocks
	 */
	@Before
	public void setup() {
		testGameMap = new ConcurrentHashMap<String, TicTacToe>();
		testSlackInput = new SlackInput();
		pc = new PlayCommand();		
		testSlackInput.setChannel_id(testChannelID);
		testSlackInput.setUser_id(testUserId);		
	}
	
	/**
	 * Test invalid and valid input params
	 */
	@Test
	public void testInputParams() {
		String testCommand = "play";
		testSlackInput.setText(testCommand);
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test1 ", LogMessage.getLogMsg(Messages.TTT5001E), testResponse.getText());
		
		testSlackInput.setText(testCommand + "user");
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", LogMessage.getLogMsg(Messages.TTT5001E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " user");
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test3 ", LogMessage.getLogMsg(Messages.TTT5003E), testResponse.getText());
		
		testGameMap.put(testChannelID, new TicTacToe("user1", "user2"));
		testSlackInput.setText(testCommand + " <@U4BJG47DG|oxo>");
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		System.out.println("ERROR " + LogMessage.getLogMsg(Messages.TTT5002E, TTTUtils.formatUserId("user1"), 
				TTTUtils.formatUserId("user2")));
		assertEquals("Test4 ", LogMessage.getLogMsg(Messages.TTT5002E, TTTUtils.formatUserId("user1"), 
				TTTUtils.formatUserId("user2")), testResponse.getText());
		testGameMap.remove(testChannelID);
		
		testSlackInput.setText(testCommand + " <@U4BJG47DG|oxo>");
		testSlackInput.setUser_id("U4BJG47DG");
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test5 ", LogMessage.getLogMsg(Messages.TTT5008E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " <@U4BJG47DE|test_user>");
		testResponse = pc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", LogMessage.getLogMsg(Messages.TTT5013I, TTTUtils.formatUserId(testSlackInput.getUser_id()),
				TTTUtils.formatUserId("U4BJG47DE")), testResponse.getText());
		
	}
	
}
