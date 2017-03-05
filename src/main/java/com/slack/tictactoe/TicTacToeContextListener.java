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
		// remove the gameMap from ServletContext
		ServletContext context = sce.getServletContext();
		context.removeAttribute("gameMap");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//Add gameMap to the servlet context so it's available between HTTP requests
		Map<String, TicTacToe> gameMap = new HashMap<String, TicTacToe>();
		ServletContext context = sce.getServletContext();
		context.setAttribute("gameMap", gameMap);
	}

}