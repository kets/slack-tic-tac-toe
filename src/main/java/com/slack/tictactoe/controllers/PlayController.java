package com.slack.tictactoe.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.EphemeralResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

public class PlayController implements CommandController {
	private static final Logger logger = LoggerFactory.getLogger(PlayController.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		//TODO validate input tokens
		if (gameMap.containsKey(slackInput.getChannel_id())) {
			return new ChannelResponse("An existing game is currently being played. Wait until it's completed to start a new one");
		}
		
		String firstPlayer = slackInput.getUser_name();
		String secondPlayer = inputTokens[1].substring(1);
		logger.info("firstPlayer: " + firstPlayer + " secondPlayer " + secondPlayer);
		
		//TODO validate if second user is a real user in the channel
		SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackInput.getToken());
        try {
			session.connect();
		} catch (IOException e) {
			return new EphemeralResponse("Could not connect to Slack with the provided token.");
		}
        
        SlackChannel currentChannel = session.findChannelById(slackInput.getChannel_id());
        
        if (currentChannel == null) {
        	return new EphemeralResponse("Could not find the channel with the provided channel_id");
        }
		
        //Get all the channel users
        Collection<SlackUser> usersInChannel = currentChannel.getMembers();
        
        //There has to be at least one user (me), so don't have to check for null or empty
        if (!usersInChannel.contains(secondPlayer)) {
        	return new EphemeralResponse("Couldn't find user " + secondPlayer + " in this channel. Please select a valid user to play with."); 
        }
        
        //validated user
		TicTacToe game = new TicTacToe(firstPlayer, secondPlayer);
		gameMap.put(slackInput.getChannel_id(), game);
	
		return new ChannelResponse("Game started between @" + firstPlayer + " and @" + secondPlayer);
	}

}
