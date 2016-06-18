package projara.model.dao.interfaces;

import java.util.List;

import projara.model.items.Item;
import projara.util.json.search.AdvancedSearch;

public interface ItemDaoLocal extends GenericDaoLocal<Item, Integer> {

	public List<Item> advancedSearch(AdvancedSearch advSearch);
}
