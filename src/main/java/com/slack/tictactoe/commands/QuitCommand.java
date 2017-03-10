package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.GameState;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

public class QuitCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(QuitCommand.class);

	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("quit command invoked");
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5000I));
		}
		
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
		logger.debug("slackPlayer: " + slackInput.getUser_id());
		logger.debug("firstPlayer: " + game.getFirstPlayer());
		logger.debug("secondPlayer: " + game.getSecondPlayer());
		
		//validate if the current user is authorized to quit the game. 
		//only players of the current game can quit.
		if(!game.getFirstPlayer().equals(slackInput.getUser_id())
				&& !game.getSecondPlayer().equals(slackInput.getUser_id())) {
			return new EphemeralResponse("You are not allowed to quit the game!");
		}
		
		game.setGameState(GameState.QUIT);
		
		gameMap.remove(slackInput.getChannel_id());
		
		return new ChannelResponse(TTTUtils.formatUserId(slackInput.getUser_id()) + " quit the game.");
	}

}
