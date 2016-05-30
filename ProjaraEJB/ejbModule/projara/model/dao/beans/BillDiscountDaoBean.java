package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.BillDiscountDaoLocal;
import projara.model.shop.BillDiscount;

@Stateless
@Local(BillDiscountDaoLocal.class)
public class BillDiscountDaoBean extends GenericDaoBean<BillDiscount, Integer>
		implements
			BillDiscountDaoLocal {

}
