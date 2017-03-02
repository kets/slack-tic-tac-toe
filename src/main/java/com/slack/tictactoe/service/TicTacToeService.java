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
//		if (formParams.containsKey("token")){
//			slackParams.setToken(formParams.getFirst("token"));
//			if (!slackParams.getToken().equals(System.getenv("token"))) {
//				return Response.status(Response.Status.BAD_REQUEST).build();
//			}			
//		}
//		slackParams.setChannel_id(formParams.getFirst("channel_id"));
//		slackParams.setChannel_name(formParams.getFirst("channel_name"));
//		slackParams.setUser_id(formParams.getFirst("user_id"));
//		slackParams.setUser_name(formParams.getFirst("user_name"));
//		slackParams.setTeam_id(formParams.getFirst("team_id"));
//		slackParams.setTeam_domain(formParams.getFirst("team_domain"));
//		slackParams.setText(formParams.getFirst("text"));
//		slackParams.setResponse_url(formParams.getFirst("response_url"));
//		
//		slackParams.setCommand(formParams.getFirst("text"));
//		
//		logger.info(slackParams.toString());
		
//		return Response.status(Response.Status.OK).entity("Let's play! " + slackParams.getUser_name()).build();
		return Response.status(Response.Status.OK).entity("Let's play! " + formParams.getFirst("user_name")
						+ " " + formParams.getFirst("response_url") 
						+ " " + formParams.getFirst("user_id")
						+ " " + formParams.getFirst("channel_id")
						+ " " + formParams.getFirst("channel_name")
						+ " " + formParams.getFirst("text")
						+ " " + formParams.getFirst("token")
						+ " " + formParams.getFirst("team_domain")
						+ " " + formParams.getFirst("team_id")
						+ " " + formParams.getFirst("token")).build();
	}
	

}
