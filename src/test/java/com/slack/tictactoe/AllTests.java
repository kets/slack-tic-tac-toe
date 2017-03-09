package com.slack.tictactoe;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.slack.tictactoe.service.TicTacToeServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	TicTacToeServiceTest.class
	
})
public class AllTests {

}
