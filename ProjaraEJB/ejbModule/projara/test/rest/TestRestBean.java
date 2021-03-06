package projara.test.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jess.JessException;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

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
import projara.session.interfaces.ActionManagerLocal;
import projara.session.interfaces.BillManagerLocal;
import projara.session.interfaces.CustomerCategoryManagerLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.session.interfaces.UserManagerLocal;
import projara.test.TestEntity;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
import projara.util.json.search.AdvancedSearch;
import projara.util.json.search.ItemCategorySearch;
import projara.util.json.search.ItemCostSearch;
import projara.util.json.view.BillInfo;
import projara.util.json.view.ItemCategoryJson;
import projara.util.json.view.ItemJson;

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
	
	@EJB
	private ActionManagerLocal actionManager;

	@Override
	@GET
	@Path("/ok")
	public String ok() {
		System.out.println("OK REST");
		File file = new File(".");
		System.out.println(file.getAbsolutePath());
		System.out.println(getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath());
		String papath = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()+"/../ProjaraWeb.war";
		
		try {
			System.out.println(new File(papath).getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
		BillItem billIt2 = new BillItem(250.0, 2, 500, 500, item2, bill1);

		billItem.persist(billIt1);
		billItem.persist(billIt2);

		bill1.setAwardPoints((short) 3);
		bill1.setOriginalTotal(2500.00);
		bill1.setTotal(2500.00);

		bill1 = bill.merge(bill1);
	}

	@Path("/test/register")
	@POST
	public Response testRegisterCustomer(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName) {

		try {
			Customer cust = (Customer) userManager.registerUser(username,
					password, "C", firstName, lastName);
		} catch (UserException e) {
			return Response.status(400).entity(e.getMessage()).build();
		} catch (BadArgumentsException e) {
			return Response.status(400).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	@Path("/test/login")
	@POST
	public Response testLogin(@FormParam("username") String username,
			@FormParam("password") String password, @FormParam("role") String role) {
		System.out.println("Username :" + username + " Password :" + password + "Role: " + role);
		User u = null;
		try {
			u = userManager.login(username, password, role);
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build();
		}

		if (u == null)
			return Response.status(400).entity("Not successful").build();

		return Response.ok().build();
	}

	@Path("/test/events")
	@GET
	public void getEvents() {
		List<ActionEvent> events = actionEvent.findActiveEvents();
		for (ActionEvent e : events) {
			System.out.println(e.getName());
		}
	}

	@Path("/test/dummyBill")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BillInfo makeDummyBill() throws CustomerCategoryException,
			BadArgumentsException, UserException, ItemCategoryException,
			ItemException, BillException, JessException {
		CustomerCategory cZlatni = custCatManager.makeCustomerCategory("A",
				"Zlatni");

		Customer cust1 = (Customer) userManager.registerUser("pera", "123",
				"C", "Pera", "PerićčšđžŽĐŠČĆ");

		Threshold t = custCatManager.makeThreshold(20000, 400000, 1.0);
		cZlatni = custCatManager.addThreshold(cZlatni, t);
		cust1 = userManager.setCustomerCategory(cust1, cZlatni);

		cust1.setPoints(3000);

		cust1 = (Customer) user.persist(cust1);

		ItemCategory ic = itemManager.makeItemCategory((ItemCategory) null,
				"A", "Siroka potrosnja", 10.0);
		ItemCategory ic2 = itemManager.makeItemCategory(ic, "B",
				"Podkategorija siroke", 5.0);
		ItemCategory ic3 = itemManager.makeItemCategory((ItemCategory) null,
				"C", "Televizori, racunari, laptopovi", 15.0);
		
		ItemCategory ic4 = itemManager.makeItemCategory(ic2, "D", "Druga dubina siroke", 10);

		Item i1 = itemManager.addItem("Item 1", 5000.0, 200, 20, ic2);
		Item i2 = itemManager.addItem("Item 2", 40000.00, 10, 5, ic3);
		Item i3 = itemManager.addItem("Item 3", 20000.0, 30, 45, ic);
		Item i4 = itemManager.addItem("Item 4", 400.00, 30, 10, ic3);
		Item i5 = itemManager.addItem("Item 5", 200.00, 20, 12, ic3);
		Item i6 = itemManager.addItem("Tralal", 2000.00, 20, 10, ic4);

		Calendar calB = Calendar.getInstance();
		calB.set(2016, 5, 1);
		Calendar calA = Calendar.getInstance();
		calA.set(2016, 6, 1);

		ActionEvent ae = actionManager.createActionEvent("Letnji popust",
				calB.getTime(), calA.getTime(), 15.0);

		ae = actionManager.addCategoryToAction(ae, ic3);
		
		/*
		Bill bill1 = billManager.createBill(cust1);

		BillItem bi1 = billManager.addBillItem(bill1, i1, 2);

		BillItem bi2 = billManager.addBillItem(bill1, i2, 3);

		BillItem bi3 = billManager.addBillItem(bill1, i4, 21);

		BillItem bi4 = billManager.addBillItem(bill1, i5, 6);

		BillInfo bi = billManager.calculateCost(bill1, (short) 2390);

		return bi;
		*/
		return null;
		
		/*
		BillCostInfo withPoints = bi.getCostInfos().get(0);

		billManager.finishOrder(bill1, withPoints);

		billManager.approveOrder(bill1);

		itemManager.automaticOrdering();
		*/
	}

	@Path("/test/query")
	@GET
	public void testQuery() {

		// VRACA SVE koji u nazivu imaju tem
		AdvancedSearch advSearch1 = new AdvancedSearch("tem", 0,
				"", new ItemCostSearch());
		List<Item> q1 = itemManager.filterItems(advSearch1);
		System.out.println("Svi koji imaju u nazivu 'tem'");
		printListItem(q1);

		advSearch1.setName("");
		advSearch1.setId(1);
		System.out.println("SA ID 1");
		printListItem(itemManager.filterItems(advSearch1));

		advSearch1.setId(-1);
		advSearch1.setCategory("sirok");
		System.out.println("Svi kategorije siroke potrosnje");
		printListItem(itemManager.filterItems(advSearch1));

		advSearch1.setCategory("");
		advSearch1.getCostRange().setMaxCost(15000);
		System.out.println("DO 15000");
		printListItem(itemManager.filterItems(advSearch1));

		advSearch1.setCategory("televiz");
		advSearch1.getCostRange().setMinCost(100);
		System.out.println("Televizori od 3000 do 15000");
		printListItem(itemManager.filterItems(advSearch1));

	}

	private void printListItem(List<Item> list) {
		if (list == null || list.isEmpty())
			System.out.println("PRAZNO");
		for (Item i : list) {
			System.out.println(i.getId() + " " + i.getName() + " "
					+ i.getPrice() + " " + i.getCategory().getCode() + " "
					+ i.getCategory().getName());
		}
		System.out.println("********************************");
	}

	@Path("/test/imgupload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@MultipartForm TestEntity test) {
		String path = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()
				+ "/projara/resources/images/";

		String warPath = getClass().getProtectionDomain().getCodeSource()
		.getLocation().getPath()+"/../ProjaraWeb.war/images";
		File file = new File(warPath);

		try (FileOutputStream fos = new FileOutputStream(new File(warPath+"/"
				+ "test.jpg"))) {
			byte[] fileBytes = test.getFile();
			fos.write(fileBytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@GET
	@Path("/test/items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemJson getItemById(@PathParam("id") int id){
		
		try {
			return itemManager.transformToJson(item.findById(id));
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
	
	@GET
	@Path("/test/items")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemJson> getItems(){
		
		try {
			return itemManager.transformItems(item.findAll());
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
	
	@Override
	@GET
	@Path("/test/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemCategoryJson> getAllCategories(){
		return itemManager.getTree();
	}

	@Override
	public Response testLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}


}
