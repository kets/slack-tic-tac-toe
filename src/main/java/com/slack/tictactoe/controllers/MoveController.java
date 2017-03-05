package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class MoveController {
	
private static final Logger logger = LoggerFactory.getLogger(MoveController.class);
	
	public SlackResponse processMoveCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 3) {
			return new ChannelResponse("Insufficient input params. Please try again");
		}
		logger.debug("inputTokens: " + inputTokens[0] + " " + inputTokens[1] + " " + inputTokens[2]);
		//check if this is a valid game
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			//TODO add message
			return new ChannelResponse("Not a valid game");
		}
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
		
		int row = Integer.parseInt(inputTokens[1]);
		int col = Integer.parseInt(inputTokens[2]);
		if (!game.makeMove(row, col)){
			return new ChannelResponse("Invalid Params. Please try again.");
		}
		
		return new ChannelResponse("```"+game.displayBoard() +"```" + "\n It's " + slackInput.getUser_name() + " turn");
	}


}
