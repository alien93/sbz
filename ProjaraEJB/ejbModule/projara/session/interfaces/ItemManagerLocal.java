package projara.session.interfaces;

import java.util.Date;

import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;

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
}