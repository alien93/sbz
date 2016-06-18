package projara.session.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import jess.JessException;
import jess.Rete;
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
import projara.model.users.Threshold;
import projara.session.interfaces.BillManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.BillNotExistsException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.exception.ItemQuantityIsOverLimitException;
import projara.util.exception.NoBillItemsException;
import projara.util.exception.NotEnoughItemsException;
import projara.util.exception.NotEnoughPontsException;
import projara.util.exception.UserException;
import projara.util.exception.UserNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillInfo;
@Stateless
@Local(BillManagerLocal.class)
public class BillManagerBean implements BillManagerLocal {

	@EJB
	private BillDaoLocal billDao;

	@EJB
	private BillItemDaoLocal billItemDao;

	@EJB
	private UserDaoLocal userDao;

	@EJB
	private BillItemDiscountDaoLocal billItemDiscountDao;

	@EJB
	private BillDiscountDaoLocal billDiscountDao;

	@EJB
	private ItemDaoLocal itemDao;

	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;

	@EJB
	private ActionEventDaoLocal actionDao;

	@EJB
	private CustomerCategoryDaoLocal customerCategoryDao;

	@EJB
	private ThresholdDaoLocal thresholdDao;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill createBill(Customer customer) throws UserNotExistsException,
			BadArgumentsException {

		try {
			customer = (Customer) userDao.merge(customer);
		} catch (Exception e) {
			throw new UserNotExistsException("User not exists");
		}

		Bill bill = new Bill("T", customer);

		bill = billDao.persist(bill);

		return bill;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public BillInfo calculateCost(Bill bill, short points)
			throws BillException, JessException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		if (bill.getBillItems().isEmpty())
			throw new NoBillItemsException("Bill is empty");

		// try {
		// for (BillItem bi : bill.getBillItems()) {
		// bi.removeAllDiscounts();
		// bi = billItemDao.persist(bi);
		// }
		// bill.removeAllBillDiscounts();
		// bill = billDao.persist(bill);
		// } catch (Exception e) {
		// throw new BillException("Cant remove discounts");
		// }

		if (points > bill.getCustomer().getPoints())
			points = (short) bill.getCustomer().getPoints();

		// ///////////////////////////////////
		// POZOVI JESS /////////////////////
		// /////////////////////////////////
		Rete engine = new Rete();
		engine.reset();
		engine.eval("(watch all)");
		engine.batch("projara/resources/jess/model_templates.clp");
		// ////////////////////////////////
		// /// NAPRAVI FAKTE///////////////
		// ////////////////////////////////
		// ADD ItemCategories ///
		List<ItemCategory> itemCategories = itemCategoryDao.findAll();
		for (ItemCategory ic : itemCategories) {
			ic = itemCategoryDao.merge(ic);
			// System.out.println("UBACUJE KATEGORIJU: "+ic.getCode()+" "+ic.getName());
			engine.definstance(ic.getClass().getSimpleName(), ic, false);
		}
		// ////ACTIONS//////////////
		List<ActionEvent> actions = actionDao.findActiveEvents();
		for (ActionEvent ae : actions) {
			ae = actionDao.merge(ae);
			engine.definstance(ae.getClass().getSimpleName(), ae, false);
		}
		// CUSTOMER///////
		Customer c = (Customer) userDao.merge(bill.getCustomer());
		engine.definstance(c.getClass().getSimpleName(), c, false);
		// //Customer category/////
		CustomerCategory cc = customerCategoryDao.merge(c.getCategory());
		engine.definstance(cc.getClass().getSimpleName(), cc, false);
		// THRESHOLDS/////
		for (Threshold t : cc.getThresholds()) {
			t = thresholdDao.merge(t);
			engine.definstance(t.getClass().getSimpleName(), t, false);
		}
		// ///ITEMS///////////
		List<Item> items = itemDao.findAll();
		for (Item i : items) {
			i = itemDao.merge(i);
			engine.definstance(i.getClass().getSimpleName(), i, false);
		}
		// //////BillItems///////
		System.out.println("UBACUJE BILL ITEMSE");
		for (BillItem bi : bill.getItems()) {
			bi = billItemDao.merge(bi);
			// Item i = itemDao.merge(bi.getItem());
			// ItemCategory ic = itemCategoryDao.merge(i.getCategory());
			// i.setCategory(ic);
			// bi.setItem(i);
			// System.out.println("UBACEN PROIZVOD: "+bi.getOriginalTotal()+" "+bi.getItem().getCategory().getName());
			engine.definstance(bi.getClass().getSimpleName(), bi, false);
		}
		engine.definstance(bill.getClass().getSimpleName(), bill, false);

		// WorkingMemoryMarker wmm = engine.mark();
		// /////BILL ITEMI RACUNANJE//////////

		engine.batch("projara/resources/jess/bill_item_rules.clp");

		engine.run();

		bill = billDao.persist(bill);

		// engine.resetToMark(wmm);

		// //////////////racuna ukupnu cenu (originalnu)
		for (BillItem bi : bill.getBillItems()) {
			bill.setOriginalTotal(bill.getOriginalTotal() + bi.getTotal());
		}

		// ///rezoner racuna ukupnu cenu sa popustima //////////
		// engine.definstance(bill.getClass().getSimpleName(), bill, false);

		engine.updateObject(bill);
		engine.batch("projara/resources/jess/bill_rules.clp");

		engine.run();

		// ////////////////////
		// WorkingMemoryMarker preDodavanjaPoena = engine.mark();
		// ///////

		bill = billDao.persist(bill);

		bill.setSpentPoints((short) points);
		bill.setAwardPoints((short) 0);
		double percentBeforeAward0 = bill.getDiscountPercentage();
		double costBeforeAward0 = bill.getTotal();

		engine.unDefrule("");

		System.out.println("*********************************************");
		System.out.println("*********************************************");
		System.out.println("*********************************************");
		System.out.println("*********************************************");

		System.out.println("Before award: " + percentBeforeAward0 + " "
				+ costBeforeAward0);

		engine.updateObject(bill);
		engine.batch("projara/resources/jess/award_points.clp");
		engine.run();

		short awardAfterP = bill.getAwardPoints();
		short spentAfterP = bill.getSpentPoints();
		double percentageAfterP = bill.getDiscountPercentage();
		double finalCostAfterP = bill.getTotal();

		// ///////////////////////////////////////////////
		BillCostInfo withSpent = new BillCostInfo(awardAfterP, spentAfterP,
				percentageAfterP, finalCostAfterP);
		// ///////////////////////////////////////////////

		System.out.println("After award+spent_points " + awardAfterP + " "
				+ spentAfterP + " " + percentageAfterP + " " + finalCostAfterP);

		bill.setAwardPoints((short) 0);
		bill.setSpentPoints((short) 0);
		bill.setTotal(costBeforeAward0);
		bill.setDiscountPercentage(percentBeforeAward0);

		engine.updateObject(bill);
		engine.batch("projara/resources/jess/award_points.clp");
		engine.run();

		bill = billDao.persist(bill);

		// //////////////////////////////
		BillCostInfo withoutSpentPoints = new BillCostInfo(
				bill.getAwardPoints(), (short) 0, bill.getDiscountPercentage(),
				bill.getTotal());
		// ////////////////////////////

		System.out.println("After award with 0 spent: " + bill.getAwardPoints()
				+ " " + bill.getSpentPoints() + " "
				+ bill.getDiscountPercentage() + " " + bill.getTotal());

		List<BillCostInfo> listBillCostInfo = new ArrayList<>();
		listBillCostInfo.add(withSpent);
		listBillCostInfo.add(withoutSpentPoints);

		BillInfo billInfo = makeBillInfo(bill, listBillCostInfo);

		return billInfo;

	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public BillInfo makeBillInfo(Bill bill, List<BillCostInfo> listBillCostInfo)
			throws BillException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		BillInfo bi = new BillInfo(bill.getId(), listBillCostInfo);

		return bi;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill finishOrder(Bill bill, BillCostInfo billCostInfo)
			throws BillException, BadArgumentsException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		int custPoints = bill.getCustomer().getPoints();
		int reservedPoints = bill.getCustomer().getReservedPoints();

		if (custPoints < billCostInfo.getSpentPoints())
			throw new NotEnoughPontsException("Customer has " + custPoints
					+ " but requested " + billCostInfo.getSpentPoints());

		bill.getCustomer().setReservedPoints(
				reservedPoints + billCostInfo.getSpentPoints());
		bill.getCustomer()
				.setPoints(custPoints - billCostInfo.getSpentPoints());

		bill.setAwardPoints(billCostInfo.getAwardPoints());
		bill.setSpentPoints(billCostInfo.getSpentPoints());
		bill.setDiscountPercentage(billCostInfo.getDiscount());
		bill.setTotal(billCostInfo.getTotal());

		bill.setState("O");

		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			throw new BillException("Problem with ordering");
		}

		return bill;
	}

	// vendor cancel
	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill cancelOrder(Bill bill) throws BillException, UserException,
			BadArgumentsException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		int spentPoints = bill.getSpentPoints();

		Customer customer = null;
		try {
			customer = (Customer) userDao.merge(bill.getCustomer());
		} catch (Exception e) {
			throw new UserNotExistsException("Customer not exists");
		}

		int reservedPoints = customer.getReservedPoints();
		int points = customer.getPoints();

		customer.setPoints(spentPoints + points);
		customer.setReservedPoints(reservedPoints - spentPoints);

		try {
			customer = (Customer) userDao.persist(customer);
		} catch (Exception e) {
			throw new UserException("Problem with persisting customer");
		}

		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			throw new BillException("Problem with persiting bill");
		}

		return bill;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public BillItem addBillItem(Bill bill, Item item, int quantity)
			throws BillException, ItemException, BadArgumentsException {

		if (quantity <= 0)
			throw new BillException(
					"Quantity of item can not be less or equal then 0");

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill with id: " + bill.getId()
					+ " not exists");
		}

		try {
			item = itemDao.merge(item);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item with id: " + item.getId()
					+ " not exists");
		}

		if (item.getInStock() < quantity)
			throw new ItemQuantityIsOverLimitException("Order is: " + quantity
					+ " but in stock is: " + item.getInStock());

		BillItem billItem = new BillItem(item.getPrice(), quantity, item, bill);
		billItem.setOriginalTotal(item.getPrice() * quantity);
		billItem.setTotal(billItem.getOriginalTotal());

		billItem = billItemDao.persist(billItem);
		// bill = billDao.persist(bill);
		// item = itemDao.persist(item);

		return billItem;
	}

	@Override
	public BillItem addBillItem(int billId, int itemId, int quantity)
			throws BillException, ItemException, BadArgumentsException {

		Bill bill = null;
		Item item = null;

		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill with id: " + billId
					+ "not exists");
		}

		try {
			item = itemDao.findById(itemId);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item with id: " + itemId
					+ " not exists");
		}

		return addBillItem(bill, item, quantity);
	}

	@Override
	public Bill createBill(int customerId) throws UserNotExistsException,
			BadArgumentsException {

		Customer cust = null;
		try {
			cust = (Customer) userDao.findById(customerId);
		} catch (Exception e) {
			throw new UserNotExistsException("User with id: " + customerId
					+ " not exists");
		}

		return createBill(cust);
	}

	@Override
	public BillInfo calculateCost(int billid, short points)
			throws BillException, JessException {

		Bill bill = null;
		try {
			bill = billDao.findById(billid);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists with id: "
					+ billid);
		}
		if (bill == null)
			throw new BillNotExistsException("Bill not exists with id: "
					+ billid);

		return calculateCost(bill, points);
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill finishOrder(int billId, BillCostInfo billCostInfo)
			throws BillException, BadArgumentsException {

		Bill bill = null;
		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists with id: "
					+ billId);
		}
		if (bill == null)
			throw new BillNotExistsException("Bill not exists with id: "
					+ billId);

		return finishOrder(bill, billCostInfo);
	}

	// Vendor cancel
	@Override
	public Bill cancelOrder(int billId) throws BillException,
			BadArgumentsException, UserException {

		Bill bill = null;
		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill with id: " + billId
					+ " not exists");
		}
		if (bill == null)
			throw new BillNotExistsException("Bill with id: " + billId
					+ " not exists");

		return cancelOrder(bill);
	}

	// USER REJECT
	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public void rejectOrder(Bill bill) throws BillException,
			BadArgumentsException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Can not delete not existing bill");
		}

		if (!bill.getState().equals("T"))
			throw new BillException(
					"Cant reject order which is canceled, successful or ordered");

		billDao.remove(bill);

	}

	// USER REJECT
	@Override
	public void rejectOrder(int billId) throws BillException,
			BadArgumentsException {
		Bill bill = null;
		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Can not delete not existing bill");
		}
		if (bill == null)
			throw new BillNotExistsException("Can not delete not existing bill");

		rejectOrder(bill);
	}

	// vendor approves
	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill approveOrder(Bill bill) throws BillException,
			BadArgumentsException, UserException, ItemException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		if (!bill.getState().equals("O")) {
			throw new BillException("Only ordered bills can be approved");
		}

		for (BillItem bi : bill.getBillItems()) {
			try {
				bi = billItemDao.merge(bi);
			} catch (Exception e) {
				throw new BillException("Bill item not exists");
			}
			Item item = bi.getItem();
			try {
				item = itemDao.merge(item);
			} catch (Exception e) {
				throw new ItemNotExistsException("Item not exists");
			}

			if (item.getInStock() < bi.getQuantity())
				throw new NotEnoughItemsException("Item: " + item.getName()
						+ " in stock: " + item.getInStock() + " requested: "
						+ bi.getQuantity());
			
			item.setInStock(item.getInStock() - (int)bi.getQuantity());
			
			//item = itemDao.persist(item);
		}

		bill.setState("S");
		int awardPoints = bill.getAwardPoints();
		int spentPoints = bill.getSpentPoints();

		Customer c = bill.getCustomer();
		try {
			c = (Customer) userDao.merge(bill.getCustomer());
		} catch (Exception e) {
			throw new UserNotExistsException("Customer not exists");
		}

		int custReserved = bill.getCustomer().getReservedPoints();
		int custPoints = bill.getCustomer().getPoints();

		bill.getCustomer().setReservedPoints(custReserved - spentPoints);
		bill.getCustomer().setPoints(custPoints + awardPoints);

		try {
			c = (Customer) userDao.persist(c);
		} catch (Exception e) {
			throw new UserException("Problem with points");
		}

		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			throw new BillException("Problem with bill and points");
		}

		return bill;
	}

	// vendor approves
	@Override
	public Bill approveOrder(int billId) throws BillException,
			BadArgumentsException, UserException, ItemException {

		Bill bill = null;
		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists with id: "
					+ billId);
		}
		if (bill == null)
			throw new BillNotExistsException("Bill not exists with id: "
					+ billId);

		return approveOrder(bill);
	}

}
