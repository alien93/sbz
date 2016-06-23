package projara.session.interfaces;

import java.util.Date;
import java.util.List;

import jess.JessException;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.json.create.CreateItemForm;
import projara.util.json.search.AdvancedSearch;
import projara.util.json.view.ItemCategoryJson;
import projara.util.json.view.ItemInfo;
import projara.util.json.view.ItemJson;

public interface ItemManagerLocal {

	public Item addItem(String name, double cost, int inStock, int minQuantity,
			ItemCategory itemCat) throws ItemException, BadArgumentsException,
			ItemCategoryException;

	public Item order(Item item, int orderQuantity) throws ItemException,
			BadArgumentsException;

	public Item addItem(String name, double cost, int inStock, int minQuality,
			String itemCat) throws ItemException, BadArgumentsException,
			ItemCategoryException;

	public Item order(int itemId, int orderQuantity) throws ItemException,
			BadArgumentsException;

	public ItemCategory makeItemCategory(ItemCategory parentCategory,
			String code, String name, double maxDiscount)
			throws BadArgumentsException, ItemCategoryException;

	public ItemCategory makeItemCategory(String parentCategory, String code,
			String name, double maxDiscount) throws BadArgumentsException,
			ItemCategoryException;

	public ItemCategory changeParentCategory(ItemCategory thisItemCat,
			ItemCategory parentItemCategory) throws BadArgumentsException,
			ItemCategoryException;

	public ItemCategory changeParentCategory(String thisItemCat,
			String parentItemCat) throws BadArgumentsException,
			ItemCategoryException;

	public ActionEvent createActionEvent(String name, Date from, Date until,
			double dicount) throws BadArgumentsException;

	public ActionEvent addCategoryToAction(ActionEvent actionEvent,
			ItemCategory itemCategory) throws BadArgumentsException,
			ItemCategoryException;

	public ActionEvent addCategoryToAction(int actionId, String itemCategoryCode)
			throws BadArgumentsException, ItemCategoryException;

	public void automaticOrdering() throws ItemException, JessException;

	public List<Item> filterItems(AdvancedSearch advSearch);

	public ItemJson transformToJson(Item item) throws ItemException,
			BadArgumentsException, ItemCategoryException;

	public List<ItemJson> transformItems(List<Item> items)
			throws ItemException, ItemCategoryException, BadArgumentsException;

	public ItemInfo getBasicInfo(Item item) throws ItemException,
			ItemCategoryException;

	public List<ItemCategoryJson> getTree();

	public ItemCategoryJson getCategoryById(String id);

	public ItemJson formToItem(CreateItemForm form) throws ItemException,
			ItemCategoryException, BadArgumentsException;

	public ItemJson updateItemForm(CreateItemForm itemForm) throws ItemException,
			ItemCategoryException, BadArgumentsException;

	public Item updateItem(int itemId, String name, String catCode, double cost,
			int minQuantity) throws ItemException, ItemCategoryException;


	public void setActive(int itemId, boolean active) throws ItemException;

}
