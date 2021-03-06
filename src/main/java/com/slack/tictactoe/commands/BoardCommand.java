package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.common.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

/**
 * Handles the processing when the 'board' command is invoked in Slack
 */
public class BoardCommand implements Command  {
	private static final Logger logger = LoggerFactory.getLogger(BoardCommand.class);
	
	/**
	 * Returns the current state of the board and which player will make the next move
	 */
	@Override
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug(LogMessage.getLogMsg(Messages.TTT5014D, "board"));
		
		//validate input tokens
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
	
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			logger.error(LogMessage.getLogMsg(Messages.TTT5000E));
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5000E));
		}
		
		logger.debug("inputTokens: " + inputTokens[0]);
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS +
				 "\n\n " + LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(game.getCurrentPlayer())));
	}

}
