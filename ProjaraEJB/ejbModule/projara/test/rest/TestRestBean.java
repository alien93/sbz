package projara.test.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.dao.interfaces.BillDiscountDaoLocal;
import projara.model.dao.interfaces.BillItemDaoLocal;
import projara.model.dao.interfaces.BillItemDiscountDaoLocal;
import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.dao.interfaces.ThresholdDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.Manager;
import projara.model.users.Threshold;
import projara.model.users.User;
import projara.model.users.Vendor;
import projara.session.interfaces.BillManagerLocal;
import projara.session.interfaces.CustomerCategoryManagerLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.session.interfaces.UserManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;

@Stateless
@Path("/test")
public class TestRestBean implements TestRest {

	@EJB
	private ActionEventDaoLocal actionEvent;
	@EJB
	private BillDaoLocal bill;
	@EJB
	private BillDiscountDaoLocal billDiscount;
	@EJB
	private BillItemDaoLocal billItem;
	@EJB
	private BillItemDiscountDaoLocal billItemDiscount;
	@EJB
	private CustomerCategoryDaoLocal customerCategory;
	@EJB
	private ItemCategoryDaoLocal itemCategory;
	@EJB
	private ItemDaoLocal item;
	@EJB
	private ThresholdDaoLocal threshold;
	@EJB
	private UserDaoLocal user;
	
	@EJB
	private BillManagerLocal billManager;
	
	@EJB
	private UserManagerLocal userManager;
	
	@EJB
	private CustomerCategoryManagerLocal custCatManager;
	
	@EJB
	private ItemManagerLocal itemManager;

	@Override
	@GET
	@Path("/ok")
	public String ok() {
		System.out.println("OK REST");
		return "JEEEEEEEEE";
	}

	@Override
	@Path("/init")
	@GET
	public void init() {
		/*
		 * CUSTOMER CATEGORY
		 */
		CustomerCategory c1 = new CustomerCategory("A", "Kategorija A");
		CustomerCategory c2 = new CustomerCategory("B", "Kategorija B");

		c1 = customerCategory.persist(c1);
		c2 = customerCategory.persist(c2);
		/*
		 * 
		 */
		/*
		 * USERS
		 */
		User u1 = new Customer("pera", "Pera", "Peric", "Adresa 1", "123456");
		User u2 = new Customer("mika", "Mika", "Mikić", "Adresa 2", "123456");
		User u3 = new Manager("menadzerko", "Menadzerko", "Menadzerkovic",
				"123456");
		User u4 = new Vendor("vendor", "Vendor", "Vendoric", "123");

		c1.addCustomers((Customer) u1);
		c2.addCustomers((Customer) u2);

		u1 = user.persist(u1);
		u2 = user.persist(u2);
		u3 = user.persist(u3);
		u4 = user.persist(u4);

		/**/
		/*
		 * THRESHOLD
		 */
		Threshold threshold1 = new Threshold(100, 200, 15.2);
		Threshold threshold2 = new Threshold(200, 500, 12.23);
		Threshold threshold3 = new Threshold(500, 1000, 30.50);

		c1.addThresholds(threshold1);
		c2.addThresholds(threshold2);
		c1.addThresholds(threshold2);
		c1.addThresholds(threshold3);

		threshold1 = threshold.persist(threshold1);
		threshold2 = threshold.persist(threshold2);
		threshold3 = threshold.persist(threshold3);
		/**/
		/* ITEM CATEGORY */
		ItemCategory ic1 = new ItemCategory("AAA", "Parent", 20);
		ItemCategory ic2 = new ItemCategory("AAB", "Child", 10, ic1);

		ic1 = itemCategory.persist(ic1);
		ic2 = itemCategory.persist(ic2);

		/***/
		/* ITEM */
		Item item1 = new Item("Item 1", 200.00, 30, ic1);
		Item item2 = new Item("Item 2", 250.00, 10, ic2);
		Item item3 = new Item("Item 3", 5000.00, 100, ic1);

		item1 = item.persist(item1);
		item2 = item.persist(item2);
		item3 = item.persist(item3);
		/***/
		Bill bill1 = new Bill("O", (Customer) u1);
		bill1 = bill.persist(bill1);
		
		BillItem billIt1 = new BillItem(item1.getPrice(), 10,
				item1.getPrice() * 10, item1.getPrice() * 10, item1, bill1);
		BillItem billIt2 = new BillItem(250.0,2,500,500,item2,bill1);
		
		billItem.persist(billIt1);
		billItem.persist(billIt2);
		
		bill1.setAwardPoints((short)3);
		bill1.setOriginalTotal(2500.00);
		bill1.setTotal(2500.00);
		
		bill1 = bill.merge(bill1);
	}
	
	@Path("/test/register")
	@POST
	public Response testRegisterCustomer(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName){
		
		try {
			Customer cust = (Customer) userManager.registerUser(username, password, "C", firstName, lastName);
		} catch (UserException e) {
			 return Response.status(400).entity(e.getMessage()).build();
		} catch (BadArgumentsException e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
		
		return Response.ok().build();
	}
	
	@Path("/test/login")
	@POST
	public Response testLogin(@FormParam("username") String username,@FormParam("password")String password){
		System.out.println("Username :"+username+" Password :"+password);
		User u = null;
		try {
			u = userManager.login(username, password);
		} catch (Exception e){
			return Response.status(400).entity(e.getMessage()).build();
		}
		
		if(u == null)
			return Response.status(400).entity("Not successful").build();
		
		return Response.ok().build();
	}
	
	@Path("/test/events")
	@GET
	public void getEvents(){
		List<ActionEvent> events = actionEvent.findActiveEvents();
		for(ActionEvent e:events){
			System.out.println(e.getName());
		}
	}
	
	@Path("/test/dummyBill")
	@GET
	public void makeDummyBill() throws CustomerCategoryException, BadArgumentsException, UserException, ItemCategoryException, ItemException{
		CustomerCategory cZlatni = custCatManager.makeCustomerCategory("A", "Zlatni");
		
		Customer cust1 = (Customer) userManager.registerUser("pera", "123", "C", "Pera", "Peric");
		
		Threshold t = custCatManager.makeThreshold(2000, 4000, 10.0);
		cZlatni = custCatManager.addThreshold(cZlatni, t);
		cust1 = userManager.setCustomerCategory(cust1, cZlatni);
		
		ItemCategory ic = itemManager.makeItemCategory((ItemCategory)null, "A", "Široka potrošnja", 10.0);
		ItemCategory ic2 = itemManager.makeItemCategory(ic, "B", "Podkategorija široke", 5.0);
		ItemCategory ic3 = itemManager.makeItemCategory((ItemCategory)null, "C", "Televizori, laptopovi", 15.0);
		
		Item i1 = itemManager.addItem("Item 1", 5000.0, 200, 20, ic2);
		Item i2 = itemManager.addItem("Item 2", 40000.00, 10, 5, ic3);
		Item i3 = itemManager.addItem("Item 3", 20000.0, 30, 45, ic);
		
		
		
		
	}

}
