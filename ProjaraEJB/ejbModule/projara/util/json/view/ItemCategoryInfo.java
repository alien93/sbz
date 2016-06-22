package projara.util.json.view;

import java.io.Serializable;

public class ItemCategoryInfo implements Serializable {

	private String code;
	private String name;
	private double maxDiscount;
	
	public ItemCategoryInfo() {
		// TODO Auto-generated constructor stub
	}

	public ItemCategoryInfo(String code, String name, double maxDiscount) {
		super();
		this.code = code;
		this.name = name;
		this.maxDiscount = maxDiscount;
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

	public double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	
	

}
