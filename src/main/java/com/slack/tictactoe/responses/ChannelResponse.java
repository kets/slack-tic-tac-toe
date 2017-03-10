package com.slack.tictactoe.responses;

import com.slack.tictactoe.Constants;

/**
 * ChannelResponse handles responses visible to all users in a channel
 *
 */
public class ChannelResponse extends SlackResponse {
	private final String response_type = Constants.IN_CHANNEL;
	
	/**
	 * Initializes the instance with text
	 * @param text
	 */
	public ChannelResponse(String text) {
		super(text);
	}
	
	/**
	 * Overrides abstract function by setting the response_type
	 */
	public String getResponse_type() {
		return this.response_type;
	}
	
	/**
	 * Overrides toString() by call the super getText()
	 */
	@Override
	public String toString() {
		return this.response_type + " " + getText();
	}

}
