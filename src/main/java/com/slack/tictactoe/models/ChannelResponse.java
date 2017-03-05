package com.slack.tictactoe.models;

import com.slack.tictactoe.Constants;

public class ChannelResponse extends SlackResponse {
	private final String response_type = Constants.IN_CHANNEL;

	public ChannelResponse(String text) {
		super(text);
	}

	
	public String getResponse_type() {
		return this.response_type;
	}
	
	@Override
	public String toString() {
		return this.response_type;
	}

}
