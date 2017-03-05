package com.slack.tictactoe;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TicTacToeContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Map<String, TicTacToe> gameMap = new HashMap<String, TicTacToe>();
		ServletContext context = sce.getServletContext();
		context.setAttribute("gameMap", gameMap);
		
	}

}
