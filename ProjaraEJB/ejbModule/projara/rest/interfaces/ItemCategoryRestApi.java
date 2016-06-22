package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.util.json.view.ItemCategoryJson;

/**
 * 
 * @author Nina
 *
 */
public interface ItemCategoryRestApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemCategoryJson> getAllItemCategories();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemCategoryJson getItemCategoryById(@PathParam("id") String id);
	
	
}
