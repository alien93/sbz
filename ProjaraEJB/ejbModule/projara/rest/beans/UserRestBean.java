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
			@FormParam("password") String password) {

		try {
			authorization.checkIsLogged(request.getSession());
			// VEC JE ULOGOVAN
			// RETURN GRESKU
			return null;
		} catch (UserException e1) {
		}

		try {
			User u = userManager.login(username, password);
			
			request.getSession().setAttribute("userID", u.getId());
			
			return userManager.transformToJson(u);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("role") String role) {

		try {
			authorization.checkIsLogged(request.getSession());
			// VEC JE ULOGOVAN
			// RETURN GRESKU
			return null;
		} catch (UserException e1) {
		}

		try {
			User u = userManager.registerUser(username, password, role,
					firstName, lastName);
			
			request.getSession().setAttribute("userID", u.getId());
			
			return userManager.transformToJson(u);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserProfileInfoJson update(UserProfileInfoJson userProfile) {

		try {
			authorization.checkIsLogged(request.getSession());
		} catch (UserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//RETURN
			//NIJE USPESNO
		}
		
		try {
			User u = userManager.updateUser(userProfile);
			return userManager.transformToJson(u);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@POST
	@Path("/logout")
	public Response logout(){
		
		try {
			authorization.checkIsLogged(request.getSession());
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//greska return
		}
		
		request.getSession().setAttribute("userID", null);
		
		return Response.ok().build();
	}


}
