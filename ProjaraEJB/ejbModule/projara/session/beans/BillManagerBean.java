package projara.session.beans;

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
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.items.Item;
import projara.model.shop.ActionEvent;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.users.Customer;
import projara.session.interfaces.BillManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.exception.ItemQuantityIsOverLimitException;
import projara.util.exception.UserNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
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

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill createBill(Customer customer) throws UserNotExistsException,BadArgumentsException {

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
	public Bill calculateCost(Bill bill) throws BillException, JessException {
		
		try{
			bill = billDao.merge(bill);
		}catch(Exception e){
			throw new BillException("Bill not exists");
		}
		
		/////////////////////////////////////
		// POZOVI JESS /////////////////////
		///////////////////////////////////
		Rete engine = new Rete();
		engine.reset();
		engine.eval("(watch all)");
		engine.batch("projara/resources/jess/model_templates.clp");
		//////////////////////////////////
		///// NAPRAVI FAKTE///////////////
		//////////////////////////////////
		
		
		return null;
	}

	@Override
	public Bill finishOrder(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill cancelOrder(Bill bill) throws BillException {
		
		return null;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Bill addBillItem(Bill bill, Item item, int quantity)
			throws BillException, ItemException,BadArgumentsException {

		if(quantity<=0)
			throw new BillException("Quantity of item can not be less or equal then 0");
		
		try {
			bill = billDao.merge(bill);
		} catch (Exception e) {
			throw new BillException("Bill with id: " + bill.getId()
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
		billItem.setOriginalTotal(item.getPrice()*quantity);
		billItem.setTotal(billItem.getOriginalTotal());
		
		billItem = billItemDao.persist(billItem);
		bill = billDao.persist(bill);
		item = itemDao.persist(item);
		
		return bill;
	}

	@Override
	public Bill addBillItem(int billId, int itemId, int quantity)
			throws BillException, ItemException,BadArgumentsException {
		
		Bill bill = null;
		Item item = null;
		
		try{
			bill = billDao.findById(billId);
		}catch(Exception e){
			throw new BillException("Bill with id: "+billId+"not exists");
		}
		
		try{
			item = itemDao.findById(itemId);
		}catch(Exception e){
			throw new ItemNotExistsException("Item with id: "+itemId+" not exists");
		}
		
		
		
		return addBillItem(bill, item, quantity);
	}

	@Override
	public Bill createBill(int customerId) throws UserNotExistsException,BadArgumentsException {
		
		Customer cust = null;
		try{
			cust = (Customer)userDao.findById(customerId);
		}catch(Exception e){
			throw new UserNotExistsException("User with id: "+customerId+" not exists");
		}
		
		return createBill(cust);
	}

	@Override
	public Bill calculateCost(int billid) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill finishOrder(int billId) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill cancelOrder(int billId) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

}
