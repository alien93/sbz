package projara.session.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import jess.JessException;
import jess.Rete;
import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.session.interfaces.ItemManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemCategoryNotExistsException;
import projara.util.exception.ItemException;
import projara.util.exception.ItemNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
import projara.util.json.create.CreateItemForm;
import projara.util.json.search.AdvancedSearch;
import projara.util.json.view.ActionInfo;
import projara.util.json.view.ActionJson;
import projara.util.json.view.ItemCategoryInfo;
import projara.util.json.view.ItemCategoryJson;
import projara.util.json.view.ItemInfo;
import projara.util.json.view.ItemJson;

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
	@Transactional(value = TxType.REQUIRED, rollbackOn = {ItemException.class,
			BadArgumentsException.class, ItemCategoryException.class})
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
		if(item.getInStock() > item.getMinQuantity())
			item.setNeedOrdering(false);

		try {
			item = itemDao.persist(item);
		} catch (Exception e) {
			throw new ItemException("Problem occured, try order again");
		}

		return item;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	@Transactional(value = TxType.REQUIRED, rollbackOn = {
			ItemCategoryException.class, ItemException.class,
			BadArgumentsException.class})
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
		if (parentCategory == null || parentCategory.isEmpty())
			return makeItemCategory(parent, code, name, maxDiscount);

		try {
			parent = itemCategoryDao.findById(parentCategory);
		} catch (Exception e) {
			throw new ItemCategoryException("Item category with code: "
					+ parentCategory + " not exists");
		}

		if (parent == null)
			throw new ItemCategoryException("Item category with code: "
					+ parentCategory + " not exists");

		return makeItemCategory(parent, code, name, maxDiscount);
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public ItemCategory changeParentCategory(ItemCategory thisItemCat,
			ItemCategory parentItemCategory) throws BadArgumentsException,
			ItemCategoryException {

		try {
			thisItemCat = itemCategoryDao.merge(thisItemCat);
			parentItemCategory = itemCategoryDao.merge(parentItemCategory);
		} catch (Exception e) {
			throw new ItemCategoryException("Item category not found");
		}

		thisItemCat.setParentCategory(parentItemCategory);

		try {
			thisItemCat = itemCategoryDao.persist(thisItemCat);
			parentItemCategory = itemCategoryDao.persist(parentItemCategory);
		} catch (Exception e) {
			throw new ItemCategoryException(
					"Item category parent can not be set");
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

		try {
			parent = itemCategoryDao.findById(parentItemCat);
			thisCat = itemCategoryDao.findById(thisItemCat);
		} catch (Exception e) {
			throw new ItemCategoryException("oafafinfnasin poamfpoas");
		}

		if (parent == null || thisCat == null)
			throw new ItemCategoryException("oafafinfnasin poamfpoas");

		return changeParentCategory(thisCat, parent);
	}



	@Override
	public void automaticOrdering() throws ItemException, JessException {

		Rete engine = new Rete();
		engine.reset();
		engine.eval("(watch all)");
		engine.batch("projara/resources/jess/model_templates.clp");

		List<Item> items = itemDao.findAll();
		for (Item item : items) {
			item = itemDao.merge(item);
			engine.definstance(item.getClass().getSimpleName(), item, false);
		}

		engine.batch("projara/resources/jess/items_rules.clp");
		engine.run();

	}

	@Override
	public List<Item> filterItems(AdvancedSearch advSearch) {
		return itemDao.advancedSearch(advSearch);
	}

	@Override
	public ItemJson transformToJson(Item item) throws ItemException,
			BadArgumentsException, ItemCategoryException {

		try {
			item = itemDao.merge(item);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item not exists");
		}

		List<ActionEvent> acEv = actionEventDao.findActiveEvents();

		ItemJson retVal = new ItemJson();

		ItemInfo itemInfo = getBasicInfo(item);

		ItemCategoryInfo ici = new ItemCategoryInfo(item.getCategory()
				.getCode(), item.getCategory().getName(), item.getCategory()
				.getMaxDiscount());

		List<ActionInfo> listAi = new ArrayList<ActionInfo>();
		for (ActionEvent ae : acEv) {
			for (ItemCategory ic : ae.getCategories()) {
				ic = itemCategoryDao.merge(ic);
				if (item.isCategoryOf(ic)) {
					ActionInfo ai = new ActionInfo(ae.getId(), ae.getName(),
							ae.getFrom(), ae.getUntil(), ae.getDiscount());
					listAi.add(ai);
				}
			}
		}

		retVal.setCategory(ici);
		retVal.setInfo(itemInfo);
		retVal.setActions(listAi);

		retVal.calculateDiscountAndCost();

		return retVal;
	}

	@Override
	public List<ItemJson> transformItems(List<Item> items)
			throws ItemException, ItemCategoryException, BadArgumentsException {

		List<ItemJson> retVal = new ArrayList<>();
		for (Item i : items) {
			ItemJson ij = transformToJson(i);
			retVal.add(ij);
		}

		return retVal;
	}

	@Override
	public ItemInfo getBasicInfo(Item item) throws ItemException,
			ItemCategoryException {

		try {
			item = itemDao.merge(item);
		} catch (Exception e) {
			throw new ItemNotExistsException();
		}

		ItemInfo ii = new ItemInfo(item.getId(), item.getName(),
				item.getPrice(), item.getPicture(), item.getInStock(),
				item.getNeedOrdering(), item.getCreatedOn(),
				item.getMinQuantity(), item.getRecordState());

		return ii;

	}

	@Override
	public List<ItemCategoryJson> getTree() {
		List<ItemCategory> roots = itemCategoryDao.getRoots();

		if (roots == null) {
			System.out.println("NULL");
		}

		List<ItemCategoryJson> itemCatJsonList = new ArrayList<>();

		for (ItemCategory ic : roots) {
			ItemCategoryJson icj = new ItemCategoryJson();
			ItemCategoryInfo info = new ItemCategoryInfo(ic.getCode(),
					ic.getName(), ic.getMaxDiscount());
			icj.setParentCategory(null);
			icj.setInfo(info);

			List<ItemCategoryJson> children = new ArrayList<>();
			if (ic.getSubCategories() != null
					&& !ic.getSubCategories().isEmpty()) {
				for (ItemCategory catChild : ic.getSubCategories()) {
					children.add(children(catChild, icj));
				}
			}

			icj.setSubCategories(children);

			itemCatJsonList.add(icj);
		}

		return itemCatJsonList;
	}

	@Override
	public ItemCategoryJson getCategoryById(String id) {
		ItemCategoryJson retVal = new ItemCategoryJson();
		if (id == null || id.isEmpty())
			return retVal;

		ItemCategory root = itemCategoryDao.findById(id);

		ItemCategoryInfo ici = new ItemCategoryInfo(root.getCode(),
				root.getName(), root.getMaxDiscount());
		retVal.setInfo(ici);
		retVal.setParentCategory(root.getParentCategory() == null ? null : root
				.getParentCategory().getCode());
		retVal.setParentName(root.getParentCategory() == null ? null : root
				.getParentCategory().getName());
		List<ItemCategoryJson> children = new ArrayList<>();
		if (root.getSubCategories() != null
				&& !root.getSubCategories().isEmpty()) {
			for (ItemCategory catChild : root.getSubCategories()) {
				children.add(children(catChild, retVal));
			}
		}

		retVal.setSubCategories(children);

		return retVal;
	}

	private ItemCategoryJson children(ItemCategory itemCategory,
			ItemCategoryJson parent) {
		List<ItemCategoryJson> childrenOfChildren = new ArrayList<>();
		ItemCategoryJson currentCat = new ItemCategoryJson();
		ItemCategoryInfo currentInfo = new ItemCategoryInfo(
				itemCategory.getCode(), itemCategory.getName(),
				itemCategory.getMaxDiscount());
		currentCat.setInfo(currentInfo);
		currentCat.setParentCategory(parent.getInfo().getCode());
		currentCat.setParentName(parent.getInfo().getName());

		for (ItemCategory child : itemCategory.getSubCategories()) {
			childrenOfChildren.add(children(child, currentCat));
		}
		currentCat.setSubCategories(childrenOfChildren);

		return currentCat;

	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = {
			ItemException.class, BadArgumentsException.class,
			ItemCategoryException.class})
	public ItemJson formToItem(CreateItemForm form) throws ItemException,
			ItemCategoryException, BadArgumentsException {

		Item newItem = addItem(form.getName(), form.getCost(),
				form.getInStock(), form.getMinQuantity(), form.getCategory());

		if (form.getImage() != null) {
			// SNIMANJE SLIKE
			String warPath = getClass().getProtectionDomain().getCodeSource()
					.getLocation().getPath()
					+ "/../ProjaraWeb.war/images";
			File file = new File(warPath);
			String image_name = newItem.getId() + "." + form.getFormat();
			try (FileOutputStream fos = new FileOutputStream(new File(warPath
					+ "/" + image_name))) {
				byte[] fileBytes = form.getImage();
				fos.write(fileBytes);
				newItem.setPicture(image_name);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return transformToJson(newItem);
	}

	@Override
	@Transactional(value = TxType.REQUIRED, rollbackOn = {ItemException.class,
			ItemCategoryException.class, BadArgumentsException.class})
	@Interceptors({CheckParametersInterceptor.class})
	public Item updateItem(int itemId, String name, String catCode,
			double cost, int minQuantity) throws ItemException,
			ItemCategoryException {

		Item item = null;
		ItemCategory itemCat = null;

		try {
			item = itemDao.findById(itemId);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item not exists");
		}
		if (item == null)
			throw new ItemNotExistsException("Item not exists");

		try {
			itemCat = itemCategoryDao.findById(catCode);
		} catch (Exception e) {
			throw new ItemCategoryNotExistsException("Item category not exists");
		}
		if (itemCat == null)
			throw new ItemCategoryNotExistsException("Item category not exists");

		name = name.trim();

		if (!name.equals(item.getName()))
			item.setName(name);

		item.setPrice(cost);
		item.setMinQuantity(minQuantity);

		item.setCategory(itemCat);

		try {
			item = itemDao.persist(item);
		} catch (Exception e) {
			throw new ItemException();
		}

		return item;
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW, rollbackOn = {
			ItemException.class, ItemCategoryException.class,
			BadArgumentsException.class})
	public ItemJson updateItemForm(CreateItemForm itemForm)
			throws ItemException, ItemCategoryException, BadArgumentsException {

		Item item = updateItem(itemForm.getId(), itemForm.getName(),
				itemForm.getCategory(), itemForm.getCost(),
				itemForm.getMinQuantity());

		if (itemForm.getImage() != null) {
			// SNIMANJE SLIKE

			String warPath = getClass().getProtectionDomain().getCodeSource()
					.getLocation().getPath()
					+ "/../ProjaraWeb.war/images";
			String image_name = item.getId() + "." + itemForm.getFormat();

			if (!item.getPicture().startsWith("def")) {
				File file = new File(warPath + "/" + item.getPicture());
				if (file.exists())
					file.delete();
			}

			File file = new File(warPath);

			try (FileOutputStream fos = new FileOutputStream(new File(warPath
					+ "/" + image_name))) {
				byte[] fileBytes = itemForm.getImage();
				fos.write(fileBytes);
				item.setPicture(image_name);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return transformToJson(item);

	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public void setActive(int itemId, boolean active) throws ItemException {

		Item item = null;
		try {
			item = itemDao.findById(itemId);
		} catch (Exception e) {
			throw new ItemNotExistsException("Item not exists");
		}
		if (item == null)
			throw new ItemNotExistsException("Item not exists");

		item.setRecordState(active);

	}

	@Override
	public List<ItemJson> getAllByCategory(String code) {
		List<Item> list = new ArrayList<>();
		List<ItemJson> listJson = new ArrayList<>();

		ItemCategory ic = null;
		try {
			ic = itemCategoryDao.findById(code);
		} catch (Exception e) {
			return listJson;
		}
		if (ic == null)
			return listJson;

		List<ItemCategory> categories = new ArrayList<>();
		categories.add(ic);
		while (!categories.isEmpty()) {
			ItemCategory current = categories.remove(0);

			for (Item i : current.getItems()) {
				i = itemDao.merge(i);
				list.add(i);
			}

			for (ItemCategory subCat : current.getSubCategories()) {
				subCat = itemCategoryDao.merge(subCat);
				categories.add(subCat);
			}

		}

		try {
			return transformItems(list);
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}


}
