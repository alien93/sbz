package projara.rest.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ItemDaoLocal;
import projara.rest.interfaces.ItemRestApi;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
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
	
	/**
	 * Potrebno je obraditi izuzetke
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemJson> getAllItems(){
		
		try {
			return itemManager.transformItems(itemDao.findAll());
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemCategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Potrebno je obraditi izuzetke
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemJson getItemById(@PathParam("id") int id){
		
		try {
			return itemManager.transformToJson(itemDao.findById(id));
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemCategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Potrebno je obraditi izuzetke
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public List<ItemJson> advancedSearch(AdvancedSearch searchObj){
		
		try {
			return itemManager.transformItems(itemManager.filterItems(searchObj));
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemCategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Potrebno je obraditi izuzetke
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{code}")
	public List<ItemJson> getByCategory(@PathParam("code") String code){
		
		AdvancedSearch advSearch = new AdvancedSearch("", 0, new ItemCategorySearch(), new ItemCostSearch());
		advSearch.getCategory().setCode(code);
		
		return advancedSearch(advSearch);
		
	}

}
