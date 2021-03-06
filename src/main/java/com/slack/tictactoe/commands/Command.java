package com.slack.tictactoe.commands;

import java.util.Map;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.SlackResponse;

/**
 * Base class for all current and future commands
 * Implementers are required to implement the processCommand()
 *
 */
public interface Command {
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap);

}
