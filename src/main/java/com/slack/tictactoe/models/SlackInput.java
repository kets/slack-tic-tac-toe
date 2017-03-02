package com.slack.tictactoe.models;

public class SlackInput {
	private String token;
	private String team_id;
	private String team_domain;
	private String user_name;
	private String channel_id;
	private String channel_name;
	private String user_id;
	private String command;
	private String text;
	private String response_url;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the team_id
	 */
	public String getTeam_id() {
		return team_id;
	}
	/**
	 * @param team_id the team_id to set
	 */
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return the channel_id
	 */
	public String getChannel_id() {
		return channel_id;
	}
	/**
	 * @param channel_id the channel_id to set
	 */
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	/**
	 * @return the channel_name
	 */
	public String getChannel_name() {
		return channel_name;
	}
	/**
	 * @param channel_name the channel_name to set
	 */
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the response_url
	 */
	public String getResponse_url() {
		return response_url;
	}
	/**
	 * @param response_url the response_url to set
	 */
	public void setResponse_url(String response_url) {
		this.response_url = response_url;
	}
	@Override
	public String toString() {
		return "user_name="+this.getUser_name() + "text="+this.getText() + "response_url="+this.getResponse_url();
	}
	public String getTeam_domain() {
		return team_domain;
	}
	public void setTeam_domain(String team_domain) {
		this.team_domain = team_domain;
	}

}