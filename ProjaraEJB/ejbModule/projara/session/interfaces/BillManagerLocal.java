package projara.session.interfaces;

import projara.model.items.Item;
import projara.model.shop.Bill;
import projara.model.users.Customer;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.ItemException;
import projara.util.exception.UserNotExistsException;

public interface BillManagerLocal {
	
	public Bill createBill(Customer customer) throws UserNotExistsException,BadArgumentsException;
	public Bill createBill(int customerId) throws UserNotExistsException, BadArgumentsException;
	public Bill calculateCost(Bill bill) throws BillException;
	public Bill calculateCost(int billid) throws BillException;
	public Bill finishOrder(Bill bill) throws BillException;
	public Bill finishOrder(int billId) throws BillException;
	public Bill cancelOrder(Bill bill) throws BillException;
	public Bill cancelOrder(int billId) throws BillException;
	public Bill addBillItem(Bill bill,Item item, int quantity) throws BillException, ItemException,BadArgumentsException;
	public Bill addBillItem(int billId,int itemId, int quantity) throws BillException, ItemException, BadArgumentsException;
	
}
