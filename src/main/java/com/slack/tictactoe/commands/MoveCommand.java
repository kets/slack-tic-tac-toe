package com.slack.tictactoe.commands;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.common.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;
import com.slack.tictactoe.models.GameState;

/**
 * Handles the processing when the 'play' command is invoked in Slack
 */
public class MoveCommand implements Command {

	private static final Logger logger = LoggerFactory.getLogger(MoveCommand.class);

	/**
	 * Implementation of the required processCommand() function 
	 * Validates the input params and ensures that the players are making authorized moves
	 * Checks if the current move is a winning move or a tie
	 * Displays the current state of the game board and whose turn is next
	 */
	@Override
	public SlackResponse processCommand(SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug(LogMessage.getLogMsg(Messages.TTT5014D, "move"));
		
		final String[] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 3) {
			logger.error(LogMessage.getLogMsg(Messages.TTT5001E));
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5001E));
		}
		logger.debug("inputTokens: " + inputTokens[0] + " " + inputTokens[1] + " " + inputTokens[2]);

		// check if this is a valid game
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			logger.error(LogMessage.getLogMsg(Messages.TTT5000E));
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5000E));
		}

		TicTacToe game = gameMap.get(slackInput.getChannel_id());

		// check whether this user is participating in this game
		if (!slackInput.getUser_id().equals(game.getFirstPlayer())
				&& !slackInput.getUser_id().equals(game.getSecondPlayer())) {
			logger.error(slackInput.getUser_id() + " is not playing this game.");
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5004E));
		}

		// yes, user is playing in this game, then
		// check if it is the current player's turn
		if (!slackInput.getUser_id().equals(game.getCurrentPlayer())) {
			logger.error(slackInput.getUser_id() + " is making an unauthorized move.");
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5005E));
		}

		try {
			// move coordinates
			int row = Integer.parseInt(inputTokens[1]);
			int col = Integer.parseInt(inputTokens[2]);

			game.makeMove(row, col);
			if (game.getGameState().equals(GameState.PLAYER1_WINNER)
					|| game.getGameState().equals(GameState.PLAYER2_WINNER)) {
				logger.debug("winner is: " + game.getCurrentPlayer());
				// game is over, remove the game from the global gameMap
				gameMap.remove(slackInput.getChannel_id());
				return new ChannelResponse(
						Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS + "\n\n "
						+ LogMessage.getLogMsg(Messages.TTT5010I, TTTUtils.formatUserId(game.getCurrentPlayer())));
				
			} else if (game.getGameState().equals(GameState.TIE)) {
				// game is over/tie
				logger.debug("game ended in a tie. removing from gameMap");
				gameMap.remove(slackInput.getChannel_id());
				return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS
						+ "\n\n " + LogMessage.getLogMsg(Messages.TTT5009I));
			}
		} catch (NumberFormatException ex) {
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5006E));
		} catch (IllegalArgumentException ex) {
			return new EphemeralResponse(ex.getMessage());
		} catch (Exception ex) {
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5007E));
		}

		return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS + "\n\n"
				+ LogMessage.getLogMsg(Messages.TTT5011I, TTTUtils.formatUserId(game.getCurrentPlayer())));
	}

}
