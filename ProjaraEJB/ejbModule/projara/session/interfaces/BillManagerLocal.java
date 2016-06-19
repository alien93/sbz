package projara.session.interfaces;

import java.util.List;

import jess.JessException;
import projara.model.items.Item;
import projara.model.shop.Bill;
import projara.model.shop.BillItem;
import projara.model.users.Customer;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.BillException;
import projara.util.exception.BillNotExistsException;
import projara.util.exception.ItemException;
import projara.util.exception.UserException;
import projara.util.exception.UserNotExistsException;
import projara.util.json.view.BillCostInfo;
import projara.util.json.view.BillInfo;

public interface BillManagerLocal {

	public Bill createBill(Customer customer) throws UserNotExistsException,
			BadArgumentsException;
	public Bill createBill(int customerId) throws UserNotExistsException,
			BadArgumentsException;
	public BillInfo calculateCost(Bill bill, short points)
			throws BillException, JessException;
	public BillInfo calculateCost(int billid, short points)
			throws BillException, JessException;
	public Bill finishOrder(Bill bill, BillCostInfo billCostInfo)
			throws BillException, BadArgumentsException;
	public Bill finishOrder(int billId, BillCostInfo billCostInfo)
			throws BillException, BadArgumentsException;
	public Bill cancelOrder(Bill bill) throws BillException, UserException,
			BadArgumentsException;
	public Bill cancelOrder(int billId) throws BillException, UserException,
			BadArgumentsException;
	public BillItem addBillItem(Bill bill, Item item, int quantity)
			throws BillException, ItemException, BadArgumentsException;
	public BillItem addBillItem(int billId, int itemId, int quantity)
			throws BillException, ItemException, BadArgumentsException;
	public void rejectOrder(Bill bill) throws BillException,
			BadArgumentsException, UserException;
	public void rejectOrder(int billId) throws BillException,
			BadArgumentsException, UserException;
	public Bill approveOrder(Bill bill) throws BillException,
			BadArgumentsException, UserException, ItemException;
	public Bill approveOrder(int billId) throws BillException,
			BadArgumentsException, UserException, ItemException;
	public BillInfo makeBillInfo(Bill bill, List<BillCostInfo> listBillCostInfo)
			throws BillException;
	public boolean validateBill(Bill bill) throws BillException;
	public boolean validateBill(int billId) throws BillException;
}
