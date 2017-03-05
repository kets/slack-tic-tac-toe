package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class PlayController {
	private static final Logger logger = LoggerFactory.getLogger(PlayController.class);
	
	public SlackResponse processPlayCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("An existing game is currently being played. Wait until it's completed to start a new one");
		}
		
		String firstPlayer = slackInput.getUser_name();
		String secondPlayer = inputTokens[1];
		logger.info("firstPlayer: " + firstPlayer + " secondPlayer " + secondPlayer);
		TicTacToe game = new TicTacToe(firstPlayer, secondPlayer);
		gameMap.put(slackInput.getChannel_id(), game);
	
		return new ChannelResponse("Game started between " + firstPlayer + " and " + secondPlayer);
	}

}
