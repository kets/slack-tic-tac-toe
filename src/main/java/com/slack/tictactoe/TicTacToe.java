package com.slack.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.models.GameState;

public class TicTacToe {
	private final char [][] board;
	private static final Logger logger = LoggerFactory.getLogger(TicTacToe.class);
	private String firstPlayer;
	private String secondPlayer;
	private String currentPlayer;
	private char currentPiece;
	private int movesMade;
	private GameState gameState;
	
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
	
	private void boardInit() {
		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = Constants.EMPTY_CELL;
			}
		}
	}
	
	public void makeMove(int row, int col) {
		//ensure the move is valid
		if (validateMove(row, col)){
			board[row][col] = currentPiece;
			movesMade++;
		}
		
		//check if current player is winner
		if(checkForWin(currentPiece)){
			if (this.currentPiece == Constants.X) {
				this.setGameState(GameState.PLAYER1_WINNER);
			} else {
				this.setGameState(GameState.PLAYER2_WINNDER);
			}
			return; 			
		}
		
		//check for tie
		if(movesMade == 9) {
			//tie game
			this.setGameState(GameState.TIE);
			return;
		}
		
		//switch the symbol for the second player
		setCurrentPiece(Constants.O);
		//swap the player
		swapPlayer();		
	}
	
	private void swapPlayer() {
		if(this.currentPlayer.equals(this.firstPlayer)) {
			this.currentPlayer = this.secondPlayer;
		} else {
			this.currentPlayer = this.firstPlayer;
		}
	}
	
	private boolean validateMove(int row, int col) {
		if (row < 0 || row > board.length - 1 ||
				col < 0 || col > board.length - 1) {
			//TODO add to messages
			logger.error("Invalid board params");
			return false;
		}	
		
		if (board[row][col] != Constants.EMPTY_CELL) {
			//TODO add to messages
			logger.error("CELL IS ALREADY OCCUPIED");
			return false;
		}
		
		
		if (movesMade > 9) {
			//TODO add to messages
			logger.error("Illegal move");
			return false;
		}
		return true;
	}
	
	public String whoseTurn() {
		StringBuilder sb = new StringBuilder();
		if(this.currentPiece == Constants.X) {
			sb.append(this.firstPlayer);
		} else if(this.currentPiece == Constants.O) {
			sb.append(this.secondPlayer);
		}
		return sb.append("\'s turn.").toString();
	}
	
	private boolean checkForWin(char mark) {
		return checkRows(mark) || checkCols(mark) | checkDiagonals(mark);
	}
	
	private boolean checkRows(char mark) {
		for (int i = 0; i < board.length; i++) {
			if(checkRowCol(board[i][0], board[i][1], board[1][2], mark)) {
				return true;
			}
		}		
		return false;		
	}
	
	private boolean checkCols(char mark) {
		for (int j = 0; j < board.length; j++) {
			if(checkRowCol(board[0][j], board[1][j], board[2][j], mark)) {
				return true;
			}
		}		
		return false;
	}
	
	private boolean checkDiagonals(char mark) {
		return ((checkRowCol(board[0][0], board[1][1], board[2][2], mark)) 
				|| (checkRowCol(board[0][2], board[1][1], board[2][0], mark)));
		
	}  
	
	private boolean checkRowCol(char c1, char c2, char c3, char mark) {
		return (c1 != Constants.EMPTY_CELL 
				&& c1 == mark && c2 == mark && c3 == mark);
	}
	
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
	
	public void setFirstPlayer(String firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	
	public void setCurrentPlayer(String currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	
	public void setCurrentPiece(char currentPiece){
		this.currentPiece = currentPiece;
	}
	
	public void setSecondPlayer(String secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	
}
