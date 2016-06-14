package projara.model.dao.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.util.exception.UserException;
import projara.util.exception.UserNotExistsException;
@Stateless
@Local(BillDaoLocal.class)
public class BillDaoBean extends GenericDaoBean<Bill, Integer>
		implements
			BillDaoLocal {

	@EJB
	private UserDaoLocal userDao;
	
	@Override
	public List<Bill> getOrderedBills() {
		Query q = em.createNamedQuery("getOrderedBills");
		
		List<Bill> bills = null;
		
		try{
			bills = q.getResultList();
		}catch(Exception e){}
		
		return bills;
	}

	@Override
	public List<Bill> getSuccessfulBillsByUser(Customer customer)
			throws UserException {
		
		try{
			customer = (Customer)userDao.merge(customer);
		}catch(Exception e){
			throw new UserException("User is not valid");
		}
		
		Query q = em.createNamedQuery("getSuccessfulBillsByUser");
		q.setParameter("myUser", customer);
		
		List<Bill> result = null;
		try{
			result = q.getResultList();
		}catch(Exception e){}
		
		return result;
	}

	@Override
	public List<Bill> getSuccessfulBillsByUser(int customerId)
			throws UserException {
		
		Customer customer = null;
		try{
			customer = (Customer)userDao.findById(customerId);
		}catch(Exception e){
			throw new UserNotExistsException("Customer with id: "+customer+" not exists");
		}
		
		return getSuccessfulBillsByUser(customer);
	}

	@Override
	public List<Bill> getCancelledByUser(Customer customer)
			throws UserException {
		try{
			customer = (Customer)userDao.merge(customer);
		}catch(Exception e){
			throw new UserException("User is not valid");
		}
		
		Query q = em.createNamedQuery("getCancelledByUser");
		q.setParameter("myUser", customer);
		
		List<Bill> result = null;
		try{
			result = q.getResultList();
		}catch(Exception e){}
		
		return result;
	}

	@Override
	public List<Bill> getCancelledByUser(int customerId) throws UserException {
		Customer customer = null;
		try{
			customer = (Customer)userDao.findById(customerId);
		}catch(Exception e){
			throw new UserNotExistsException("Customer with id: "+customer+" not exists");
		}
		
		return getCancelledByUser(customer);
	}

	@Override
	public List<Bill> getUserHistory(Customer customer) throws UserException {
		try{
			customer = (Customer)userDao.merge(customer);
		}catch(Exception e){
			throw new UserException("User is not valid");
		}
		
		Query q = em.createNamedQuery("getUserHistory");
		q.setParameter("myUser", customer);
		
		List<Bill> result = null;
		try{
			result = q.getResultList();
		}catch(Exception e){}
		
		return result;
	}

	@Override
	public List<Bill> getUserHistory(int customerId) throws UserException {
		Customer customer = null;
		try{
			customer = (Customer)userDao.findById(customerId);
		}catch(Exception e){
			throw new UserNotExistsException("Customer with id: "+customer+" not exists");
		}
		
		return getUserHistory(customer);
	}

}
