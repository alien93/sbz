package projara.test.rest;

import java.util.Locale.Category;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.Manager;
import projara.model.users.Threshold;
import projara.model.users.User;
import projara.model.users.Vendor;

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

}