package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionJson implements Serializable {

	private ActionInfo info;
	private List<ItemCategoryInfo> categories;
	
	public ActionJson(ActionInfo info, List<ItemCategoryInfo> categories) {
		super();
		this.info = info;
		this.categories = categories;
	}

	public ActionJson() {
		super();
		// TODO Auto-generated constructor stub
		info = new ActionInfo();
		categories = new ArrayList<ItemCategoryInfo>();
	}

	public ActionInfo getInfo() {
		return info;
	}

	public void setInfo(ActionInfo info) {
		this.info = info;
	}

	public List<ItemCategoryInfo> getCategories() {
		return categories;
	}

	public void setCategories(List<ItemCategoryInfo> categories) {
		this.categories = categories;
	}
	
	
}
