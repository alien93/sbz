package projara.rest.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

import projara.model.users.User;
import projara.rest.interfaces.UserRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.UserManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.UserException;
import projara.util.exception.UserNotLoggedException;
import projara.util.json.view.UserProfileInfoJson;

@Stateless
@Local(UserRestApi.class)
@Path("/user")
public class UserRestBean implements UserRestApi {

	@EJB
	private UserManagerLocal userManager;

	@EJB
	private AuthorizationLocal authorization;

	@Context
	private HttpServletRequest request;

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson login(@FormParam("username") String username,
			@FormParam("password") String password) throws UserException,
			BadArgumentsException {

		try {
			authorization.checkIsLogged(request.getSession());
			throw new UserException("Already logged");
		} catch (UserException e1) {
		}

		User u = userManager.login(username, password);

		request.getSession().setAttribute("userID", u.getId());

		return userManager.transformToJson(u);

	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("role") String role) throws UserException,
			BadArgumentsException {

		try {
			authorization.checkIsLogged(request.getSession());
			throw new UserException("Already logged");
		} catch (UserException e) {
			// OK
		}

		User u = userManager.registerUser(username, password, role, firstName,
				lastName);

		request.getSession().setAttribute("userID", u.getId());

		return userManager.transformToJson(u);

	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson update(UserProfileInfoJson userProfile)
			throws UserException, BadArgumentsException {

		authorization.checkIsLogged(request.getSession());

		User u = userManager.updateUser(userProfile);
		return userManager.transformToJson(u);

	}

	@POST
	@Path("/logout")
	public Response logout() throws UserException {

		authorization.checkIsLogged(request.getSession());

		request.getSession().setAttribute("userID", null);

		return Response.ok().build();
	}

}
