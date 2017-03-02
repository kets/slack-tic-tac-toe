package com.slack.tictactoe.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("/ttt")
public class TicTacToeService {
	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

	@Path("/heartbeat")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("hello world!").build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response processTTTCommand(MultivaluedMap<String, String> formParams) {
		
		return Response.status(Response.Status.OK).entity("Let's play! " +formParams.getFirst("username")).build();
	}
	

}
