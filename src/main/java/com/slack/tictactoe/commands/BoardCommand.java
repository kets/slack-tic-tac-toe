package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.SlackResponse;

public class BoardCommand implements Command  {
	private static final Logger logger = LoggerFactory.getLogger(BoardCommand.class);
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 1) {
			return new ChannelResponse("Insufficient input params. Please try again");
		}
		
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("No game being played.");
		}
		logger.debug("inputTokens: " + inputTokens[0]);
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		return new ChannelResponse("```"+game.displayBoard() +"```");
	}

}
