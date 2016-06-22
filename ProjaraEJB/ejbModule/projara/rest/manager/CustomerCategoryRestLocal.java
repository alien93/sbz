package projara.rest.manager;

import java.util.List;

public interface CustomerCategoryRestLocal {
	
	public RestMsg addCategory(CustomerCategoryJson newCat, String type);
	public List<CustomerCategoryJson> getAllCategories();
	public String deleteCategory(String categoryCode);
}
