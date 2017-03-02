package com.slack.tictactoe.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.slack.tictactoe.models.SlackInput;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/ttt")
public class TicTacToeService {
	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);
	private SlackInput slackParams;
	
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
		if (formParams.containsKey("token")){
			slackParams.setToken(formParams.get("token").get(0));
			if (!slackParams.getToken().equals(System.getenv("token"))) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}			
		}
		slackParams.setChannel_id(formParams.get("channel_id").get(0));
		slackParams.setChannel_name(formParams.get("channel_name").get(0));
		slackParams.setUser_id(formParams.get("user_id").get(0));
		slackParams.setUser_name(formParams.get("user_name").get(0));
		slackParams.setTeam_id(formParams.get("team_id").get(0));
		slackParams.setTeam_domain(formParams.get("team_domain").get(0));
		slackParams.setText(formParams.get("text").get(0));
		slackParams.setResponse_url(formParams.get("response_url").get(0));
		
		slackParams.setCommand(formParams.get("text").get(0));
		
		logger.info(slackParams.toString());
		
		return Response.status(Response.Status.OK).entity("Let's play! " + slackParams.getUser_name()).build();
//		return Response.status(Response.Status.OK).entity("Let's play! " + formParams.getFirst("user_name")).build();
	}
	

}
