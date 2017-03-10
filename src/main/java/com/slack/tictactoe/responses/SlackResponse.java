package com.slack.tictactoe.responses;

/**
 * Abstract base class that implements the base functionality for a Slack response
 */
public abstract class SlackResponse {
	private String text;

	/**
	 * abstract function required to be implemented by child classes
	 * @return
	 */
	public abstract String getResponse_type();
	
	/**
	 * Initializes the instance with text
	 * @param text
	 */
	public SlackResponse(String text) {
		this.text = text;
	}
	
	/**
	 * @return text
	 */
	public String getText() {
		return text;
	}

}
