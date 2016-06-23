package projara.rest.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ItemDaoLocal;
import projara.rest.interfaces.ItemRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
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

		authorization.checkIsLogged(request.getSession());

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
	public ItemJson getItemById(@PathParam("id") int id) throws UserException, ItemException, BadArgumentsException, ItemCategoryException {

		authorization.checkIsLogged(request.getSession());

		return itemManager.transformToJson(itemDao.findById(id));

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public List<ItemJson> advancedSearch(AdvancedSearch searchObj) throws UserException {

		authorization.checkIsLogged(request.getSession());
		
		try {
			return itemManager.transformItems(itemManager
					.filterItems(searchObj));
		} catch (Exception e){e.printStackTrace();}

		return new ArrayList<>();

	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{code}")
	public List<ItemJson> getByCategory(@PathParam("code") String code)
			throws UserException {

		authorization.checkIsLogged(request.getSession());
		
		AdvancedSearch advSearch = new AdvancedSearch("", 0,
				new ItemCategorySearch(), new ItemCostSearch());
		advSearch.getCategory().setCode(code);

		return advancedSearch(advSearch);

	}

}
