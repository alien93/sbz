package projara.session.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
import projara.model.shop.BillDiscount;
import projara.model.shop.BillItem;
import projara.model.shop.BillItemDiscount;
import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.session.interfaces.BillExceptionRecoveryLocal;
import projara.session.interfaces.BillManagerLocal;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.BillNotExistsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.exception.ItemQuantityIsOverLimitException;
import projara.util.exception.NoBillItemsException;
import projara.util.exception.NotEnoughItemsException;
import projara.util.exception.NotEnoughPontsException;
import projara.util.exception.UserException;
import projara.util.exception.UserNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
import projara.util.json.create.WebShopCartItem;
import projara.util.json.create.WebShopCartJson;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillDiscountInfo;
import projara.util.json.view.BillInfo;
import projara.util.json.view.BillItemDiscountInfo;
import projara.util.json.view.BillitemInfo;
import projara.util.json.view.CustomerBasicInfo;
import projara.util.json.view.CustomerCategoryBasicInfo;
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

	@EJB
	private ItemManagerLocal itemManager;

	@EJB
	private BillExceptionRecoveryLocal billRecovery;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = UserException.class, value = TxType.SUPPORTS)
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
	@Transactional(rollbackOn = BillException.class, value = TxType.SUPPORTS)
	public BillInfo calculateCost(Bill bill, short points)
			throws BillException, JessException {

		if (bill.getBillItems().isEmpty())
			throw new NoBillItemsException("Bill is empty");

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
		if (c.getCategory() != null) {
			CustomerCategory cc = customerCategoryDao.merge(c.getCategory());
			engine.definstance(cc.getClass().getSimpleName(), cc, false);
			// THRESHOLDS/////
			for (Threshold t : cc.getThresholds()) {
				// t = thresholdDao.merge(t);
				engine.definstance(t.getClass().getSimpleName(), t, false);
			}
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

		List<BillCostInfo> listBillCostInfo = new ArrayList<>();

		if (bill.getSpentPoints() > 0) {
			engine.updateObject(bill);
			engine.batch("projara/resources/jess/award_points.clp");
			engine.run();

			short awardAfterP = bill.getAwardPoints();
			short spentAfterP = bill.getSpentPoints();
			double percentageAfterP = bill.getDiscountPercentage();
			double finalCostAfterP = bill.getTotal();

			// ///////////////////////////////////////////////
			BillCostInfo withSpent = new BillCostInfo(awardAfterP, spentAfterP,
					percentageAfterP, finalCostAfterP, bill.getId());
			// ///////////////////////////////////////////////

			System.out.println("After award+spent_points " + awardAfterP + " "
					+ spentAfterP + " " + percentageAfterP + " "
					+ finalCostAfterP);

			listBillCostInfo.add(withSpent);
		}

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
				bill.getTotal(), bill.getId());
		// ////////////////////////////

		System.out.println("After award with 0 spent: " + bill.getAwardPoints()
				+ " " + bill.getSpentPoints() + " "
				+ bill.getDiscountPercentage() + " " + bill.getTotal());

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

		// CUSTOMER INFO
		CustomerBasicInfo cbi = new CustomerBasicInfo();
		CustomerCategoryBasicInfo ccbi = new CustomerCategoryBasicInfo();

		Customer c = bill.getCustomer();

		cbi.setId(c.getId());
		cbi.setAddress(c.getAddress());
		cbi.setFirstName(c.getFirstName());
		cbi.setLastName(c.getLastName());
		cbi.setUsername(c.getUsername());

		if (c.getCategory() != null) {
			ccbi.setCode(c.getCategory().getCategoryCode());
			ccbi.setName(c.getCategory().getName());
		}else{
			ccbi.setCode(null);
			ccbi.setName(null);
		}

		cbi.setCustomerCategory(ccbi);

		// Bill info

		bi.setCustomer(cbi);
		bi.setDate(bill.getDate());
		bi.setOriginalTotal(bill.getOriginalTotal());
		bi.setState(bill.getState());

		List<BillDiscountInfo> billDiscounts = new ArrayList<>();
		// Bill discounts
		for (BillDiscount bd : bill.getBillDiscounts()) {
			BillDiscountInfo bdi = new BillDiscountInfo(bd.getId(),
					bd.getDiscount(), bd.getType(), bd.getName());
			billDiscounts.add(bdi);
		}
		bi.setBillDiscounts(billDiscounts);

		// Bill Items
		List<BillitemInfo> billItemsInfo = new ArrayList<>();
		for (BillItem billItem : bill.getBillItems()) {
			BillitemInfo billitemInfo = new BillitemInfo();
			billitemInfo.setBillId(bill.getId());
			billitemInfo
					.setDiscountPercentage(billItem.getDiscountPercentage());
			billitemInfo.setNumber(billItem.getItemNo());
			billitemInfo.setOriginalCost(billItem.getOriginalTotal());
			billitemInfo.setQuantity((int) billItem.getQuantity());
			billitemInfo.setTotalCost(billItem.getTotal());

			try {
				billitemInfo.setItem(itemManager.getBasicInfo(billItem
						.getItem()));
			} catch (ItemException | ItemCategoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// discounts

			List<BillItemDiscountInfo> discounts = new ArrayList<>();
			for (BillItemDiscount billItemDiscount : billItem.getDiscounts()) {
				BillItemDiscountInfo bidi = new BillItemDiscountInfo(
						billItemDiscount.getId(),
						billItemDiscount.getDiscount(),
						billItemDiscount.getType(), billItemDiscount.getName());
				discounts.add(bidi);
			}
			billitemInfo.setItemDiscounts(discounts);

			billItemsInfo.add(billitemInfo);
		}
		bi.setBillItems(billItemsInfo);

		return bi;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = {BillException.class,
			BadArgumentsException.class}, value = TxType.REQUIRED)
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
	@Transactional(value = TxType.REQUIRES_NEW)
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

		bill.setState("C");
		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			throw new BillException("Problem with persiting bill");
		}

		return bill;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = {ItemException.class, BillException.class,
			BadArgumentsException.class}, value = TxType.SUPPORTS)
	public BillItem addBillItem(Bill bill, Item item, int quantity)
			throws BillException, ItemException, BadArgumentsException {

		if (quantity <= 0)
			throw new BillException(
					"Quantity of item can not be less or equal then 0");

		/*
		 * try { bill = billDao.merge(bill); } catch (Exception e) { throw new
		 * BillNotExistsException("Bill not exist"); }
		 */

		try {
			item = itemDao.merge(item);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item not exist");
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
	@Transactional(rollbackOn = {ItemException.class, BillException.class,
			BadArgumentsException.class}, value = TxType.SUPPORTS)
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
		if (bill == null)
			throw new BillNotExistsException("Bill with id: " + billId
					+ "not exists");

		try {
			item = itemDao.findById(itemId);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item with id: " + itemId
					+ " not exists");
		}
		if (item == null)
			throw new ItemNotExistsException("Item with id: " + itemId
					+ " not exists");

		return addBillItem(bill, item, quantity);
	}

	@Override
	@Transactional(rollbackOn = UserException.class, value = TxType.SUPPORTS)
	public Bill createBill(int customerId) throws UserNotExistsException,
			BadArgumentsException {

		Customer cust = null;
		try {
			cust = (Customer) userDao.findById(customerId);
		} catch (Exception e) {
			throw new UserNotExistsException("User with id: " + customerId
					+ " not exists");
		}
		if (cust == null)
			throw new UserNotExistsException("User with id: " + customerId
					+ " not exists");

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
	@Transactional(rollbackOn = {BillException.class,
			BadArgumentsException.class}, value = TxType.REQUIRES_NEW)
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
	@Transactional(value = TxType.REQUIRED)
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

		/*
		 * for(BillItem bi:bill.getBillItems()){ for(BillItemDiscount
		 * bid:bi.getDiscounts()){ billItemDiscountDao.remove(bid); }
		 * billItemDao.remove(bi); }
		 * 
		 * for(BillDiscount bd:bill.getBillDiscounts()){
		 * billDiscountDao.remove(bd); }
		 */

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

		Customer customer = bill.getCustomer();
		customer.removeBills(bill);

		bill.removeAllBillDiscounts();
		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// vendor approves
	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = {BillException.class,
			BadArgumentsException.class, UserException.class,
			ItemException.class}, value = TxType.REQUIRED)
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

			item.setInStock(item.getInStock() - (int) bi.getQuantity());

			// item = itemDao.persist(item);
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
	@Transactional(rollbackOn = {BillException.class,
			BadArgumentsException.class, UserException.class,
			ItemException.class}, value = TxType.REQUIRES_NEW)
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

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public boolean validateBill(Bill bill) throws BillException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}

		for (BillItem bi : bill.getBillItems()) {
			if (bi.getItem().getInStock() < bi.getQuantity())
				return false;
		}

		return true;
	}

	@Override
	public boolean validateBill(int billId) throws BillException {

		Bill bill = null;
		try {
			bill = billDao.findById(billId);
		} catch (Exception e) {
			throw new BillNotExistsException("Bill not exists");
		}
		if (bill == null)
			throw new BillNotExistsException("Bill not exists");

		return validateBill(bill);
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(rollbackOn = {BillException.class, ItemException.class}, value = TxType.REQUIRES_NEW)
	public BillInfo populateBill(WebShopCartJson webShopCart)
			throws BillException, ItemException, UserException,
			BadArgumentsException {

		Bill bill = createBill(webShopCart.getCustomerId());
		/*
		 * int customerId = webShopCart.getCustomerId(); Customer cust = null;
		 * try { cust = (Customer) userDao.findById(customerId); } catch
		 * (Exception e) { throw new UserNotExistsException("User with id: " +
		 * customerId + " not exists"); } if (cust == null) throw new
		 * UserNotExistsException("User with id: " + customerId +
		 * " not exists");
		 * 
		 * 
		 * Bill bill = new Bill("T", cust);
		 */
		if (webShopCart.getItems().isEmpty())
			throw new BillException("No items in web shop cart");

		List<BillItem> billitems = new ArrayList<>();
		for (WebShopCartItem cartItem : webShopCart.getItems()) {
			Item item = null;
			try {
				item = itemDao.findById(cartItem.getItemId());
			} catch (Exception e) {
				throw new ItemNotExistsException("Item with id: "
						+ cartItem.getItemId() + " not exists");
			}
			if (item == null)
				throw new ItemNotExistsException("Item with id: "
						+ cartItem.getItemId() + " not exists");
			/*
			 * if (item.getInStock() < cartItem.getQuantity()) throw new
			 * ItemQuantityIsOverLimitException("Order is: " +
			 * cartItem.getQuantity() + " but in stock is: " +
			 * item.getInStock());
			 * 
			 * BillItem billItem = new BillItem(item.getPrice(),
			 * cartItem.getQuantity(), item);
			 * billItem.setOriginalTotal(item.getPrice() *
			 * cartItem.getQuantity());
			 * billItem.setTotal(billItem.getOriginalTotal());
			 * billitems.add(billItem);
			 */
			addBillItem(bill, item, cartItem.getQuantity());
		}
		/*
		 * for (int i = 0; i < billitems.size(); i++) {
		 * billitems.get(i).setId(new BillItemPK(bill.getId(), i + 1));
		 * bill.addBillItems(billitems.get(i)); }
		 */
		try {
			bill = billDao.persist(bill);
		} catch (Exception e) {
			throw new BillException();
		}

		BillInfo billJson = null;
		try {
			billJson = calculateCost(bill, (short) webShopCart.getPoints());
		} catch (JessException e) {
			new BillException("JESS PUKO MAJKU MU");
		}

		return billJson;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public BillInfo transform(Bill bill) throws BillException,
			BadArgumentsException, ItemException, UserException,
			ItemCategoryException {

		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<BillCostInfo> bci = new ArrayList<>();
		BillCostInfo info = new BillCostInfo(bill.getAwardPoints(),
				bill.getSpentPoints(), bill.getDiscountPercentage(),
				bill.getTotal(), bill.getId());
		bci.add(info);

		return makeBillInfo(bill, bci);
	}

	@Override
	public List<BillInfo> transformList(List<Bill> result) {
		List<BillInfo> billInfos = new ArrayList<BillInfo>();

		if (result == null)
			return billInfos;

		for (Bill b : result) {
			BillInfo bi;
			try {
				bi = transform(b);
				billInfos.add(bi);
			} catch (BillException | BadArgumentsException | ItemException
					| UserException | ItemCategoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

		}

		return billInfos;
	}

}
