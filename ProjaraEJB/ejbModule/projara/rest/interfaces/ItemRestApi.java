package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.util.json.search.AdvancedSearch;
import projara.util.json.view.ItemJson;

public interface ItemRestApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemJson> getAllItems();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public ItemJson getItemById(@PathParam("id") int id);
	
	//TODO
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public List<ItemJson> advancedSearch(AdvancedSearch searchObj); 
	
	//TODO
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{code}")
	public List<ItemJson> getByCategory(@PathParam("code") String code);
	
}
