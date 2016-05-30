package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.BillDaoLocal;
import projara.model.shop.Bill;
@Stateless
@Local(BillDaoLocal.class)
public class BillDaoBean extends GenericDaoBean<Bill, Integer>
		implements
			BillDaoLocal {

}
