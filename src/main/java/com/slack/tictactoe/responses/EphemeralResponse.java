package com.slack.tictactoe.responses;

import com.slack.tictactoe.Constants;

public class EphemeralResponse extends SlackResponse {
	private final String response_type = Constants.EPHEMERAL;
	
	public EphemeralResponse(String text) {
		super(text);
	}
	
	@Override
	public String getResponse_type() {
		return this.response_type;
	}
	
	@Override
	public String toString() {
		return getText();
	}
}
