package projara.util.json.create;

import java.io.Serializable;

import javax.ws.rs.FormParam;

public class CreateItemForm implements Serializable {

	@FormParam("name")
	private String name;
	@FormParam("category")
	private String category;
	@FormParam("cost")
	private double cost;
	@FormParam("inStock")
	private int inStock;
	@FormParam("minQuantity")
	private int minQuantity;
	@FormParam("id")
	private int id;
	
	@FormParam("image")
	private byte[] image;
	
	@FormParam("format")
	private String format;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public int getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public CreateItemForm(String name, String category, double cost,
			int inStock, int minQuantity, byte[] image) {
		super();
		this.name = name;
		this.category = category;
		this.cost = cost;
		this.inStock = inStock;
		this.minQuantity = minQuantity;
		this.image = image;
	}

	public CreateItemForm() {
		super();
		name = "";
		category = "";
		cost = 0;
		inStock = 0;
		minQuantity = 0;
		image = null;
		format = "";
		id = 0;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
}
