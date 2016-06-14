package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.dao.interfaces.BillDiscountDaoLocal;
import projara.model.dao.interfaces.BillItemDaoLocal;
import projara.model.dao.interfaces.BillItemDiscountDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.shop.ActionEvent;
import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.session.interfaces.BillManagerLocal;
import projara.util.exception.BillException;
import projara.util.exception.UserNotExistsException;
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

	@Override
	public Bill createBill(Customer customer) throws UserNotExistsException {

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
	public Bill calculateCost(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill finishOrder(Bill bill) throws BillException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill cancelOrder(Bill bill) throws BillException{
		// TODO Auto-generated method stub
		return null;
	}

}
