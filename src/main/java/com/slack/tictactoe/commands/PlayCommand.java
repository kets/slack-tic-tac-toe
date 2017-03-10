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
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;

public class PlayCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(PlayCommand.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("play command invoked");
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		
		//validate input tokens
		if (inputTokens.length < 2) {
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5001E));
		}
		
		// check if an existing game is being played
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5002E));
		}
		
		
		
		// validate second user by checking if the user_id is passed in the text field
        //The toggle to get the user_id and channel_is has been turned on
        //<@U4BJG47DG|oxo>
		// parse the string and look for a pipe
		// Per Slack docs, two people cannot have the same username in a team, so the username is unique
		
		if (!inputTokens[1].contains("|") || !inputTokens[1].contains(Constants.AT)) {
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5003E));
		}
		
		int pipeIndex = inputTokens[1].indexOf('|');
		String firstPlayer = Constants.AT + slackInput.getUser_id();
		String secondPlayer = inputTokens[1].substring(1, pipeIndex);
		logger.info("firstPlayer: " + firstPlayer + " secondPlayer " + secondPlayer);
        
        //validated user
		TicTacToe game = new TicTacToe(firstPlayer, secondPlayer);
		gameMap.put(slackInput.getChannel_id(), game);
	
		return new ChannelResponse("Game started between <" + firstPlayer + "> and <" + secondPlayer + ">");
	}

}
