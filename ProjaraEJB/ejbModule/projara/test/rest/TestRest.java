package projara.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public interface TestRest {
	
	@Path("/ok")
	@GET
	public String ok();
}
