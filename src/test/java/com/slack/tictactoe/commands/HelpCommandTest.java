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

public class HelpCommandTest {
	private Map<String, TicTacToe> testGameMap;
	private SlackInput testSlackInput;
	private final String testChannelID = "C12345";
	private final String testUserId = "@U12345";
	private Command hc;
	private SlackResponse testResponse;
	
	/**
	 * Set up the mocks
	 */
	@Before
	public void setup() {
		testGameMap = new ConcurrentHashMap<String, TicTacToe>();
		testSlackInput = new SlackInput();
		hc = new HelpCommand();		
		testSlackInput.setChannel_id(testChannelID);
		testSlackInput.setUser_id(testUserId);		
	}
	
	/**
	 * Help command tests
	 */
	@Test
	public void testHelpCommand() {
		String testCommand = "help";
		
		StringBuilder testHelpMessage = new StringBuilder();
		testHelpMessage.append("/ttt play @username: " + LogMessage.getLogMsg(Messages.TTT5015I));
		testHelpMessage.append("\n\n /ttt move x y: " +  LogMessage.getLogMsg(Messages.TTT5016I));
		testHelpMessage.append("\n\n /ttt board: " + LogMessage.getLogMsg(Messages.TTT5017I));
		testHelpMessage.append("\n\n /ttt quit:  "+ LogMessage.getLogMsg(Messages.TTT5018I));
		testHelpMessage.append("\n\n /ttt help: " + LogMessage.getLogMsg(Messages.TTT5019I));
		
		testSlackInput.setText(testCommand);
		testResponse = hc.processCommand(testSlackInput, testGameMap);
		assertEquals("Test2 ", testHelpMessage.toString(), testResponse.getText());
		
		
		
	}
	

}
