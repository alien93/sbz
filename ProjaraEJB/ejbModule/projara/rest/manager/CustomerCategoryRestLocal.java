package projara.rest.manager;

import java.util.List;

import projara.util.json.view.UserProfileInfoJson;

public interface CustomerCategoryRestLocal {
	
	public RestMsg addCategory(CustomerCategoryJson newCat, String type);
	public List<CustomerCategoryJson> getAllCategories();
	public String deleteCategory(String categoryCode);
	public List<UserProfileInfoJson> getAllCustomers(); 
	public RestMsg addCustomerToCat(String categoryId, int customerId);
	public UserProfileInfoJson getUser(String username, String password, 
			String firstName, String lastName, boolean save);
}
