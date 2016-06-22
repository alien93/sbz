package projara.util.json.create;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WebShopCartJson implements Serializable{

	private int customerId;
	
	private List<WebShopCartItem> items;
	
	public WebShopCartJson() {
		items = new ArrayList<WebShopCartItem>();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<WebShopCartItem> getItems() {
		return items;
	}

	public void setItems(List<WebShopCartItem> items) {
		this.items = items;
	}
	
	

}
