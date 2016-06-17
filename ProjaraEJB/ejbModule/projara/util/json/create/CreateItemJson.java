package projara.util.json.create;

public class CreateItemJson {

	private String name;
	private double cost;
	private int minQuantity;
	private int itemCategory;
	private int inStock;

	public CreateItemJson() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public CreateItemJson(String name, double cost, int minQuantity,
			int itemCategory, int inStock) {
		super();
		this.name = name;
		this.cost = cost;
		this.minQuantity = minQuantity;
		this.itemCategory = itemCategory;
		this.inStock = inStock;
	}

}
