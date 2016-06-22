package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Nina
 *
 */
public class ItemCategoryJson implements Serializable{

	private static final long serialVersionUID = 1L;
	private ItemCategoryInfo info;
	private List<ActionInfo> actions;

	public ItemCategoryJson(){
		info = new ItemCategoryInfo();
		actions = new ArrayList<ActionInfo>();
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
}
