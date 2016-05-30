package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.BillItemDaoLocal;
import projara.model.shop.BillItem;
import projara.model.shop.BillItemPK;

@Stateless
@Local(BillItemDaoLocal.class)
public class BillItemDaoBean extends GenericDaoBean<BillItem, BillItemPK>
		implements
			BillItemDaoLocal {

}
