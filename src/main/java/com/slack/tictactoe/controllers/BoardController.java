package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	public SlackResponse processBoardCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		return new ChannelResponse("```"+game.displayBoard() +"```");
	}

}
