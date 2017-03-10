package com.slack.tictactoe.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.GameState;

/**
 * TicTacToe game class which keeps track of the current players, 
 * current moves, and win scenarios
 */
public class TicTacToe {
	private final char [][] board;
	private static final Logger logger = LoggerFactory.getLogger(TicTacToe.class);
	private String firstPlayer;
	private String secondPlayer;
	private String currentPlayer;
	private char currentPiece;
	private int movesMade;
	private GameState gameState;
	
	/**
	 * Constructor to intialize a new game with two players
	 * @param firstPlayer
	 * @param secondPlayer
	 */
	public TicTacToe (String firstPlayer, String secondPlayer) {
		logger.debug("initializing (3x3) Tic Tac Toe board");
		this.board = new char[3][3];
		this.setFirstPlayer(firstPlayer);
		this.setSecondPlayer(secondPlayer);
		this.setCurrentPlayer(firstPlayer);
		this.movesMade = 0;
		this.setCurrentPiece(Constants.X); //first player is X, second player is O
		this.setGameState(GameState.IN_PROGRESS);
		boardInit();
	}
	
	/**
	 * Initializes the board array to 3x3 by default
	 */
	private void boardInit() {
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = Constants.EMPTY_CELL;
			}
		}
	}
	
	/**
	 * Validates and processes the move made by a current player
	 * Once the valid move is complete, swaps the player
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	public void makeMove(int row, int col) throws IllegalArgumentException {
		logger.debug("[row,col] = [" + row + ","+ col + "]");
		//ensure the move is valid
		validateMove(row, col);
		
		board[row][col] = currentPiece;
		movesMade++;
		
		//check if current player is winner
		if(checkForWin(row, col, this.currentPiece)){
			if (this.currentPiece == Constants.X) {
				logger.debug("player1 has won");
				this.setGameState(GameState.PLAYER1_WINNER);
			} else {
				logger.debug("player2 has won");
				this.setGameState(GameState.PLAYER2_WINNER);
			}
			return; 			
		}
		
		//check for tie
		if(movesMade >= 9) {
			//tie game
			this.setGameState(GameState.TIE);
			return;
		}
		
		//swap the player
		swapPlayer();		
	}
	
	/**
	 * swap the player and game piece once the turn is complete
	 */
	private void swapPlayer() {
		if(this.currentPlayer.equals(this.firstPlayer)) {
			//switch the symbol for the second player
			setCurrentPiece(Constants.O);
			this.currentPlayer = this.secondPlayer;		
		} else {
			//switch the symbol for the first player
			setCurrentPiece(Constants.X);
			this.currentPlayer = this.firstPlayer;
		}
	}
	
	/**
	 * Ensures that the current move is valid
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	private void validateMove(int row, int col) throws IllegalArgumentException {
		if (row < 0 || row > board.length - 1 ||
				col < 0 || col > board.length - 1) {
			//TODO add to messages
			throw new IllegalArgumentException (LogMessage.getLogMsg(Messages.TTT6000E));
		}	
		
		if (board[row][col] != Constants.EMPTY_CELL) {
			//TODO add to messages
			throw new IllegalArgumentException (LogMessage.getLogMsg(Messages.TTT6001E));
		}
		
	}
	
	/**
	 * Returns the next player's turn
	 * @return string
	 */
	public String whoseTurn() {
		StringBuilder sb = new StringBuilder();
		if (this.currentPiece == Constants.X) {
			sb.append(this.firstPlayer);
		} else if(this.currentPiece == Constants.O) {
			sb.append(this.secondPlayer);
		}
		return sb.toString();
	}
	
	/**
	 * Depending on the ,row,col] provided, determines whether the move will result in a win
	 * @param row
	 * @param col
	 * @param mark
	 * @return boolean
	 */
	private boolean checkForWin(int row, int col, char mark) {
		if (checkRowsForWin(row, col, mark) || checkColsForWin(row, col, mark)) {
			return true;
		}			
		if (row == col) {
			//on a diagonal, so check for diagonal
			return checkDiagonalForWin(row, col, mark);
		}
		if (row + col == board.length - 1) {
			//on an anti-diagonal  
			return checkReverseDiagonalForWin(row, col, mark);
		}
		return false;
	}
	
	/**
	 * Checks all cols for a specific row and determines if there is a win
	 * It skips the current cell as we know it was just marked
	 * @param row
	 * @param col
	 * @param mark
	 * @return boolean
	 */
	private boolean checkRowsForWin(int row, int col, char mark) {
		for (int i = 0; i < board.length; i++) {
			//skip the check for the cell the user just marked
			if (i != col) {
				if((board[row][i] != mark)) {
					return false;
				}
			}
		}		
		return true;		
	}
	
	/**
	 * Checks all rows for a specific col and determines if there is a win
	 * It skips the current cell as we know it was just marked
	 * @param row
	 * @param col
	 * @param mark
	 * @return boolean
	 */
	private boolean checkColsForWin(int row, int col, char mark) {
		for (int i = 0; i < board.length; i++) {
			//skip the check for the cell the user just marked
			if (i != row) {
				if((board[i][col] != mark)) {
					return false;
				}
			}
		}		
		return true;		
	}
	
	/**
	 * Checks the left to right diagonal for a win
	 * @param row
	 * @param col
	 * @param mark
	 * @return boolean
	 */
	private boolean checkDiagonalForWin(int row, int col, char mark) {
		//in this case, row == col
		for(int i = 0; i < board.length; i++){
			//skip the check for the cell the user just marked
			if (i != row) {
				if((board[i][i] != mark)) {
					return false;
				}
			}              
        }
		return true;		
	}  
	
	/**
	 * Checks the right to left diagonal for a win
	 * @param row
	 * @param col
	 * @param mark
	 * @return boolean
	 */
	private boolean checkReverseDiagonalForWin(int row, int col, char mark) {
		for (int i = 0; i < board.length; i++) {
			//skip the check for the cell the user just marked
			if (i != row) {
				if((board[i][(board.length - 1) - i] != mark)) {
					return false;
				}
			}			
		}
		return true;
	}
	
	/**
	 * Displays the current board
	 * @return String
	 */
	public String displayBoard() {
		StringBuilder boardBuilder = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			if (i != 0) {
				boardBuilder.append(Constants.ROW_SEPARATOR);
			}			
			for (int j = 0; j < board.length; j++) {
				if (j == 0) {
					boardBuilder.append(Constants.COL_SEPARATOR);
				}
				if(board[i][j] == Constants.X) {
					boardBuilder.append(Constants.TEXT_DELIMITER + Constants.X + Constants.TEXT_DELIMITER);
				} else if(board[i][j] == Constants.O) {
					boardBuilder.append(Constants.TEXT_DELIMITER + Constants.O + Constants.TEXT_DELIMITER);
				} else {
					//empty cell
					boardBuilder.append(Constants.TEXT_DELIMITER + Constants.EMPTY_CELL + Constants.TEXT_DELIMITER);
				}
				if (j < board.length) {
					boardBuilder.append(Constants.COL_SEPARATOR);
				}
			}
		}
		return boardBuilder.toString();
	}
	
	/**
	 * Sets the first player of the game
	 * This player makes the first move
	 * @param firstPlayer
	 */
	public void setFirstPlayer(String firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	
	/**
	 * Sets the current player after the game
	 * It will get set when the players are swapped
	 * @param currentPlayer
	 */
	public void setCurrentPlayer(String currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Sets the current piece (x or o)
	 * @param currentPiece
	 */
	public void setCurrentPiece(char currentPiece){
		this.currentPiece = currentPiece;
	}
	
	/**
	 * Sets the second player of the game
	 * @param secondPlayer
	 */
	public void setSecondPlayer(String secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	/**
	 * returns the current game state
	 * @return GameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * returns the first player
	 * @return String
	 */
    public String getFirstPlayer() {
		return this.firstPlayer;
	}
	
    /**
     * returns the second player
     * @return String
     */
	public String getSecondPlayer() {
		return this.secondPlayer;
	}
	
	/**
	 * returns the current player
	 * @return String
	 */
	public String getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * sets the current game state
	 * @param gameState
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	
}
