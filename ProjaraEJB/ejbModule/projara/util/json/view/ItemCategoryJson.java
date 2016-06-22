package projara.util.json.view;

import java.io.Serializable;
import java.util.List;

public class ItemCategoryJson implements Serializable {

	private ItemCategoryInfo info;
	private String parentCode;
	private List<ItemCategoryJson> subCategories;
	
	public ItemCategoryJson() {
		// TODO Auto-generated constructor stub
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCategory(String parentCode) {
		this.parentCode = parentCode;
	}

	public List<ItemCategoryJson> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<ItemCategoryJson> subCategories) {
		this.subCategories = subCategories;
	}


	public ItemCategoryInfo getInfo() {
		return info;
	}

	public void setInfo(ItemCategoryInfo info) {
		this.info = info;
	}
	
	
	

}
