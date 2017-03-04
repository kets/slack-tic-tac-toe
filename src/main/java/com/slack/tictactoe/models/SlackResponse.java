package com.slack.tictactoe.models;

public class SlackResponse {
	private String response_type;
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

}
