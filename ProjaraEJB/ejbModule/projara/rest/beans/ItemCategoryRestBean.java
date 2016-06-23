package projara.rest.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.rest.interfaces.ItemCategoryRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.ItemCategoryManagerLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.UserException;
import projara.util.json.view.ItemCategoryJson;

/**
 * 
 * @author Nina
 *
 */
@Stateless
@Local(ItemCategoryRestApi.class)
@Path("/itemCategories")
public class ItemCategoryRestBean implements ItemCategoryRestApi{

	@EJB
	private ItemCategoryManagerLocal itemCategoryManager;
	
	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@EJB
	private AuthorizationLocal authorization;
	
	@Context
	private HttpServletRequest request;
	
	@EJB
	private ItemManagerLocal itemManager;
	
	@GET
	@Path("/test")
	public String test(){
		return "test";
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryJson> getAllItemCategories() throws UserException {
		
		authorization.checkIsLogged(request.getSession());
		
		return itemManager.getTree();
		
		//return itemCategoryManager.transformItems(itemCategoryDao.findAll());
		//return new ArrayList<>();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemCategoryJson getItemCategoryById(@PathParam("id") String id) throws UserException {
		System.out.println("OVA");
		authorization.checkIsLogged(request.getSession());
		System.out.println(id);
		/*
		try {
			return itemCategoryManager.transformToJson(itemCategoryDao.findById(id));
		} catch (ItemCategoryException | BadArgumentsException e) {
			e.printStackTrace();
		}
		return null;
		*/
		
		return itemManager.getCategoryById(id);
	}
}
