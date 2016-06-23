package projara.model.dao.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.dao.interfaces.ItemDaoLocal;
import projara.model.items.Item;
import projara.model.items.ItemCategory;
import projara.util.json.search.AdvancedSearch;

@Stateless
@Local(ItemDaoLocal.class)
public class ItemDaoBean extends GenericDaoBean<Item, Integer>
		implements
			ItemDaoLocal {

	@EJB
	private ItemCategoryDaoLocal itemCatDao;

	@Override
	public List<Item> advancedSearch(AdvancedSearch advSearch) {
		List<Item> result = null;

		if (advSearch.getId() > AdvancedSearch.NO_ID_SEARCH) {
			Item i = findById(advSearch.getId());
			if(i == null)
				return null;
			result = new ArrayList<>();
			result.add(i);
			return result;
		}

		String name = "";
		if (advSearch.getName() != null && !advSearch.getName().isEmpty())
			name = advSearch.getName();

		Query q = em.createNamedQuery("advancedSearch");
		q.setParameter("name", "%"+name.toUpperCase()+"%");
		q.setParameter("minCost", advSearch.getCostRange().getMinCost());
		q.setParameter("maxCost", advSearch.getCostRange().getMaxCost());

		try {
			result = q.getResultList();
		} catch (Exception e) {
			System.out.println("ADVANCED SEARCH EXCEPTION");
			return result;
		}
		

		// FILTER BY ITEM CATEGORY CODE
		String catCode = "";
		catCode = advSearch.getCategory()==null?"":advSearch.getCategory().trim();
		
		/*
		if (advSearch.getCategory().getCode() != null
				&& !advSearch.getCategory().getCode().isEmpty())
			catCode = advSearch.getCategory().getCode();

		String catName = "";
		if (advSearch.getCategory().getName() != null
				&& !advSearch.getCategory().getName().isEmpty()) {
			catName = advSearch.getCategory().getName();
		}
		*/
		/*
		if (!catCode.isEmpty()) {

			List<Item> filteredResult = new ArrayList<>();

			ItemCategory ic = null;
			try {
				ic = itemCatDao.findById(catCode);
			} catch (Exception e) {

			}

			if (ic != null) {
				for (Item i : result) {
					i.getCategory().getName();
					i = merge(i);
					if (i.isCategoryOf(ic)) {
						filteredResult.add(i);
					}
				}
			}

			return filteredResult;

		} else if (!catName.isEmpty()) {
		*/
		if(!catCode.isEmpty()){
			List<Item> filteredResult = new ArrayList<>();
			
			List<ItemCategory> itemCategories = itemCatDao.singleFieldSearch(catCode);
			
			if(itemCategories == null || itemCategories.isEmpty())
				return filteredResult;

			for(Item i:result){
				i.getCategory().getName();
				i = merge(i);
				for(ItemCategory ic:itemCategories){
					ic = itemCatDao.merge(ic);
					if(i.isCategoryOf(ic)){
						filteredResult.add(i);
						
						break;
					}
				}
			}
			
			return filteredResult;
		}

		return result;
	}
}
