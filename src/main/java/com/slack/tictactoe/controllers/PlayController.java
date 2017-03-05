package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.EphemeralResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;
import com.slack.tictactoe.service.TicTacToeService;

public class PlayController {
	private static final Logger logger = LoggerFactory.getLogger(PlayController.class);
	
	public SlackResponse processPlayCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			
		}
	
		return new EphemeralResponse("Ephemeral response from PlayController");
	}

}
