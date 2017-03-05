package com.slack.tictactoe.controllers;

import java.util.Map;

import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

public interface CommandController {
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap);

}
