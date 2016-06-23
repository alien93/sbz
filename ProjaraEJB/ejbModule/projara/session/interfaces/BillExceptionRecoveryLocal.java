package projara.session.interfaces;

import projara.model.shop.Bill;

public interface BillExceptionRecoveryLocal {

	public void deleteBill(int billID);

	public void deleteBill(Bill bill);


}
