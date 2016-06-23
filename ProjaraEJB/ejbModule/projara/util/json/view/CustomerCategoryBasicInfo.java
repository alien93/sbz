package projara.util.json.view;

import java.io.Serializable;

public class CustomerCategoryBasicInfo implements Serializable {

	private String code;
	private String name;
	
	public CustomerCategoryBasicInfo() {
		// TODO Auto-generated constructor stub
		code = "";
		name = "";
	}

	public CustomerCategoryBasicInfo(String code, String name) {
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
