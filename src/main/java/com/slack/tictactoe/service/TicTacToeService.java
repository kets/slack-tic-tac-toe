package com.slack.tictactoe.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.slack.tictactoe.commands.BoardCommand;
import com.slack.tictactoe.commands.Command;
import com.slack.tictactoe.commands.HelpCommand;
import com.slack.tictactoe.commands.MoveCommand;
import com.slack.tictactoe.commands.PlayCommand;
import com.slack.tictactoe.commands.QuitCommand;
import com.slack.tictactoe.common.Constants;
import com.slack.tictactoe.game.TicTacToe;
import com.slack.tictactoe.i18n.Messages;
import com.slack.tictactoe.logging.LogMessage;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.responses.EphemeralResponse;
import com.slack.tictactoe.responses.SlackResponse;
import com.slack.tictactoe.utils.TTTUtils;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Service class to handle all the incoming HTTP request from Slack
 */
@Path("/ttt")
public class TicTacToeService extends Application {
	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);
	private Command playController = new PlayCommand();
	private Command moveController = new MoveCommand();
	private Command boardController = new BoardCommand();
	private Command helpController = new HelpCommand();
	private Command quitController = new QuitCommand();			

	@Context
	private ServletContext context;
	
	/**
	 * Test GET method to ensure service is up and running
	 * @return
	 */
	@Path("/heartbeat")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("Get ready to play Tic Tac Toe!").build();
	}
	
	/**
	 * POST method to process when /ttt command is sent from Slack
	 * @param formParams
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response processTTTCommand(MultivaluedMap<String, String> formParams) {
		//init the SLackInput POJO with the input params
		logger.debug("size: " + formParams.size());
		logger.debug("params: " + formParams.entrySet().toString());
		
		final SlackInput slackParams = new SlackInput();
		SlackResponse slackRes = null;
		
		//validate token from Slack
		if (!formParams.containsKey(Constants.TOKEN)){
			slackRes = new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT4001E).toString());
			logger.error(slackRes.getText());
			return Response.status(Response.Status.OK).entity(new Gson().toJson(slackRes)).build();
		}
		
		slackParams.setToken(formParams.getFirst(Constants.TOKEN));
		if (!slackParams.getToken().equals(System.getenv(Constants.TOKEN))) {
			slackRes = new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT4002E).toString());
			logger.error(slackRes.getText());
			return Response.status(Response.Status.OK).entity(new Gson().toJson(slackRes)).build();
		}	
		
		// Initialize SlackInput POJO with input params
		slackParams.setChannel_id(formParams.getFirst(Constants.CHANNEL_ID));
		slackParams.setChannel_name(formParams.getFirst(Constants.CHANNEL_NAME));
		slackParams.setUser_id(formParams.getFirst(Constants.USER_ID));
		slackParams.setUser_name(formParams.getFirst(Constants.USER_NAME));
		slackParams.setTeam_id(formParams.getFirst(Constants.TEAM_ID));
		slackParams.setTeam_domain(formParams.getFirst(Constants.TEAM_DOMAIN));
		slackParams.setText(formParams.getFirst(Constants.TEXT));
		slackParams.setCommand(formParams.getFirst(Constants.COMMAND));
		
		//Decode response_url for delayed responses
		String responseUrl = TTTUtils.decodeString(formParams.getFirst(Constants.RESPONSE_URL));
		if (responseUrl != null){
			slackParams.setResponse_url(responseUrl);
		}
		
		logger.info(slackParams.toString());
		
		//parse the text from the command 
		String [] inputText = slackParams.getText().split(Constants.TEXT_DELIMITER);
		@SuppressWarnings("unchecked")
		Map<String, TicTacToe> gameMap = (ConcurrentHashMap<String, TicTacToe>) context.getAttribute("gameMap");
		
		//invoke the command classes based on the command
		switch (inputText[0]) {
			case Constants.PLAY:
				slackRes = playController.processCommand(slackParams, gameMap);
				break;
			case Constants.MOVE:
				slackRes = moveController.processCommand(slackParams, gameMap);
				break;
			case Constants.BOARD:
				slackRes = boardController.processCommand(slackParams, gameMap);
				break;
			case Constants.HELP:
				slackRes = helpController.processCommand(slackParams, gameMap);
				break;
			case Constants.QUIT:
				slackRes = quitController.processCommand(slackParams, gameMap);
				break;
			default:
				slackRes = new EphemeralResponse(LogMessage.getLogMsg(Messages.TTT4004E));
				logger.error(slackRes.getText());				
				return Response.status(Response.Status.OK).entity(new Gson().toJson(slackRes)).build();
							
		}
		return Response.status(Response.Status.OK).entity(new Gson().toJson(slackRes)).build();
	}
	
}
