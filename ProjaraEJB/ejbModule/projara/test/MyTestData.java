package projara.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import jess.Filter;
import jess.Rete;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.shop.BillItemDiscount;

public class MyTestData {
	private HashMap<String, ItemCategory> categories;
	private HashMap<Integer, Item> items;
	private HashMap<Integer, Bill> bills;

	public MyTestData() {
		categories = new HashMap<>();
		items = new HashMap<>();
		bills = new HashMap<>();

		populateData();
	}

	private void populateData() {

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

		/* ITEMS */
		Item i1 = new Item("Proizvod siroke potrosnje", 100, 50, ic1);
		i1.setCreatedOn(new Date());
		i1.setId(1);

		Item i2 = new Item("Drugi proizvod siroke", 100, 50, ic2);
		i2.setCreatedOn(new Date());
		i2.setId(2);

		Item i3 = new Item("Neki drugi proizvod", 200, 19, ic3);
		i3.setId(3);
		i3.setCreatedOn(new Date());
		
		Item i4 = new Item("TRALALAL", 100, 20, ic3);
		i4.setId(4);
		
		Item i5 = new Item("222222", 200, 34, ic3);
		i5.setId(5);

		items.put(i1.getId(), i1);
		items.put(i2.getId(), i2);
		items.put(i3.getId(), i3);
		items.put(i4.getId(), i4);
		items.put(i5.getId(), i5);
		
		/* BILLS */

		Bill b = new Bill();
		b.setId(1);

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
	
	public void testBillItemDiscount() throws Exception{
		Rete engine = new Rete();
		engine.reset();
		engine.eval("(watch all)");
		engine.batch("projara/resources/jess/model_templates.clp");
		
		for(ItemCategory ic:categories.values()){
			engine.definstance(ic.getClass().getSimpleName(), ic, false);
		}
		
		for(Item i:items.values()){
			engine.definstance(i.getClass().getSimpleName(), i, false);
		}
		
		for(Bill b:bills.values()){
			for(BillItem bi:b.getItems()){
				engine.definstance(bi.getClass().getSimpleName(), bi, false);
			}
		}
		
		engine.batch("projara/resources/jess/bill_item_rules.clp");
		engine.run();
		
		Iterator it = engine.getObjects(new Filter.ByClass(BillItemDiscount.class));
		while(it.hasNext()){
			BillItemDiscount bid = (BillItemDiscount)it.next();
			System.out.println(bid.getDiscount()+" "+bid.getBillItem().getItem().getName());
		}
		
		for(Bill b:bills.values()){
			for(BillItem bi:b.getBillItems()){
				for(BillItemDiscount bid:bi.getDiscounts()){
					System.out.println(bi.getItem().getName()+" "+bid.getDiscount());
				}
			}
		}
	}
	
	public static void main(String[] args){
		MyTestData mtd = new MyTestData();
		try {
			mtd.testBillItemDiscount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
