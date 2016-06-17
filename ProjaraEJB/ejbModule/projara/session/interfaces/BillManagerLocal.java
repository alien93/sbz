package projara.session.interfaces;

import jess.JessException;
import projara.model.items.Item;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.users.Customer;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.ItemException;
import projara.util.exception.UserNotExistsException;

public interface BillManagerLocal {
	
	public Bill createBill(Customer customer) throws UserNotExistsException,BadArgumentsException;
	public Bill createBill(int customerId) throws UserNotExistsException, BadArgumentsException;
	public Bill calculateCost(Bill bill,short points) throws BillException, JessException;
	public Bill calculateCost(int billid,short points) throws BillException;
	public Bill finishOrder(Bill bill) throws BillException;
	public Bill finishOrder(int billId) throws BillException;
	public Bill cancelOrder(Bill bill) throws BillException;
	public Bill cancelOrder(int billId) throws BillException;
	public BillItem addBillItem(Bill bill,Item item, int quantity) throws BillException, ItemException,BadArgumentsException;
	public BillItem addBillItem(int billId,int itemId, int quantity) throws BillException, ItemException, BadArgumentsException;
	
}
