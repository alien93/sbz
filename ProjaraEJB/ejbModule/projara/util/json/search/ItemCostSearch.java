package projara.util.json.search;

import java.io.Serializable;

public class ItemCostSearch implements Serializable {
	public static final double MIN_COST = 0;
	public static final double MAX_COST = Double.MAX_VALUE;

	private double minCost;
	private double maxCost;

	public ItemCostSearch() {
		minCost = MIN_COST;
		maxCost = MAX_COST;
	}

	public ItemCostSearch(double minCost, double maxCost) {
		super();
		setMinCost(minCost);
		setMaxCost(maxCost);
	}

	public double getMinCost() {
		return minCost;
	}

	public void setMinCost(double minCost) {
		this.minCost = minCost;
	}

	public double getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(double maxCost) {
		if (maxCost <= MIN_COST)
			this.maxCost = MAX_COST;
		else
			this.maxCost = maxCost;
	}

}
