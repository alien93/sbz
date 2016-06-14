package projara.model.dao.interfaces;

import java.util.List;

import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.util.exception.UserException;

public interface BillDaoLocal extends GenericDaoLocal<Bill, Integer>{

	public List<Bill> getOrderedBills();
	public List<Bill> getSuccessfulBillsByUser(Customer customer) throws UserException;
	public List<Bill> getSuccessfulBillsByUser(int customerId) throws UserException;
	public List<Bill> getCancelledByUser(Customer customer) throws UserException;
	public List<Bill> getCancelledByUser(int customerId) throws UserException;
	public List<Bill> getUserHistory(Customer customer) throws UserException;
	public List<Bill> getUserHistory(int customerId) throws UserException;
}
