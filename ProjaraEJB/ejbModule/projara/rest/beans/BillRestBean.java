package projara.rest.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.model.users.User;
import projara.rest.interfaces.BillRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.BillManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
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
	
	@EJB
	private BillManagerLocal billManager;
	
	@EJB
	private BillDaoLocal billDao;
	
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo makeBill(WebShopCartJson webShopCart){
		
		try {
			BillInfo info = billManager.populateBill(webShopCart);
			return info;
		} catch (BillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	@POST
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo finishBill(BillCostInfo bci){
		
		Bill bill  = null;
		try {
			bill = billManager.finishOrder(bci.getBillId(), bci);
		} catch (BillException e) {
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return billManager.transform(bill);
		} catch (BillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemCategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	@POST
	@Path("/reject/{id}")
	public Response rejectBill(@PathParam("id") int billId){
		
		try {
			billManager.rejectOrder(billId);
		} catch (BillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@POST
	@Path("/cancel/{id}")
	public Response cancelBill(@PathParam("id") int billId){
		
		try {
			billManager.cancelOrder(billId);
		} catch (BillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getAllBills(){
		
		//U zavisnosti od uloge dobija odgovarajucu kolekciju
		User u = new Customer(); // iz sesije izvuce i proveri da li je ulogovan;
		u.setId(1);
		//////////////////////////////
		
		List<Bill> result = null;
		
		if(u instanceof Customer){
			try {
				result = billDao.getUserHistory(u.getId());
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			result = billDao.getAll();
		
		
		return billManager.transformList(result);
	}
	
	@GET
	@Path("/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getBillsByState(@PathParam("state") String state){
		
		//U zavisnosti od uloge dobija odgovarajucu kolekciju
		User u = new Customer(); // iz sesije izvuce i proveri da li je ulogovan;
		u.setId(1);
		
		List<Bill> result = null;
		
		if(u instanceof Customer){
			try {
				result = billDao.getByStateAndUser(state, u.getId());
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			result = billDao.getByState(state);
		
		return billManager.transformList(result);
		
	}
	
	
	
	@Override
	@GET
	@Path("/approve/{id}")
	public Response approveBill(@PathParam("id") int billId){
		
		try {
			billManager.approveOrder(billId);
		} catch (BillException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadArgumentsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public String ok(){
		
		return (String) request.getSession().getAttribute("pera");
		
	}

}
