package com.slack.tictactoe.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slack.tictactoe.Constants;
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
	
	public void makeMove(int row, int col) throws IllegalArgumentException {
		//ensure the move is valid
		validateMove(row, col);
		
		board[row][col] = currentPiece;
		movesMade++;
		
		//check if current player is winner
		if(checkForWin(row, col, this.currentPiece)){
			if (this.currentPiece == Constants.X) {
				this.setGameState(GameState.PLAYER1_WINNER);
			} else {
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
	 * 
	 * @param row
	 * @param col
	 * @throws IllegalArgumentException
	 */
	private void validateMove(int row, int col) throws IllegalArgumentException {
		if (row < 0 || row > board.length - 1 ||
				col < 0 || col > board.length - 1) {
			//TODO add to messages
			throw new IllegalArgumentException ("Invalid board coordinates. Please try again.");
		}	
		
		if (board[row][col] != Constants.EMPTY_CELL) {
			//TODO add to messages
			throw new IllegalArgumentException ("Cell is occupied. Please try again.");
		}
		
	}
	
	public String whoseTurn() {
		StringBuilder sb = new StringBuilder();
		if(this.currentPiece == Constants.X) {
			sb.append(this.firstPlayer);
		} else if(this.currentPiece == Constants.O) {
			sb.append(this.secondPlayer);
		}
		return sb.append(" 's turn.").toString();
	}
	
	private boolean checkForWin(int row, int col, char mark) {
		if (checkRows(row, col, mark) || checkCols(row, col, mark)) {
			return true;
		}			
		if (row == col) {
			//on a diagonal, so check for diagonal
			return checkDiagonal(row, col, mark);
		}
		if (row + col == board.length - 1) {
			//on an anti-diagonal  
			return checkReverseDiagonal(row, col, mark);
		}
		return false;
	}
	
	private boolean checkRows(int row, int col, char mark) {
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
	
	private boolean checkCols(int row, int col, char mark) {
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
	
	private boolean checkDiagonal(int row, int col, char mark) {
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
	
	private boolean checkReverseDiagonal(int row, int col, char mark) {
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

    public String getFirstPlayer() {
		return this.firstPlayer;
	}
	
	public String getSecondPlayer() {
		return this.secondPlayer;
	}
	
	public String getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	
}