package projara.rest.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.rest.interfaces.ItemCategoryRestApi;
import projara.session.interfaces.ItemCategoryManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
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
	
	@GET
	@Path("/test")
	public String test(){
		return "test";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryJson> getAllItemCategories() {
		try {
			return itemCategoryManager.transformItems(itemCategoryDao.findAll());
		} catch (ItemCategoryException | BadArgumentsException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemCategoryJson getItemCategoryById(@PathParam("id") String id) {
		try {
			return itemCategoryManager.transformToJson(itemCategoryDao.findById(id));
		} catch (ItemCategoryException | BadArgumentsException e) {
			e.printStackTrace();
		}
		return null;
	}
}
