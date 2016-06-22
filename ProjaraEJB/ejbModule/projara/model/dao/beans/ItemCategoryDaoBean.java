package projara.model.dao.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
@Stateless
@Local(ItemCategoryDaoLocal.class)
public class ItemCategoryDaoBean extends GenericDaoBean<ItemCategory, String>
		implements
			ItemCategoryDaoLocal {

	@Override
	public List<ItemCategory> filterByName(String name) {
		
		List<ItemCategory> result = null;
		
		if(name == null || name.isEmpty())
			return result;
		
		Query q = em.createNamedQuery("filterByName");
		q.setParameter("name", "%"+name.toUpperCase()+"%");
		
		try{
			result = q.getResultList();
		}catch(Exception e){
			
		}
		
		return result;
	}

	@Override
	public List<ItemCategory> getRoots() {
		Query q = em.createNamedQuery("getRoots");
		
		return q.getResultList();
	}

}
