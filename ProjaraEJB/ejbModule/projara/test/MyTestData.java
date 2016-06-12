package projara.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import jess.Filter;
import jess.Rete;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.shop.BillItemDiscount;
import projara.model.users.Customer;

public class MyTestData {
	private HashMap<String, ItemCategory> categories;
	private HashMap<Integer, Item> items;
	private HashMap<Integer, Bill> bills;
	private HashMap<Integer, Customer> customers;
	private HashMap<Short,ActionEvent> actions;

	public MyTestData() {
		categories = new HashMap<>();
		items = new HashMap<>();
		bills = new HashMap<>();
		customers = new HashMap<>();
		actions = new HashMap<>();

		populateData();
	}

	private void populateData() {

		Customer cust = new Customer("pera", "Pera", "Peric", "Adresa",
				"123456");
		cust.setId(1);

		customers.put(cust.getId(), cust);

		/* CATEGORIES */
		ItemCategory ic1 = new ItemCategory();
		ic1.setCode("SPP");
		ic1.setName("Široka potrošnja");
		ic1.setMaxDiscount(10);

		ItemCategory ic2 = new ItemCategory();
		ic2.setCode("SPA");
		ic2.setName("Child siroke");
		ic2.setMaxDiscount(5);

		ic1.addSubCategories(ic2);

		ItemCategory ic3 = new ItemCategory();
		ic3.setCode("AAA");
		ic3.setName("Televizori, računari, laptopovi");
		ic3.setMaxDiscount(20);

		categories.put(ic1.getCode(), ic1);
		categories.put(ic2.getCode(), ic2);
		categories.put(ic3.getCode(), ic3);
		
		/*ACTIONS*/
		
		ActionEvent ev = new ActionEvent();
		ev.addCategories(ic1);
		ev.setDiscount(3);
		ev.setId((short)1);
		
		actions.put(ev.getId(), ev);

		/* ITEMS */
		Item i1 = new Item(" Item 1 - siroka potrosnja", 100, 50, ic1);
		i1.setCreatedOn(new Date());
		i1.setId(1);

		Item i2 = new Item(" Item 2 - siroka potrosnja", 100, 50, ic2);
		i2.setCreatedOn(new Date());
		i2.setId(2);

		Item i3 = new Item(" Item 3 - televizori", 200, 19, ic3);
		i3.setId(3);
		i3.setCreatedOn(new Date());

		Item i4 = new Item("Item 4 - televizori", 100, 20, ic3);
		i4.setId(4);

		Item i5 = new Item("Item 5 - televizori", 200, 34, ic3);
		i5.setId(5);

		items.put(i1.getId(), i1);
		items.put(i2.getId(), i2);
		items.put(i3.getId(), i3);
		items.put(i4.getId(), i4);
		items.put(i5.getId(), i5);

		/* BILLS */

		Bill bp = new Bill();
		bp.setId(2);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		bp.setDate(cal.getTime());
		bp.setState("S");

		BillItem bitP1 = new BillItem(400, 2, i1, bp);
		bitP1.getId().setItemNo(1);

		cust.addBills(bp);
		bills.put(bp.getId(), bp);

		Bill b = new Bill();
		b.setId(1);
		b.setDate(new Date());
		b.setState("O");

		cust.addBills(b);

		BillItem bi1 = new BillItem(10000, 30, i1, b);
		bi1.getId().setItemNo(1);
		BillItem bi2 = new BillItem(300, 34, i2, b);
		bi2.getId().setItemNo(2);
		BillItem bi3 = new BillItem(400, 23, i3, b);
		bi3.getId().setItemNo(3);
		BillItem bi4 = new BillItem(500, 18, i4, b);
		bi4.getId().setItemNo(4);
		BillItem bi5 = new BillItem(6500, 6, i5, b);
		bi5.getId().setItemNo(5);

		bills.put(b.getId(), b);

	}

	public void testBillItemDiscount() throws Exception {
		Rete engine = new Rete();
		engine.reset();
		engine.eval("(watch all)");
		engine.batch("projara/resources/jess/model_templates.clp");

		for (ItemCategory ic : categories.values()) {
			engine.definstance(ic.getClass().getSimpleName(), ic, false);
		}

		for (Item i : items.values()) {
			engine.definstance(i.getClass().getSimpleName(), i, false);
		}

		for (BillItem bi : bills.get(1).getBillItems()) {

			engine.definstance(bi.getClass().getSimpleName(), bi, false);

		}

		for (Customer c : customers.values()) {
			engine.definstance(c.getClass().getSimpleName(), c, false);
		}
		
		for(ActionEvent ae:actions.values()){
			engine.definstance(ae.getClass().getSimpleName(), ae, false);
		}

		engine.batch("projara/resources/jess/bill_item_rules.clp");
		engine.run();

		Iterator it = engine.getObjects(new Filter.ByClass(
				BillItemDiscount.class));
		while (it.hasNext()) {
			BillItemDiscount bid = (BillItemDiscount) it.next();
			System.out.println(bid.getDiscount() + " "
					+ bid.getBillItem().getItem().getName());
		}

		/*
		 * for(Bill b:bills.values()){ for(BillItem bi:b.getBillItems()){
		 * for(BillItemDiscount bid:bi.getDiscounts()){
		 * System.out.println(bi.getItem().getName()+" "+bid.getDiscount()); } }
		 * }
		 */
	}

	public static void main(String[] args) {
		MyTestData mtd = new MyTestData();
		try {
			mtd.testBillItemDiscount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("VREME TEST");
		Date d = new Date();
		Calendar curr = Calendar.getInstance();
		curr.setTime(d);

		Calendar before15 = Calendar.getInstance();
		before15.add(Calendar.DATE, -15);

		System.out.println(before15.getTime().toString());
	}
}
