package projara.rest.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.dao.interfaces.ThresholdDaoLocal;
import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;

@Path("/customerCategory")
@Stateless
@Local(CustomerCategoryRestLocal.class)
public class CustomerCategoryRest implements CustomerCategoryRestLocal{
	
	private static final String MODIFY = "modify";
	private static final String ADD = "add";
	
	@EJB
	private CustomerCategoryDaoLocal customerCategoryDao;
	
	@EJB
	private ThresholdDaoLocal thresholdDao;
	
	@POST
	@Path("/add/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestMsg addCategory(CustomerCategoryJson newCat, 
			@PathParam("type") String type){
		CustomerCategory cat = new CustomerCategory();
		if(type.equals(MODIFY)){
			cat = (CustomerCategory) customerCategoryDao.findById(newCat.getCategoryCode());
			
		}else if(type.equals(ADD)){
			cat.setCategoryCode(newCat.getCategoryCode());
		}
		cat.setName(newCat.getName());
		Set<Threshold> thresholds = new HashSet<>();
		for(ThresholdJson tJson : newCat.getThresholds()){
			Threshold t = customerCategoryDao.fromJsonThreshold(tJson);
			if(thresholdDao.findById(tJson.getId()) != null){
				t = thresholdDao.merge(t);
			}else{
				t = thresholdDao.persist(t);
			}
			thresholds.add(t);
		}
		cat.setThresholds(thresholds);
		if(type.equals(MODIFY)){
			try{
				CustomerCategory modCat = customerCategoryDao.merge(cat);
				return new RestMsg("OK", toJsonCategory(modCat));
			}catch(Exception e){
				return new RestMsg(e.getMessage(), null);
			}
		}else if(type.equals(ADD)){
			try{
				if(customerCategoryDao.findById(cat.getCategoryCode()) != null){
					return new RestMsg("Već postoji kategorija sa ovom oznakom, unesite drugu oznaku.", null);
				}
				CustomerCategory modCat = customerCategoryDao.persist(cat);
				return new RestMsg("OK", toJsonCategory(modCat));
			}catch(Exception e){
				return new RestMsg(e.getMessage(), null);
			}
		}
		
		return new RestMsg("Greska", null);
	}
	
	@GET
	@Path("/delete/{catCode}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCategory(@PathParam("catCode") String categoryCode){
		try{
			List<Integer> tDelete = new ArrayList<Integer>();
			CustomerCategory category = customerCategoryDao.findById(categoryCode);
			for(Threshold t : category.getThresholds()){
				tDelete.add((int) t.getId());
			}
			customerCategoryDao.remove(category);
			customerCategoryDao.flush();
			for(Integer i : tDelete){
				Threshold t = thresholdDao.findById((int)i);
				thresholdDao.remove(t);
			}
			return "OK";
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerCategoryJson> getAllCategories(){
		List<CustomerCategoryJson> retList = new ArrayList<>();
		List<CustomerCategory> categories = customerCategoryDao.findAll();
		for(CustomerCategory c : categories){
			retList.add(toJsonCategory(c));
		}
		return retList;
	}
	
	private CustomerCategoryJson toJsonCategory(CustomerCategory c){
		Set<ThresholdJson> thresholds = new HashSet<ThresholdJson>();
		for(Threshold t : c.getThresholds()){
			thresholds.add(new ThresholdJson((int)t.getId(), t.getFrom(), t.getTo(), t.getPercentage()));
		}
		CustomerCategoryJson newCat = new CustomerCategoryJson(c.getCategoryCode(), c.getName(), thresholds);
		return newCat;
	}
}
