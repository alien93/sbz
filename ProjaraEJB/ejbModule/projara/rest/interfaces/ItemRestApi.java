package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
import projara.util.json.search.AdvancedSearch;
import projara.util.json.view.ItemJson;

public interface ItemRestApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemJson> getAllItems() throws UserException;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemJson getItemById(@PathParam("id") int id) throws UserException, ItemException, BadArgumentsException, ItemCategoryException;
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public List<ItemJson> advancedSearch(AdvancedSearch searchObj) throws UserException; 
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{code}")
	public List<ItemJson> getByCategory(@PathParam("code") String code) throws UserException;
	
}
