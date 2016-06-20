package projara.util.json.search;

import java.io.Serializable;

public class ItemCategorySearch implements Serializable {

	private String code;
	private String name;
	
	public ItemCategorySearch() {
		// TODO Auto-generated constructor stub
	}

	public ItemCategorySearch(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
