package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.BillItemDiscountDaoLocal;
import projara.model.shop.BillItemDiscount;
@Stateless
@Local(BillItemDiscountDaoLocal.class)
public class BillItemDiscountDaoBean
		extends
			GenericDaoBean<BillItemDiscount, Integer>
		implements
			BillItemDiscountDaoLocal {

}
