package com.slack.tictactoe.commands;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.ChannelResponse;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.models.GameState;

public class MoveCommand implements Command{
	
private static final Logger logger = LoggerFactory.getLogger(MoveCommand.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		logger.debug("move command invoked");
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 3) {
			return new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT5001E));
		}
		logger.debug("inputTokens: " + inputTokens[0] + " " + inputTokens[1] + " " + inputTokens[2]);
		//check if this is a valid game
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			//TODO add message
			return new ChannelResponse(LogMessage.getLogMsg(Messages.TTT5000I));
		}
		
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
		
		//check if it is the current player's turn
		if(!isLegalMove(slackInput.getUser_name(), game)){
			return new EphemeralResponse("Please wait for your turn!");
		}		
		
		try {
			// validate coordinates
			int row = Integer.parseInt(inputTokens[1]);
			int col = Integer.parseInt(inputTokens[2]);
			 
			game.makeMove(row, col);
			if (game.getGameState().equals(GameState.PLAYER1_WINNER) || game.getGameState().equals(GameState.PLAYER2_WINNER)) {
				logger.debug("winner is: " + game.getCurrentPlayer());
				//game is over, remove the game from the global gameMap				
				gameMap.remove(slackInput.getChannel_id());				
				return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS + "\n\n Congrats to " + game.getCurrentPlayer() + " for winning the game!");
			} else if (game.getGameState().equals(GameState.TIE)){
				//game is over/tie
				gameMap.remove(slackInput.getChannel_id());				
				return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS + "\n\n Welp, it's a tie. Play again? :)");
			}
		} catch (NumberFormatException ex) {
			return new EphemeralResponse("Illegal command format. Use /ttt move x y to make your next move");
		} catch (IllegalArgumentException ex) {
			return new EphemeralResponse(ex.getMessage());
		} catch (Exception ex) {
			return new EphemeralResponse("Use /ttt help for usage");
		}
		
		return new ChannelResponse(Constants.BACK_TICKS + game.displayBoard() + Constants.BACK_TICKS + 
				"\n\nIt's " + Constants.AT + game.whoseTurn());
	}
	
	private boolean isLegalMove(String currentUser, TicTacToe game) {
		logger.debug("currentUser: "+ currentUser + " gamePlayer: "+ game.getCurrentPlayer());
		if(!currentUser.equals(game.getCurrentPlayer())) {
			return false;			
		}
		return true;
	}


}
