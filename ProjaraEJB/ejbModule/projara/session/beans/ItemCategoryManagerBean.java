package projara.session.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.ActionEventDaoLocal;
import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
import projara.model.shop.ActionEvent;
import projara.session.interfaces.ItemCategoryManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.exception.ItemCategoryNotExistsException;
import projara.util.json.view.ActionInfo;
import projara.util.json.view.ItemCategoryInfo;
import projara.util.json.view.ItemCategoryJson;
/**
 * 
 * @author Nina
 *
 */
@Stateless
@Local(ItemCategoryManagerLocal.class)
public class ItemCategoryManagerBean implements ItemCategoryManagerLocal{
	
	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@EJB
	private ActionEventDaoLocal actionEventDao;

	@Override
	public ItemCategory addItemCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemCategoryJson transformToJson(ItemCategory itemCategory) throws ItemCategoryException, BadArgumentsException {
		try{
			itemCategory = itemCategoryDao.merge(itemCategory);
		}catch(Exception e){
			throw new ItemCategoryNotExistsException("Item category does not exist");
		}
		List<ActionEvent> acEv = actionEventDao.findActiveEvents();
		
		ItemCategoryJson retVal = new ItemCategoryJson();
		
		ItemCategoryInfo itemCategoryInfo = new ItemCategoryInfo(itemCategory.getCode(), itemCategory.getName(), itemCategory.getMaxDiscount());
		
		
		List<ActionInfo> listAi = new ArrayList<ActionInfo>();
		for(ActionEvent ae:acEv){
			ActionInfo ai = new ActionInfo(ae.getId(), ae.getName(), ae.getFrom(), ae.getUntil(), ae.getDiscount());
			listAi.add(ai);
		}
		
		retVal.setInfo(itemCategoryInfo);
		retVal.setActions(listAi);
		
		return retVal;
	}

	@Override
	public List<ItemCategoryJson> transformItems(List<ItemCategory> itemCategories)
			throws ItemCategoryException, BadArgumentsException {
		
		List<ItemCategoryJson> retVal = new ArrayList<>();
		for(ItemCategory ic:itemCategories){
			ItemCategoryJson icj = transformToJson(ic);
			retVal.add(icj);
		}
		
		return retVal;
	}

	
}
