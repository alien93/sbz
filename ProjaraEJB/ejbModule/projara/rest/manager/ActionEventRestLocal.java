package projara.rest.manager;

import java.util.List;

import projara.util.json.view.ActionJson;
import projara.util.json.view.ItemCategoryInfo;

public interface ActionEventRestLocal {
	
	
	public List<ActionJson> getAllActionEvents();
	public List<ItemCategoryInfo> getCategoriesForAction(int actionId);
	public List<ItemCategoryInfo> getItemCat(String catCode);
	public RestMsg addAction(ActionJson action, String modify);
}
