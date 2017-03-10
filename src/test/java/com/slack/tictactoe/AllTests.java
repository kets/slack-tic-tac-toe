package com.slack.tictactoe;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.slack.tictactoe.service.TicTacToeServiceTest;
import com.slack.tictactoe.commands.*;

@RunWith(Suite.class)
@SuiteClasses({
	TicTacToeServiceTest.class,
	PlayCommandTest.class,
	MoveCommandTest.class,
	HelpCommandTest.class,
	QuitCommandTest.class
	
})
public class AllTests {

}
