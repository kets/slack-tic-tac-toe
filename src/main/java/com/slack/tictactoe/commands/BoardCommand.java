package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

public class BoardCommand implements Command  {
	private static final Logger logger = LoggerFactory.getLogger(BoardCommand.class);
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("board command invoked");
		//
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 1) {
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5001E));
		}
		
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5000I));
		}
		logger.debug("inputTokens: " + inputTokens[0]);
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
	
		return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS +
				 "\n\n It's "+ TTTUtils.formatUserId(game.whoseTurn()));
	}

}
