package com.slack.tictactoe.models;

public abstract class SlackResponse {	
	private String text;
	
	public abstract String getResponse_type();
	public SlackResponse(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
}
