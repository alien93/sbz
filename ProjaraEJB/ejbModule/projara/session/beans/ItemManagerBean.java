package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;

@Stateless
@Local(ItemManagerLocal.class)
public class ItemManagerBean implements ItemManagerLocal {

	@EJB
	private ItemDaoLocal itemDao;
	
	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@Override
	public Item addItem(String name, double cost, int inStock, int minQuantity,
			ItemCategory itemCat) throws ItemException, BadArgumentsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item order(Item item, int orderQuantity) throws ItemException,
			BadArgumentsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item addItem(String name, double cost, int inStock, int minQuality,
			String itemCat) throws ItemException, BadArgumentsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item order(int itemId, int orderQuantity) throws ItemException,
			BadArgumentsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemCategory makeItemCategory(ItemCategory parentCategory,
			String code, String name, double maxDiscount)
			throws BadArgumentsException, ItemCategoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemCategory makeItemCategory(String parentCategory, String code,
			String name, double maxDiscount) throws BadArgumentsException,
			ItemCategoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemCategory changeParentCategory(ItemCategory thisItemCat,
			ItemCategory parentItemCategory) throws BadArgumentsException,
			ItemCategoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemCategory changeParentCategory(String thisItemCat,
			String parentItemCat) throws BadArgumentsException,
			ItemCategoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
