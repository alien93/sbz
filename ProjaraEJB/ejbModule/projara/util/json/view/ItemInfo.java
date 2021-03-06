package projara.util.json.view;

import java.io.Serializable;
import java.util.Date;

import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;

public class ItemInfo implements Serializable {

	private int id;
	private String name;
	private double cost;
	private String picture;
	private int inStock;
	private boolean needOrdering;
	private Date createdOn;
	private int minQuantity;
	private boolean active;
	
	public ItemInfo() {
		// TODO Auto-generated constructor stub
		id = 0;
		name = "";
		cost = 0;
		picture = "";
		inStock = 0;
		needOrdering = false;
		createdOn = null;
		minQuantity = 0;
		active = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public boolean isNeedOrdering() {
		return needOrdering;
	}

	public void setNeedOrdering(boolean needOrdering) {
		this.needOrdering = needOrdering;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}

	public ItemInfo(int id, String name, double cost, String picture,
			int inStock, boolean needOrdering, Date createdOn, int minQuantity,boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.picture = picture;
		this.inStock = inStock;
		this.needOrdering = needOrdering;
		this.createdOn = createdOn;
		this.minQuantity = minQuantity;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
