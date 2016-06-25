package projara.rest.beans;

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
import projara.model.dao.interfaces.BillDiscountDaoLocal;
import projara.model.dao.interfaces.BillItemDaoLocal;
import projara.model.dao.interfaces.BillItemDiscountDaoLocal;
import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.model.users.User;
import projara.rest.interfaces.BillRestApi;
import projara.session.interfaces.AuthorizationLocal;
import projara.session.interfaces.BillExceptionRecoveryLocal;
import projara.session.interfaces.BillManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.exception.NotEnoughItemsException;
import projara.util.exception.NotEnoughPontsException;
import projara.util.exception.UserException;
import projara.util.json.create.WebShopCartJson;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillInfo;

@Stateless
@Local(BillRestApi.class)
@Path("/bills")
public class BillRestBean implements BillRestApi {

	@Context
	HttpServletRequest request;

	@EJB
	private AuthorizationLocal authorization;

	@EJB
	private BillManagerLocal billManager;

	@EJB
	private BillDaoLocal billDao;

	@EJB
	private BillExceptionRecoveryLocal billRecovery;

	@EJB
	private BillItemDiscountDaoLocal billitemDiscountDao;

	@EJB
	private BillItemDaoLocal billItemDao;

	@EJB
	private BillDiscountDaoLocal billDiscountDao;

	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo makeBill(WebShopCartJson webShopCart) throws UserException,
			BadArgumentsException, BillException, ItemException {
		
		authorization.checkRole("C", request.getSession());
		
		webShopCart.setCustomerId((int) request.getSession().getAttribute("userID"));

		return billManager.populateBill(webShopCart);

	}

	@Override
	@POST
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo finishBill(BillCostInfo bci) throws BillException,
			BadArgumentsException, UserException, ItemException,
			ItemCategoryException {

		authorization.checkRole("C", request.getSession());

		Bill bill = null;
		try {
			bill = billManager.finishOrder(bci.getBillId(), bci);
		} catch (NotEnoughPontsException e) {
			billManager.cancelOrder(bci.getBillId());
			throw e;
		}

		return billManager.transform(bill);
	}

	@Override
	@POST
	@Path("/reject/{id}")
	public Response rejectBill(@PathParam("id") int billId)
			throws BillException, BadArgumentsException, UserException {

		authorization.checkRole("C", request.getSession());

		billManager.rejectOrder(billId);

		return Response.ok().build();
	}

	@Override
	@POST
	@Path("/cancel/{id}")
	public Response cancelBill(@PathParam("id") int billId)
			throws BillException, UserException, BadArgumentsException {

		authorization.checkRole("V", request.getSession());

		billManager.cancelOrder(billId);

		return Response.ok().build();
	}

	@Override
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getAllBills() throws UserException {

		User u = authorization.checkIsLogged(request.getSession());

		// U zavisnosti od uloge dobija odgovarajucu kolekciju
		// User u = new Customer(); // iz sesije izvuce i proveri da li je
		// // ulogovan;
		// u.setId(1);
		// ////////////////////////////

		List<Bill> result = null;

		if (u instanceof Customer) {

			result = billDao.getUserHistory(u.getId());

		} else
			result = billDao.getAll();

		return billManager.transformList(result);
	}

	@GET
	@Path("/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BillInfo> getBillsByState(@PathParam("state") String state)
			throws UserException {

		User u = authorization.checkIsLogged(request.getSession());

		List<Bill> result = null;

		if (u instanceof Customer) {
			try {
				result = billDao.getByStateAndUser(state, u.getId());
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			result = billDao.getByState(state);

		return billManager.transformList(result);

	}

	@Override
	@GET
	@Path("/approve/{id}")
	public Response approveBill(@PathParam("id") int billId)
			throws ItemException, BillException, BadArgumentsException,
			UserException {

		User u = authorization.checkRole("V", request.getSession());

		try {
			billManager.approveOrder(billId);
		} catch (ItemNotExistsException | NotEnoughItemsException e) {
			try {
				billManager.cancelOrder(billId);
			} catch (BillException e1) {
				// TODO Auto-generated catch block
				 e1.printStackTrace();
			} catch (UserException e1) {
				// TODO Auto-generated catch block
				 e1.printStackTrace();
			} catch (BadArgumentsException e1) {
				// TODO Auto-generated catch block
				 e1.printStackTrace();
			}
			throw e;
		}

		return Response.ok().build();
	}

	@Override
	@GET
	@Path("/set")
	public String set() {

		request.getSession().setAttribute("pera", "pera");

		return "OK";

	}

	@Override
	@GET
	@Path("/ok")
	public String ok() {

		return (String) request.getSession().getAttribute("pera");

	}

}
