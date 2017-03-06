package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class PlayController implements CommandController {
	private static final Logger logger = LoggerFactory.getLogger(PlayController.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		//TODO validate input tokens
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("An existing game is currently being played. Wait until it's completed to start a new one");
		}
		
		String firstPlayer = slackInput.getUser_name();
		//TODO in order to emulate another player, will need to hard code the username as the second player.
//		String secondPlayer = inputTokens[1].substring(1);
		String secondPlayer = slackInput.getUser_name();
		logger.info("firstPlayer: " + firstPlayer + " secondPlayer " + secondPlayer);
		TicTacToe game = new TicTacToe(firstPlayer, secondPlayer);
		gameMap.put(slackInput.getChannel_id(), game);
	
		return new ChannelResponse("Game started between @" + firstPlayer + " and @" + secondPlayer);
	}

}
