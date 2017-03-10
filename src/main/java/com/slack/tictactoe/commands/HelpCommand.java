package com.slack.tictactoe.commands;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.SlackResponse;

/**
 * Handles the processing when the 'help' command is invoked in Slack
 *
 */
public class HelpCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);
	
	/**
	 * Displays the usage of all the supported commands
	 */
	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {		
		logger.debug(LogMessage.getLogMsg(Messages.TTT5014D, "help"));
		
		// command usage
		StringBuilder helpText = new StringBuilder();
		helpText.append("/ttt play @username: " + LogMessage.getLogMsg(Messages.TTT5015I));
		helpText.append("\n\n /ttt move x y: " +  LogMessage.getLogMsg(Messages.TTT5016I));
		helpText.append("\n\n /ttt board: " + LogMessage.getLogMsg(Messages.TTT5017I));
		helpText.append("\n\n /ttt quit:  "+ LogMessage.getLogMsg(Messages.TTT5018I));
		helpText.append("\n\n /ttt help: " + LogMessage.getLogMsg(Messages.TTT5019I));
		return new ChannelResponse(helpText.toString());
	}

}