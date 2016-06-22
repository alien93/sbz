package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemCategoryJson implements Serializable {

	private static final long serialVersionUID = 1L;
	private ItemCategoryInfo info;
	private String parentCode;
	private String parentName;
	private List<ItemCategoryJson> subCategories;
	private List<ActionInfo> actions;
	
	public ItemCategoryJson() {
		info = new ItemCategoryInfo();
		actions = new ArrayList<ActionInfo>();
		subCategories = new ArrayList<ItemCategoryJson>();
		parentCode = "";
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
	
	public List<ActionInfo> getActions() {
		return actions;
	}

	public void setActions(List<ActionInfo> actions) {
		this.actions = actions;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	

}
