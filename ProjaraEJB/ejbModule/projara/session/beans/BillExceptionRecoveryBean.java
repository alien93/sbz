package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.dao.interfaces.BillDiscountDaoLocal;
import projara.model.dao.interfaces.BillItemDaoLocal;
import projara.model.dao.interfaces.BillItemDiscountDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.shop.Bill;
import projara.model.shop.BillDiscount;
import projara.model.shop.BillItem;
import projara.model.shop.BillItemDiscount;
import projara.session.interfaces.BillExceptionRecoveryLocal;

@Stateless
@Local(BillExceptionRecoveryLocal.class)
public class BillExceptionRecoveryBean implements BillExceptionRecoveryLocal {

	@EJB
	private BillDaoLocal billDao;
	
	@EJB
	private BillItemDaoLocal billItemDao;
	
	@EJB
	private BillDiscountDaoLocal billDiscountDao;
	
	@EJB
	private BillItemDiscountDaoLocal billitemDiscountDao;
	
	@EJB
	private UserDaoLocal userDao;
	
	@Override
	public void deleteBill(Bill bill) {
		
		if(bill == null)
			return;
		
		try{
			bill = billDao.merge(bill);
		}catch(Exception e){ return; }
		
		for (BillItem bi : bill.getBillItems()) {
			for (BillItemDiscount bid : bi.getDiscounts()) {
				billitemDiscountDao.remove(bid);
			}
			bi.removeAllDiscounts();
			billItemDao.remove(bi);
		}

		for (BillDiscount bd : bill.getBillDiscounts()) {
			billDiscountDao.remove(bd);
		}
		bill.removeAllBillDiscounts();
		bill.removeAllBillItems();
		bill.getCustomer().removeBills(bill);

		billDao.remove(bill);
		billDao.flush();
		
	}
	@Override
	public void deleteBill(int billID) {
		
		Bill bill = null;
		try{
			bill = billDao.findById(billID);
		}catch(Exception e){
			return;
		}
		if(bill == null)
			return;
		
		deleteBill(bill);
		
	}

}
