package projara.model.dao.interfaces;

import java.util.List;

import projara.model.items.ItemCategory;

public interface ItemCategoryDaoLocal extends GenericDaoLocal<ItemCategory, String> {

	public List<ItemCategory> filterByName(String name);
	public List<ItemCategory> getRoots();
}
