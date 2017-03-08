package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class HelpController implements CommandController {
	private static final Logger logger = LoggerFactory.getLogger(HelpController.class);
	
	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {		
		logger.debug("help command invoked");
		// Help command 
		String helpText = 
				"/ttt play @[username]: starts a Tic Tac Toe game with another user in the channel " +
			    "\n\n /ttt move x y: move the game piece to a particular position on the board " +
				"\n\n /ttt board: display the current board " +
			    "\n\n /ttt quit: quit the current game (only game players can quit the game) " +
				"\n\n /ttt help: help manual";
		return new ChannelResponse(helpText);
	}

}