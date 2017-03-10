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

public class BoardCommandTest {
	
	private Map<String, TicTacToe> testGameMap;
	private SlackInput testSlackInput;
	private final String testChannelID = "C12345";
	private final String testUserId = "@U12345";
	private Command bc;
	private SlackResponse testResponse;
	
	/**
	 * Set up the mocks
	 */
	@Before
	public void setup() {
		testGameMap = new ConcurrentHashMap<String, TicTacToe>();
		testSlackInput = new SlackInput();
		bc = new BoardCommand();		
		testSlackInput.setChannel_id(testChannelID);
		testSlackInput.setUser_id(testUserId);		
	}
	
	@Test
	public void testBoardCommand() {
		String testCommand = "board";
		
		testSlackInput.setText(testCommand);
		testResponse = bc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", LogMessage.getLogMsg(Messages.TTT5000E), testResponse.getText());
		
		testGameMap.put(testChannelID, new TicTacToe("user1", "user2"));
		testResponse = bc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test3 ", Constants.BACK_TICKS + testGameMap.get(testSlackInput.getChannel_id()).displayBoard() + Constants.BACK_TICKS +
				 "\n\n " + LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(testGameMap.get(testSlackInput.getChannel_id()).getCurrentPlayer())), 
				
				testResponse.getText());
		
	}
	

}
