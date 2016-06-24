package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.util.exception.ActionEventNotExists;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.UserException;
import projara.util.json.view.ActionInfo;
import projara.util.json.view.ActionJson;

public interface ActionRestApi {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getAllActions() throws UserException;

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getActive() throws UserException;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson getActionById(@PathParam("id") int id)
			throws ActionEventNotExists, UserException;

	@GET
	@Path("/subcat")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> applyedToSubcat() throws UserException;

	@GET
	@Path("/subcat/active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> activeApplyedToSubcat() throws UserException;

	@GET
	@Path("/subcat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson oneActionWithApplied(@PathParam("id") int id)
			throws ActionEventNotExists, UserException;

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionJson add(ActionInfo newAction) throws ActionEventNotExists,
			BadArgumentsException, UserException;

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson update(ActionInfo updateInfo)
			throws ActionEventNotExists, UserException;

	@DELETE
	@Path("/{id}")
	public Response deleteAction(@PathParam("id") int id)
			throws ActionEventNotExists, UserException;

	@PUT
	@Path("/addcat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson addCategory(@PathParam("id") int actionId,
			@PathParam("code") String code) throws ActionEventNotExists,
			BadArgumentsException, ItemCategoryException, UserException;

	@DELETE
	@Path("/removecat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson removeCategory(@PathParam("id") int actionId,
			@PathParam("code") String code) throws ActionEventNotExists,
			ItemCategoryException, UserException;

}
