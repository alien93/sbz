package projara.test.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.model.shop.ActionEvent;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;

public interface TestRest {

	@Path("/ok")
	@GET
	public String ok();

	@Path("/init")
	@GET
	public void init();

	@Path("/test/register")
	@POST
	public Response testRegisterCustomer(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName);

	@Path("/test/login")
	@POST
	public Response testLogin(@FormParam("username") String username,
			@FormParam("password") String password);
	
	@Path("/test/events")
	@GET
	public void getEvents();
	
	@Path("/test/dummyBill")
	@GET
	public void makeDummyBill() throws CustomerCategoryException, BadArgumentsException, UserException, ItemCategoryException, ItemException;
}
