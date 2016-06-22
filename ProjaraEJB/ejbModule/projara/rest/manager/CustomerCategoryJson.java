package projara.rest.manager;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CustomerCategoryJson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4704595974779473457L;
	private String categoryCode;
	private String name;
	private Set<ThresholdJson> thresholds = new HashSet<>();
	
	public CustomerCategoryJson(){}
	
	public CustomerCategoryJson(String categoryCode, String name,
			Set<ThresholdJson> thresholds) {
		super();
		this.categoryCode = categoryCode;
		this.name = name;
		this.thresholds = thresholds;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<ThresholdJson> getThresholds() {
		return thresholds;
	}
	public void setThresholds(Set<ThresholdJson> thresholds) {
		this.thresholds = thresholds;
	}
	
}
