package com.slack.tictactoe.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/ttt")
public class TicTacToeService {
	@Path("/heartbeat")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("hello world!").build();
	}
	
	@POST
	public Response processTTTCommand(String request) {
		
		return Response.status(Response.Status.OK).entity("Let's play! " +request).build();
	}
	

}