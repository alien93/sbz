package projara.rest.interfaces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.util.json.create.WebShopCartJson;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillInfo;

public interface BillRestApi {

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo makeBill(WebShopCartJson webShopCart);
	
	@POST
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo finishBill(BillCostInfo bci);
	
	@DELETE
	@Path("/reject/{id}")
	public Response rejectBill(@PathParam("id") int billId);
	
	@DELETE
	@Path("/cancel/{id}")
	public Response cancelBill(@PathParam("id") int billId);
	
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getAllBills();
	
	@GET
	@Path("/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getBillsByState(@PathParam("state") String state);
	
	@GET
	@Path("/approve/{id}")
	public Response approveBill(@PathParam("id") int billId);
	
	@GET
	@Path("/ok")
	public String ok();
	
	@GET
	@Path("/set")
	public String set();
	
	

}
