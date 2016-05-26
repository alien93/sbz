package projara.test.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("/test")
public class TestRestBean implements TestRest {

	@Override
	@GET
	@Path("/ok")
	public String ok() {
		System.out.println("OK REST");
		return "JEEEEEEEEE";
	}

}
