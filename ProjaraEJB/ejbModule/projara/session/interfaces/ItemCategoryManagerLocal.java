package projara.session.interfaces;

import java.util.List;

import projara.model.items.ItemCategory;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.json.view.ItemCategoryJson;

/**
 * 
 * @author Nina
 *
 */
public interface ItemCategoryManagerLocal {

	public ItemCategory addItemCategory();
	
	public ItemCategoryJson transformToJson(ItemCategory itemCategory) throws ItemCategoryException, 
			BadArgumentsException;
	
	public List<ItemCategoryJson> transformItems(List<ItemCategory> itemCategories) throws
		ItemCategoryException, BadArgumentsException;
}
