package projara.rest.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.rest.interfaces.BillRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.util.interceptors.IsLoggedInterception;
import projara.util.json.create.WebShopCartJson;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillInfo;

@Stateless
@Local(BillRestApi.class)
@Path("/bills")
public class BillRestBean implements BillRestApi {
	
	@Context HttpServletRequest request;
	
	@EJB
	private AuthorizationLocal authorization;
	
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo makeBill(WebShopCartJson webShopCart){
		
		return null;
	}
	
	@Override
	@POST
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo finishmBill(BillCostInfo bci){
		
		return null;
	}
	
	@Override
	@DELETE
	@Path("/reject/{id}")
	public Response rejectBill(@PathParam("id") int billId){
		
		return null;
	}
	
	@Override
	@DELETE
	@Path("/cancel/{id}")
	public Response cancelBill(@PathParam("id") int billId){
		
		return null;
	}
	
	@Override
	@GET
	@Path("/{type}")
	public List<BillInfo> getBillsByType(@PathParam("type") String type){
		
		return null;
	}
	
	@Override
	@GET
	@Path("/approve/{id}")
	public Response approveBill(@PathParam("id") String billId){
		
		return null;
	}
	
	@Override
	@GET
	@Path("/set")
	public String set(){
		
		request.getSession().setAttribute("pera", "pera");
		
		
		return "OK";
		
	}
	
	@Override
	@GET
	@Path("/ok")
	@Interceptors({IsLoggedInterception.class})
	public String ok(){
		
		return (String) request.getSession().getAttribute("pera");
		
	}

}
