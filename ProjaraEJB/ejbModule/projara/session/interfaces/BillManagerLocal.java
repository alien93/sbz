package projara.session.interfaces;

import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.util.exception.BillException;
import projara.util.exception.UserNotExistsException;

public interface BillManagerLocal {
	
	public Bill createBill(Customer customer) throws UserNotExistsException;
	public Bill calculateCost(Bill bill) throws BillException;
	public Bill finishOrder(Bill bill) throws BillException;
	public Bill cancelOrder(Bill bill) throws BillException;
	
}
