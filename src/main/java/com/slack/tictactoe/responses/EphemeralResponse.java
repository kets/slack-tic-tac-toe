package com.slack.tictactoe.responses;

import com.slack.tictactoe.common.Constants;

/**
 * EphemeralResponses handles responses that only the issuing user can see
 *
 */
public class EphemeralResponse extends SlackResponse {
	private final String response_type = Constants.EPHEMERAL;
	
	/**
	 * Initializes the instance with text
	 * @param text
	 */
	public EphemeralResponse(String text) {
		super(text);
	}
	
	/**
	 * Overrides abstract function by setting the response_type
	 */
	@Override
	public String getResponse_type() {
		return this.response_type;
	}
	
	/**
	 * Overrides toString() by call the super getText()
	 */
	@Override
	public String toString() {
		return getText();
	}
}
