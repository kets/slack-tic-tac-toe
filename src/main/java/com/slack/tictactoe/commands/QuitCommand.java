package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.models.GameState;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;

public class QuitCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(QuitCommand.class);

	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("quit command invoked");
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("No games being played. Please start a game with another user in the channel.");
		}
		
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
		logger.debug("slackPlayer: " + slackInput.getUser_name());
		logger.debug("firstPlayer: " + game.getFirstPlayer());
		logger.debug("secondPlayer: " + game.getSecondPlayer());
		
		//validate if the current user is authorized to quit the game. 
		//only players of the current game can quit.
		if(!game.getFirstPlayer().equals(slackInput.getUser_name())) {
			return new EphemeralResponse("You are not allowed to quit the game!");
		}
		
		game.setGameState(GameState.QUIT);
		
		gameMap.remove(slackInput.getChannel_id());
		
		return new ChannelResponse(slackInput.getUser_name() + " quit the game.");
	}

}
