package projara.rest.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.dao.interfaces.ThresholdDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.Threshold;
import projara.model.users.User;
import projara.session.interfaces.UserManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.UserException;
import projara.util.json.view.CustomerCategoryBasicInfo;
import projara.util.json.view.UserProfileInfoJson;

@Path("/customerCategory")
@Stateless
@Local(CustomerCategoryRestLocal.class)
public class CustomerCategoryRest implements CustomerCategoryRestLocal{
	
	public static final String MODIFY = "modify";
	public static final String ADD = "add";
	
	@EJB
	private CustomerCategoryDaoLocal customerCategoryDao;
	
	@EJB
	private ThresholdDaoLocal thresholdDao;
	
	@EJB
	private UserDaoLocal userDao;
	
	@EJB
	private UserManagerLocal userManager;
	
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
	
	@GET
	@Path("/allCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<UserProfileInfoJson> getAllCustomers() {
		List<UserProfileInfoJson> ret = new ArrayList<>();
		List<User> users = userDao.getCustomers();
		
		for(User u : users){
			Customer c = (Customer)u;
			UserProfileInfoJson cJson = new UserProfileInfoJson(c.getUsername(), c.getPassword(), c.getAddress(), c.getId(),
					c.getFirstName(), c.getLastName(), c.getRole(), c.getRegisteredOn(), c.getPoints());
			if(c.getCategory() != null)
				cJson.setCategory(new CustomerCategoryBasicInfo(
						c.getCategory().getCategoryCode(), c.getCategory().getName()));
			ret.add(cJson);
		}
		return ret;
	}
	
	@GET
	@Path("/setCustomerCategory/{cat}/{cust}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public RestMsg addCustomerToCat(@PathParam("cat") String categoryId, 
			@PathParam("cust") int customerId) {
		try {
			Customer c = (Customer)userDao.findById(customerId);
			if("_".equals(categoryId)){
				c.setCategoryNull();
				userDao.merge(c);
			}else{
				CustomerCategory cat = customerCategoryDao.findById(categoryId);
				c.setCategory(cat);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RestMsg("Greska na serveru", null);
		}
		return new RestMsg("OK", null);
	}

	@GET	
	@Path("/getUser/{username}/{password}/{firstName}/{lastName}/{save}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public UserProfileInfoJson getUser(@PathParam("username") String username, 
									   @PathParam("password") String password,
									   @PathParam("firstName") String firstName, 
									   @PathParam("lastName") String lastName, 
									   @PathParam("save") boolean save) {
		UserProfileInfoJson retUser = null;
		try {
			User user = userDao.findByUsername(username);
			if(save){ 
				user.setFirstName(firstName);
				user.setPassword(password);
				user.setLastName(lastName);
				user = userDao.merge(user);
			}
			retUser = userManager.transformToJson(user);
		} catch (BadArgumentsException | UserException e) {
			e.printStackTrace();
		}
		
		return retUser;
	}
}
