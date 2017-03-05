package com.slack.tictactoe.service;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.models.SlackInput;
import com.slack.tictactoe.models.SlackResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/ttt")
public class TicTacToeService {
	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);
	private final SlackInput slackParams = new SlackInput();
	
	/**
	 * Test GET method to ensure service is up and running
	 * @return
	 */
	@Path("/heartbeat")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("hello world!").build();
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
		logger.info("size: " + formParams.size());
		logger.info("parms: " + formParams.entrySet().toString());
		SlackResponse slackRes = new SlackResponse();
		if (!formParams.containsKey("token")){
			slackRes.setText("Slack request didn't include the token");
			return Response.status(Response.Status.OK).entity(slackRes.toString()).build();
		}
		
		slackParams.setToken(formParams.getFirst("token"));
		if (!slackParams.getToken().equals(System.getenv("token"))) {
			slackRes.setText("Provided token failed to verify");
			return Response.status(Response.Status.OK).entity(slackRes.toString()).build();
		}		
		slackParams.setChannel_id(formParams.getFirst("channel_id"));
		slackParams.setChannel_name(formParams.getFirst("channel_name"));
		slackParams.setUser_id(formParams.getFirst("user_id"));
		slackParams.setUser_name(formParams.getFirst("user_name"));
		slackParams.setTeam_id(formParams.getFirst("team_id"));
		slackParams.setTeam_domain(formParams.getFirst("team_domain"));
		slackParams.setText(formParams.getFirst("text"));
		slackParams.setCommand(formParams.getFirst("command"));
		
		//Decode response_url for delayed responses
		try {
			String responseUrl = java.net.URLDecoder.decode(formParams.getFirst("response_url"), "UTF-8");
			slackParams.setResponse_url(responseUrl);
		} catch (UnsupportedEncodingException ex) {
			logger.error("Couldn't decode response_url");			
		}		
		
		logger.info(slackParams.toString());
		
		//parse the text from the command 
		
		return Response.status(Response.Status.OK).entity("Let's play! " + slackParams.getUser_name()).build();
	}
	

}
