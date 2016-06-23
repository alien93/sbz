package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
import projara.util.json.create.CreateItemForm;
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
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson addNewItem(@MultipartForm CreateItemForm newItem)
			throws ItemException, ItemCategoryException, BadArgumentsException, UserException;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson updateItem(@MultipartForm CreateItemForm itemForm)
			throws ItemException, ItemCategoryException, BadArgumentsException, UserException;
	
	@POST
	@Path("/order")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson orderItems(@FormParam("id") int itemId,
			@FormParam("quantity") int quantity) throws ItemException,
			BadArgumentsException, ItemCategoryException, UserException;

	@DELETE
	@Path("/{id}")
	Response deleteItem(int itemId) throws ItemException, UserException;

	@PUT
	@Path("/{id}")
	Response addExisting(int itemId) throws ItemException, UserException;
	
}
