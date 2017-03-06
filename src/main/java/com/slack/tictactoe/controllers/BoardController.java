package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class BoardController implements CommandController  {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 1) {
			return new ChannelResponse("Insufficient input params. Please try again");
		}
		
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("An existing game is currently being played. Wait until it's completed to start a new one");
		}
		logger.debug("inputTokens: " + inputTokens[0]);
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		return new ChannelResponse("```"+game.displayBoard() +"```");
	}

}
