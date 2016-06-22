package projara.util.json.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemJson implements Serializable {

	private ItemInfo info;
	private ItemCategoryInfo category;
	private List<ActionInfo> actions;
	
	private double costWithDiscount;
	private double discountPerc;
	
	public ItemJson() {
		info = new ItemInfo();
		category = new ItemCategoryInfo();
		actions = new ArrayList<ActionInfo>();
	}

	public ItemInfo getInfo() {
		return info;
	}

	public void setInfo(ItemInfo info) {
		this.info = info;
	}

	public ItemCategoryInfo getCategory() {
		return category;
	}

	public void setCategory(ItemCategoryInfo category) {
		this.category = category;
	}

	public List<ActionInfo> getActions() {
		return actions;
	}

	public void setActions(List<ActionInfo> actions) {
		this.actions = actions;
	}

	public ItemJson(ItemInfo info, ItemCategoryInfo category,
			List<ActionInfo> actions) {
		super();
		this.info = info;
		this.category = category;
		this.actions = actions;
	}

	public double getCostWithDiscount() {
		return costWithDiscount;
	}

	public void setCostWithDiscount(double costWithDiscount) {
		this.costWithDiscount = costWithDiscount;
	}
	
	
	public double getDiscountPerc() {
		return discountPerc;
	}

	public void setDiscountPerc(double discountPerc) {
		this.discountPerc = discountPerc;
	}

	public void calculateDiscountAndCost(){
		double perc = 0;
		for(ActionInfo ai:actions){
			perc+=ai.getDicount();
		}
		
		if(perc>getCategory().getMaxDiscount())
			perc = getCategory().getMaxDiscount();
		
		discountPerc = perc;
		costWithDiscount = getInfo().getCost() - (getInfo().getCost() * discountPerc)/100.0;
	}

}
