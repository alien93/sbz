package projara.util.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import projara.util.exception.UserRoleException;

@Provider
public class UserRoleExceptionMapper
		implements
			ExceptionMapper<UserRoleException> {

	@Override
	public Response toResponse(UserRoleException arg0) {
		ResponseError error = new ResponseError();

		error.setName(getClass().getSimpleName());
		error.setMessage(arg0.getMessage());
		error.setStatus(Status.UNAUTHORIZED.getStatusCode());

		return Response.status(Status.UNAUTHORIZED)
				.type(MediaType.APPLICATION_JSON).entity(error).build();
	}

}
