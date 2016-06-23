package projara.util.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import projara.util.exception.BadArgumentsException;

@Provider
public class BadArgumentsExceptionMapper
		implements
			ExceptionMapper<BadArgumentsException> {

	@Override
	public Response toResponse(BadArgumentsException arg0) {
		ResponseError error = new ResponseError();

		error.setName(arg0.getClass().getSimpleName());
		error.setMessage(arg0.getMessage());
		error.setStatus(Status.BAD_REQUEST.getStatusCode());

		return Response.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON).entity(error).build();
	}

}
