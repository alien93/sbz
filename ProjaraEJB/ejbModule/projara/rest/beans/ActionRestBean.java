package projara.rest.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.rest.interfaces.ActionRestApi;
import projara.session.interfaces.ActionManagerLocal;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.ActionEventNotExists;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.UserException;
import projara.util.json.view.ActionInfo;
import projara.util.json.view.ActionJson;

@Path("/actions")
@Stateless
@Local(ActionRestApi.class)
public class ActionRestBean implements ActionRestApi {

	@Context
	private HttpServletRequest request;

	@EJB
	private AuthorizationLocal authorization;

	@EJB
	private ItemManagerLocal itemManager;

	@EJB
	private ItemCategoryDaoLocal itemCatDao;

	@EJB
	private ActionManagerLocal actionManager;

	@EJB
	private ActionEventDaoLocal actionDao;
	

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getAllActions() throws UserException {
		
		authorization.checkIsLogged(request.getSession());
		
		return actionManager.transformActions(actionDao.findAll());

	}

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getActive() throws UserException {

		authorization.checkIsLogged(request.getSession());
		
		return actionManager.transformActions(actionDao.findActiveEvents());
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson getActionById(@PathParam("id") int id)
			throws ActionEventNotExists, UserException {

		authorization.checkIsLogged(request.getSession());
		
		return actionManager.transformAction(id);
	}

	@GET
	@Path("/subcat")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> applyedToSubcat() throws UserException {

		authorization.checkIsLogged(request.getSession());
		
		return actionManager.getActionAppliedToSub(actionDao.findAll());

	}

	@GET
	@Path("/subcat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson oneActionWithApplied(@PathParam("id") int id)
			throws ActionEventNotExists, UserException {
		
		authorization.checkIsLogged(request.getSession());
		
		return actionManager.getActionAppliedToSub(id);
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionJson add(ActionInfo newAction) throws ActionEventNotExists,
			BadArgumentsException, UserException {

		authorization.checkRole("M", request.getSession());
		
		return actionManager.transformAction(actionManager.createActionEvent(
				newAction.getName(), newAction.getFrom(), newAction.getUntil(),
				newAction.getDicount()));
	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionJson update(ActionInfo update) throws ActionEventNotExists, UserException {

		authorization.checkRole("M", request.getSession());
		
		return actionManager.transformAction(actionManager.updateAction(
				update.getId(), update.getName(), update.getFrom(),
				update.getUntil(), update.getDicount()));

	}

	@DELETE
	@Path("/{id}")
	public Response deleteAction(@PathParam("id") int id)
			throws ActionEventNotExists, UserException {

		authorization.checkRole("M", request.getSession());
		
		actionManager.removeAction(id);

		return Response.ok().build();
	}

	@PUT
	@Path("/addcat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson addCategory(@PathParam("id") int actionId,
			@PathParam("code") String code) throws ActionEventNotExists,
			BadArgumentsException, ItemCategoryException, UserException {

		authorization.checkRole("M", request.getSession());
		
		return actionManager.transformAction(actionManager.addCategoryToAction(
				actionId, code));
	}

	@DELETE
	@Path("/removecat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson removeCategory(@PathParam("id") int actionId,
			@PathParam("code") String code) throws ActionEventNotExists,
			ItemCategoryException, UserException {

		authorization.checkRole("M", request.getSession());
		
		return actionManager.transformAction(actionManager
				.removeCategoryFromAction(actionId, code));
	}

	@GET
	@Path("/subcat/active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> activeApplyedToSubcat() throws UserException {
		
		authorization.checkIsLogged(request.getSession());
		
		return actionManager
				.getActionAppliedToSub(actionDao.findActiveEvents());
	}

}
