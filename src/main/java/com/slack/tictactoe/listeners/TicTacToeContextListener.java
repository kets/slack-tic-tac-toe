package com.slack.tictactoe.listeners;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;

@WebListener
public class TicTacToeContextListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(TicTacToeContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// remove the gameMap from ServletContext
		ServletContext context = sce.getServletContext();
		context.removeAttribute("gameMap");
		logger.info(LogMessage.getLogMsg(Messages.TTT4005I));
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//Add gameMap to the servlet context so it's available between HTTP requests
		Map<String, TicTacToe> gameMap = new ConcurrentHashMap<String, TicTacToe>();
		ServletContext context = sce.getServletContext();
		context.setAttribute("gameMap", gameMap);
		logger.info(LogMessage.getLogMsg(Messages.TTT4000I));
	}

}
