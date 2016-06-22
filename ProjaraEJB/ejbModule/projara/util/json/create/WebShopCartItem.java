package projara.util.json.create;

import java.io.Serializable;

public class WebShopCartItem implements Serializable {

	private int itemId;
	private int quantity;
	
	public WebShopCartItem() {
		// TODO Auto-generated constructor stub
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
