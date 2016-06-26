package projara.rest.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jess.JessException;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.users.User;
import projara.model.users.Vendor;
import projara.rest.interfaces.ItemRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
import projara.util.json.create.CreateItemForm;
import projara.util.json.search.AdvancedSearch;
import projara.util.json.search.ItemCategorySearch;
import projara.util.json.search.ItemCostSearch;
import projara.util.json.view.ItemJson;

@Stateless
@Local(ItemRestApi.class)
@Path("/items")
public class ItemRestBean implements ItemRestApi {

	@EJB
	private ItemManagerLocal itemManager;

	@EJB
	private ItemDaoLocal itemDao;

	@EJB
	private AuthorizationLocal authorization;

	@Context
	private HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemJson> getAllItems() throws UserException {

		User u = authorization.checkIsLogged(request.getSession());

		if (u instanceof Vendor)
			try {
				itemManager.automaticOrdering();
			} catch (ItemException | JessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		try {
			return itemManager.transformItems(itemDao.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<ItemJson>();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemJson getItemById(@PathParam("id") int id) throws UserException,
			ItemException, BadArgumentsException, ItemCategoryException {

		authorization.checkIsLogged(request.getSession());

		User u = authorization.checkIsLogged(request.getSession());

		if (u instanceof Vendor)
			try {
				itemManager.automaticOrdering();
			} catch (ItemException | JessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return itemManager.transformToJson(itemDao.findById(id));

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public List<ItemJson> advancedSearch(AdvancedSearch searchObj)
			throws UserException {

		authorization.checkIsLogged(request.getSession());

		User u = authorization.checkIsLogged(request.getSession());

		if (u instanceof Vendor)
			try {
				itemManager.automaticOrdering();
			} catch (ItemException | JessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		try {
			return itemManager.transformItems(itemManager
					.filterItems(searchObj));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{code}")
	public List<ItemJson> getByCategory(@PathParam("code") String code)
			throws UserException {

		authorization.checkIsLogged(request.getSession());

		User u = authorization.checkIsLogged(request.getSession());

		if (u instanceof Vendor)
			try {
				itemManager.automaticOrdering();
			} catch (ItemException | JessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return itemManager.getAllByCategory(code);

	}

	@Override
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson addNewItem(@MultipartForm CreateItemForm newItem)
			throws ItemException, ItemCategoryException, BadArgumentsException,
			UserException {
		
		authorization.checkRole("V", request.getSession());

		return itemManager.formToItem(newItem);

	}

	@Override
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson updateItem(@MultipartForm CreateItemForm itemForm)
			throws ItemException, ItemCategoryException, BadArgumentsException,
			UserException {

		authorization.checkRole("V", request.getSession());

		return itemManager.updateItemForm(itemForm);
	}

	@Override
	@POST
	@Path("/order")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson orderItems(@FormParam("id") int itemId,
			@FormParam("quantity") int quantity) throws ItemException,
			BadArgumentsException, ItemCategoryException, UserException {

		authorization.checkRole("V", request.getSession());

		return itemManager.transformToJson(itemManager.order(itemId, quantity));
	}

	/*
	 * Ne brise se item iz baze, samo se stavi naznaka da vise nije aktivan
	 */
	@Override
	@DELETE
	@Path("/{id}")
	public Response deleteItem(@PathParam("id") int itemId)
			throws ItemException, UserException {

		authorization.checkRole("V", request.getSession());

		itemManager.setActive(itemId, false);

		return Response.ok().build();
	}

	@Override
	@PUT
	@Path("/{id}")
	public Response addExisting(@PathParam("id") int itemId)
			throws ItemException, UserException {

		authorization.checkRole("V", request.getSession());

		itemManager.setActive(itemId, true);

		return Response.ok().build();
	}

}
