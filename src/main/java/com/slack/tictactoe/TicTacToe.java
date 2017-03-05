package com.slack.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToe {
	private final char [][] board;
	private static final Logger logger = LoggerFactory.getLogger(TicTacToe.class);
	private String firstPlayer;
	private String secondPlayer;
	private char currentPlayer;
	private int movesMade;
	
	public TicTacToe (String firstPlayer, String secondPlayer) {
		logger.debug("initializing Tic Tac Toe board");
		this.board = new char[3][3];
		this.firstPlayer = firstPlayer;
		this.secondPlayer =  secondPlayer;
		this.currentPlayer = Constants.X;
		this.movesMade = 0;
		boardInit();
	}
	
	public void boardInit() {
		for(int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board.length - 1; j++) {
				board[i][j] = Constants.EMPTY_CELL;
			}
		}
	}
	
	public void makeMove(int row, int col) {
		//ensure the move is valid
		if (validateMove(row, col)){
			board[row][col] = currentPlayer;
			movesMade++;
		}
		//check if current player is winnder
		if(checkForWin(currentPlayer)){
			
		}
		
		//check for tie
		if(movesMade == 9) {
			//tie game
		}
		//switch the symbol for the second player
		currentPlayer = Constants.O;
		
		//check the game status of the player
		
	}
	
	private boolean validateMove(int row, int col) {
		if (board[row][col] != Constants.EMPTY_CELL) {
			//TODO add to messages
			logger.error("CELL IS ALREADY OCCUPIED");
			return false;
		}
		
		if (row < 0 || row > board.length - 1 ||
				col < 0 || col > board.length - 1) {
			//TODO add to messages
			logger.error("Invalid board params");
			return false;
		}	
		
		if (movesMade > 9) {
			//TODO add to messages
			logger.error("Illegal move");
			return false;
		}
		return true;
	}
	
	private boolean checkForWin(char mark) {
		return checkRows(mark) || checkCols(mark) | checkDiagonals(mark);
	}
	
	private boolean checkRows(char mark) {
		for (int i = 0; i < board.length - 1; i++) {
			if(checkRowCol(board[i][0], board[i][1], board[1][2], mark)) {
				return true;
			}
		}		
		return false;		
	}
	
	private boolean checkCols(char mark) {
		for (int j = 0; j < board.length - 1; j++) {
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
		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board.length - 1; j++) {
				if(board[i][j] == Constants.X) {
					boardBuilder.append(Constants.X);
				} else if(board[i][j] == Constants.O) {
					boardBuilder.append(Constants.X);
				} else {
					//empty cell
					boardBuilder.append(Constants.EMPTY_CELL);
				}
				if (j < board.length - 1) {
					boardBuilder.append(Constants.COL_SEPARATOR);
				}
			}
			if (i < board.length - 1) {
				boardBuilder.append(Constants.ROW_SEPARATOR);
			}
		}
		return boardBuilder.toString();
	}
	
	
	
}
