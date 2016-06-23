package projara.util.json.search;

import java.io.Serializable;

public class AdvancedSearch implements Serializable {
	public static final int NO_ID_SEARCH = 0;
	
	private String name;
	private int id;
	private String category;
	private ItemCostSearch costRange;
	
	
	
	public AdvancedSearch() {
		category = "";
		costRange = new ItemCostSearch(0, 0);
	}



	public AdvancedSearch(String name, int id, String category,
			ItemCostSearch costRange) {
		super();
		this.name = name;
		this.id = id;
		this.category = category;
		this.costRange = costRange;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public ItemCostSearch getCostRange() {
		return costRange;
	}



	public void setCostRange(ItemCostSearch costRange) {
		this.costRange = costRange;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	

}
