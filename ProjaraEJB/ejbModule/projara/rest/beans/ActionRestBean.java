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

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.rest.interfaces.ActionRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.ItemManagerLocal;
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
	
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getAllActions(){
		return null;
	}

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> getActive(){
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson getActionById(@PathParam("id") int id){
		return null;
	}

	@GET
	@Path("/subcat")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActionJson> applyedToSubcat(){
		return null;
	}

	@GET
	@Path("/subcat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson oneActionWithApplied(@PathParam("id") int id){
		return null;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionJson add(ActionJson newAction){
		return null;
	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionJson update(ActionJson update){
		return null;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteAction(@PathParam("id") int id){
		return null;
	}

	@PUT
	@Path("/addcat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson addCategory(@PathParam("id") int actionId,
			@PathParam("code") String code){
		return null;
	}
	
	@DELETE
	@Path("/removecat/{id}/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public ActionJson removeCategory(@PathParam("id") int actionId,
			@PathParam("code") String code){
		return null;
	}

}
