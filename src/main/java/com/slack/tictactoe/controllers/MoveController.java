package com.slack.tictactoe.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.TicTacToe;
import com.slack.tictactoe.models.ChannelResponse;
import com.slack.tictactoe.models.EphemeralResponse;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;
import com.slack.tictactoe.models.GameState;

public class MoveController implements CommandController{
	
private static final Logger logger = LoggerFactory.getLogger(MoveController.class);
	
	public SlackResponse processCommand (SlackInput slackInput, Map<String, TicTacToe> gameMap) {
		final String [] inputTokens = slackInput.getText().split(Constants.TEXT_DELIMITER);
		if (inputTokens.length < 3) {
			return new ChannelResponse("Insufficient input params. Please try again");
		}
		logger.debug("inputTokens: " + inputTokens[0] + " " + inputTokens[1] + " " + inputTokens[2]);
		//check if this is a valid game
		if (!gameMap.containsKey(slackInput.getChannel_id())) {
			//TODO add message
			return new ChannelResponse("No games being played. Please start a game with another user in the channel.");
		}
		
		TicTacToe game = gameMap.get(slackInput.getChannel_id());
		
		//TODO check if it is the current player's turn
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
				return new ChannelResponse("Congrats to " + game.getCurrentPlayer() + " for winning the game!");
			} else if (game.getGameState().equals(GameState.TIE)){
				//game is over/tie
				gameMap.remove(slackInput.getChannel_id());				
				return new ChannelResponse("Welp, it's a tie. Play again? :)");
			}
		} catch (NumberFormatException ex) {
			return new EphemeralResponse("Illegal command format. Use /ttt move x y to make your next move");
		} catch (IllegalArgumentException ex) {
			return new EphemeralResponse(ex.getMessage());
		} catch (Exception ex) {
			return new EphemeralResponse("Use /ttt help for usage");
		}
		
		return new ChannelResponse("```"+game.displayBoard() +"```" + "\n\n It's @" + game.whoseTurn());
	}
	
	private boolean isLegalMove(String currentUser, TicTacToe game) {
		logger.debug("currentUser: "+ currentUser + " gamePlayer: "+ game.getCurrentPlayer());
		if(!currentUser.equals(game.getCurrentPlayer())) {
			return false;			
		}
		return true;
	}


}
