package projara.rest.manager;

import java.util.List;

import projara.util.json.view.ItemCategoryJson;

public interface ItemCategoryRestLocal {
	
	public List<ItemCategoryJson> getParentCategories(String catId);
	public List<ItemCategoryJson> getAllCategories();
	public RestMsg addCategory(ItemCategoryJson newCat, String modify);
	public RestMsg deleteCategory(String code);
}
