package com.slack.tictactoe;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/tictactoe/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicTacToeService {
	@Path("heartbeat")
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("hello world!").build();
	}
	
	@Path("ttt")
	@POST
	public Response processTTTCommand(String body) {
		
		return Response.status(Response.Status.OK).entity(body + " Got the command!").build();
	}
	

}
