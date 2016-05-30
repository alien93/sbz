package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
@Stateless
@Local(ItemCategoryDaoLocal.class)
public class ItemCategoryDaoBean extends GenericDaoBean<ItemCategory, String>
		implements
			ItemCategoryDaoLocal {

}
