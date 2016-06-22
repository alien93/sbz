package projara.rest.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.util.json.view.UserProfileInfoJson;

public interface UserRestApi {

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson login(@FormParam("username") String username,
			@FormParam("password") String password);

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName, @FormParam("role") String role);
	
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson update(UserProfileInfoJson userProfile);
	
	@POST
	@Path("/logout")
	public Response logout();

}
