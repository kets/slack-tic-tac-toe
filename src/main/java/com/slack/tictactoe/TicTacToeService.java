package com.slack.tictactoe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/slack-ttt/")
@Produces(MediaType.APPLICATION_JSON)
public class TicTacToeService {
	@Path("heartbeat")
	@GET
	public Response helloWorld() {
		return Response.status(Response.Status.OK).entity("hello world!").build();
	}

}
