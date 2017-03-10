package com.slack.tictactoe.commands;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import com.slack.tictactoe.common.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

public class MoveCommandTest {

	private Map<String, TicTacToe> testGameMap;
	private SlackInput testSlackInput;
	private final String testChannelID = "C12345";
	private final String testUserId = "@U12345";
	private Command mc;
	private SlackResponse testResponse;
	
	/**
	 * Set up the mocks
	 */
	@Before
	public void setup() {
		testGameMap = new ConcurrentHashMap<String, TicTacToe>();
		testSlackInput = new SlackInput();
		mc = new MoveCommand();		
		testSlackInput.setChannel_id(testChannelID);
		testSlackInput.setUser_id(testUserId);		
	}
	
	/**
	 * Test invalid and valid input params
	 */
	@Test
	public void testInputParams() {
		String testCommand = "move";
		testSlackInput.setText(testCommand);
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test1 ", LogMessage.getLogMsg(Messages.TTT5001E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 1 2");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", LogMessage.getLogMsg(Messages.TTT5000E), testResponse.getText());
		
		testGameMap.put(testChannelID, new TicTacToe("user1", "user2"));
		testSlackInput.setUser_id("user3");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test3 ", LogMessage.getLogMsg(Messages.TTT5004E), testResponse.getText());
		
		testSlackInput.setUser_id("user2");		
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test4 ", LogMessage.getLogMsg(Messages.TTT5005E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " * &");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test5 ", LogMessage.getLogMsg(Messages.TTT5006E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 3 0");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", LogMessage.getLogMsg(Messages.TTT6000E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 0 0");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test7 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS + "\n\n" +
				LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 0 0");
		testSlackInput.setUser_id("user2");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", LogMessage.getLogMsg(Messages.TTT6001E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 0 1");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", LogMessage.getLogMsg(Messages.TTT5005E), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 0 1");
		testSlackInput.setUser_id("user2");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS + "\n\n" +
				LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), testResponse.getText());
		
		
		testSlackInput.setText(testCommand + " 1 1");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS + "\n\n" +
				LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 1 2");
		testSlackInput.setUser_id("user2");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test6 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS + "\n\n" +
				LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), testResponse.getText());
		
		testSlackInput.setText(testCommand + " 2 2");
		testSlackInput.setUser_id("user1");
		testResponse = mc.processCommand(testSlackInput, testGameMap);
//		assertEquals("Test6 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS + "\n\n" +
//				LogMessage.getLogMsg(Messages.TTT5010I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), testResponse.getText());
		
	}
}
