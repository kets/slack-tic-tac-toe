package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.EphemeralResponse;
import com.slack.tictactoe.models.GameState;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public class QuitController implements CommandController {
	private static final Logger logger = LoggerFactory.getLogger(QuitController.class);

	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("quit command invoked");
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("No games being played. Please start a game with another user in the channel.");
		}
		
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		if(!game.getFirstPlayer().equals(slackInput.getUser_name()) ||
				!game.getSecondPlayer().equals(slackInput.getUser_name())) {
			return new EphemeralResponse("You are not allowed to quit the game!");
		}
		
		game.setGameState(GameState.QUIT);
		
		gameMap.remove(slackInput.getChannel_id());
		
		return new ChannelResponse(slackInput.getUser_name() + " quit the game.");
	}

}
