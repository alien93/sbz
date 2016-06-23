package projara.rest.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.ItemCategoryDaoLocal;
import projara.model.items.ItemCategory;
import projara.session.interfaces.ItemCategoryManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.ItemCategoryException;
import projara.util.json.view.ItemCategoryJson;

@Path("/itemCategory")
@Stateless
@Local(ItemCategoryRestLocal.class)
public class ItemCategoryRest implements ItemCategoryRestLocal{
	
	@EJB
	private ItemCategoryDaoLocal itemCategoryDao;
	
	@EJB
	private ItemCategoryManagerLocal itemCategoryManager;
	
	@POST
	@Path("/add/{modify}")
	@Override
	public RestMsg addCategory(ItemCategoryJson newCat, @PathParam("modify") String modify) {
		ItemCategory itemCategory = new ItemCategory();
		if(modify.equals(CustomerCategoryRest.MODIFY)){
			itemCategory = itemCategoryDao.findById(newCat.getInfo().getCode());
		}else if(modify.equals(CustomerCategoryRest.ADD)){
			if(itemCategoryDao.findById(newCat.getInfo().getCode()) != null){
				return new RestMsg("Vec postoji kategorija sa ovom oznakom.", null);
			}
			itemCategory.setCode(newCat.getInfo().getCode());
		}
		
		itemCategory.setName(newCat.getInfo().getName());;
		itemCategory.setMaxDiscount(newCat.getInfo().getMaxDiscount());
		if(newCat.getParentCode() != null)
			itemCategory.setParentCategory(itemCategoryDao.findById(newCat.getParentCode()));
		
		if(modify.equals(CustomerCategoryRest.MODIFY)){
			itemCategory = itemCategoryDao.merge(itemCategory);
		}else if(modify.equals(CustomerCategoryRest.ADD)){
			itemCategory = itemCategoryDao.persist(itemCategory);
		}
		itemCategoryDao.flush();
		try {
			return new RestMsg("OK", itemCategoryManager.transformToJson(itemCategory));
		} catch (ItemCategoryException | BadArgumentsException e) {
			e.printStackTrace();
			return new RestMsg("Greska", null);
		}
	}
	
	@Path("/parentCat/{code}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryJson> getParentCategories(@PathParam("code") String catId) {
		List<ItemCategoryJson> retVal = new ArrayList<>();
		for(ItemCategory ic : itemCategoryDao.findAll()){
			if(testCat(catId, ic))
				try {
					retVal.add(itemCategoryManager.transformToJson(ic));
				} catch (ItemCategoryException | BadArgumentsException e) {
					e.printStackTrace();
				}
		}
		return retVal;
	}
	
	private boolean testCat(String catId, ItemCategory cat){
		if(catId.equals(cat.getCode())) return false;
		ItemCategory pom = cat;
		while(pom.getParentCategory() != null){
			if(pom.getParentCategory().getCode().equals(catId)) return false;
			pom = pom.getParentCategory();
		}
		return true;
	}
	
	@Path("/allCat")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<ItemCategoryJson> getAllCategories() {
		List<ItemCategory> categories = itemCategoryDao.findAll();
		List<ItemCategoryJson> categoriesJson = new ArrayList<ItemCategoryJson>();
		for(ItemCategory ic : categories){
			try {
				categoriesJson.add(itemCategoryManager.transformToJson(ic));
			} catch (ItemCategoryException | BadArgumentsException e) {
				e.printStackTrace();
			}
		}
		return categoriesJson;
	}

	@POST
	@Path("/delete")
	@Override
	public RestMsg deleteCategory(String code) {
		try{
			deleteCat(code);
			return new RestMsg("OK", null);
		}catch(Exception e){
			return new RestMsg("Greska", null);
		}
	}
	
	private void deleteCat(String code){
		ItemCategory ic = itemCategoryDao.findById(code);
		List<String> forDelete = new ArrayList<String>();
		for(ItemCategory sub : ic.getSubCategories()){
			forDelete.add(sub.getCode());
		}
		for(String d : forDelete) deleteCat(d);
		itemCategoryDao.remove(ic);
		itemCategoryDao.flush();
	}

}
