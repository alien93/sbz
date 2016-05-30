package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.items.Item;

@Stateless
@Local(ItemDaoLocal.class)
public class ItemDaoBean extends GenericDaoBean<Item, Integer>
		implements
			ItemDaoLocal {

}
