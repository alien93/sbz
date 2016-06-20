package projara.session.beans;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;

@Stateless
@Local(ItemManagerLocal.class)
public class ItemManagerBean implements ItemManagerLocal {

	@EJB
	private ItemDaoLocal itemDao;

	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@EJB
	private ActionEventDaoLocal actionEventDao;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Item addItem(String name, double cost, int inStock, int minQuantity,
			ItemCategory itemCat) throws ItemException, BadArgumentsException,
			ItemCategoryException {

		try {
			itemCat = itemCategoryDao.merge(itemCat);
		} catch (Exception e) {
			throw new ItemCategoryException("Item category with code: "
					+ itemCat.getCode() + " not exists");
		}
		if (itemCat == null)
			throw new ItemCategoryException("Item category with code: "
					+ itemCat.getCode() + " not exists");

		Item item = new Item(name, cost, inStock, itemCat);
		item.setMinQuantity(minQuantity);

		try {
			item = itemDao.persist(item);
		} catch (Exception e) {
			throw new ItemException("Can't create item with name: " + name);
		}

		return item;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Item order(Item item, int orderQuantity) throws ItemException,
			BadArgumentsException {

		try {
			item = itemDao.merge(item);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item with id: " + item.getId()
					+ " not exists");
		}
		if (item == null)
			throw new ItemNotExistsException("Item with id: " + item.getId()
					+ " not exists");

		item.setInStock(item.getInStock() + orderQuantity);

		try {
			item = itemDao.persist(item);
		} catch (Exception e) {
			throw new ItemException("Problem occured, try order again");
		}

		return item;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Item addItem(String name, double cost, int inStock, int minQuality,
			String itemCat) throws ItemException, BadArgumentsException,
			ItemCategoryException {

		ItemCategory itemCategory = null;

		try {
			itemCategory = itemCategoryDao.findById(itemCat);
		} catch (Exception e) {
			throw new ItemCategoryException("Item category with code: "
					+ itemCat + " do not exist");
		}

		if (itemCategory == null)
			throw new ItemCategoryException("Item category with code: "
					+ itemCat + " do not exist");

		return addItem(name, cost, inStock, minQuality, itemCategory);
	}

	@Override
	public Item order(int itemId, int orderQuantity) throws ItemException,
			BadArgumentsException {

		Item item = null;

		try {
			item = itemDao.findById(itemId);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item with id: " + itemId
					+ " not exists");
		}

		if (item == null)
			throw new ItemNotExistsException("Item with id: " + itemId
					+ " not exists");

		return order(item, orderQuantity);
	}

	@Override
	public ItemCategory makeItemCategory(ItemCategory parentCategory,
			String code, String name, double maxDiscount)
			throws BadArgumentsException, ItemCategoryException {

		ItemCategory ic = null;
		try {
			ic = itemCategoryDao.findById(code);
		} catch (Exception e) {
			throw new ItemCategoryException("Error---");
		}
		if (ic != null)
			throw new ItemCategoryException("Item category with code: " + code
					+ " already exists");

		ic = new ItemCategory(code, name, maxDiscount);

		if (parentCategory != null) {
			try {
				parentCategory = itemCategoryDao.merge(parentCategory);
			} catch (Exception e) {
				throw new ItemCategoryException("Item category with code: "
						+ parentCategory.getCode() + " not exists");
			}
			ic.setParentCategory(parentCategory);
		}

		try {
			ic = itemCategoryDao.persist(ic);
		} catch (Exception e) {
			throw new ItemCategoryException("Item category with code: " + code
					+ " cant be persisted");
		}

		return ic;
	}

	@Override
	public ItemCategory makeItemCategory(String parentCategory, String code,
			String name, double maxDiscount) throws BadArgumentsException,
			ItemCategoryException {
		ItemCategory parent = null;
		if(parentCategory == null || parentCategory.isEmpty())
			return makeItemCategory(parent, code, name, maxDiscount);
		
		
		try{
			parent = itemCategoryDao.findById(parentCategory);
		}catch(Exception e){
			throw new ItemCategoryException("Item category with code: "+parentCategory+" not exists");
		}
		
		if(parent == null)
			throw new ItemCategoryException("Item category with code: "+parentCategory+" not exists");
		
		
		
		return makeItemCategory(parent, code, name, maxDiscount);
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ItemCategory changeParentCategory(ItemCategory thisItemCat,
			ItemCategory parentItemCategory) throws BadArgumentsException,
			ItemCategoryException {
		
		try{
			thisItemCat = itemCategoryDao.merge(thisItemCat);
			parentItemCategory = itemCategoryDao.merge(parentItemCategory);
		}catch(Exception e){
			throw new ItemCategoryException("Item category not found");
		}
		
		thisItemCat.setParentCategory(parentItemCategory);
		
		try{
			thisItemCat = itemCategoryDao.persist(thisItemCat);
			parentItemCategory = itemCategoryDao.persist(parentItemCategory);
		}catch(Exception e){
			throw new ItemCategoryException("Item category parent can not be set");
		}
		
		return thisItemCat;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ItemCategory changeParentCategory(String thisItemCat,
			String parentItemCat) throws BadArgumentsException,
			ItemCategoryException {
		
		ItemCategory parent = null;
		ItemCategory thisCat = null;
		
		try{
			parent = itemCategoryDao.findById(parentItemCat);
			thisCat = itemCategoryDao.findById(thisItemCat);
		}catch(Exception e){
			throw new ItemCategoryException("oafafinfnasin poamfpoas");
		}
		
		if(parent == null || thisCat == null)
			throw new ItemCategoryException("oafafinfnasin poamfpoas");
		
		return changeParentCategory(thisCat, parent);
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ActionEvent createActionEvent(String name, Date from, Date until,
			double dicount) throws BadArgumentsException {
		
		if(from.after(until))
			throw new BadArgumentsException("date from is greater then date until");
		
		ActionEvent actionEvent = new ActionEvent(name, from, until, dicount);
		
		try{
			actionEvent = actionEventDao.persist(actionEvent);
		}catch(Exception e){
			throw new BadArgumentsException("Cant create action event");
		}
		
		return actionEvent;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ActionEvent addCategoryToAction(ActionEvent actionEvent,
			ItemCategory itemCategory) throws BadArgumentsException,
			ItemCategoryException {
		
		try{
			actionEvent = actionEventDao.merge(actionEvent);
			itemCategory = itemCategoryDao.merge(itemCategory);
		}catch(Exception e){
			throw new BadArgumentsException("Action or item category not exists");
		}
		
		actionEvent.addCategories(itemCategory);
		
		try{
			actionEvent = actionEventDao.persist(actionEvent);
			itemCategory = itemCategoryDao.persist(itemCategory);
		}catch(Exception e){
			throw new ItemCategoryException("Error while persisting action event and itemCategoty");
		}
		
		return actionEvent;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ActionEvent addCategoryToAction(int actionId, String itemCategoryCode)
			throws BadArgumentsException, ItemCategoryException {
		
		ItemCategory ic = null;
		ActionEvent ae = null;
		
		try{
			ic = itemCategoryDao.findById(itemCategoryCode);
			ae = actionEventDao.findById(actionId);
		}catch(Exception e){
			throw new BadArgumentsException();
		}
		if(ae == null || ic == null)
			throw new BadArgumentsException("action event and item category are null");
		
		return addCategoryToAction(ae, ic);
	}

}