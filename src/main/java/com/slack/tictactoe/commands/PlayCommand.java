package com.slack.tictactoe.commands;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;

public class PlayCommand implements Command {
	private static final Logger logger = LoggerFactory.getLogger(PlayCommand.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("play command invoked");
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		//TODO validate input tokens
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("An existing game is currently being played. Wait until it's completed to start a new one");
		}
		
		String firstPlayer = slackInput.getUser_name();
		
		//TODO validate second user by checking if the user_id is passed in the text field
        //The toggle to get the user_id and channel_is has been turned on
        //<@U4BJG47DG|oxo>
		// parse the string and look for a pipe
		// two people cannot have the same username in a team, so the username is unique
		
		if (!inputTokens[1].contains("|")) {
			return new EphemeralResponse("The user was not found in the channel. Please play with a valid user in this channel.");
		}
		
		int pipeIndex = inputTokens[1].indexOf('|');
		String secondPlayer = inputTokens[1].substring(pipeIndex + 1, inputTokens[1].length() - 1);
		logger.info("firstPlayer: " + firstPlayer + " secondPlayer " + secondPlayer);
        
        //validated user
		TicTacToe game = new TicTacToe(firstPlayer, secondPlayer);
		gameMap.put(slackInput.getChannel_id(), game);
	
		return new ChannelResponse("Game started between @" + firstPlayer + " and @" + secondPlayer);
	}

}
