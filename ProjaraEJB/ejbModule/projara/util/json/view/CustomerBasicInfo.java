package projara.util.json.view;

import java.io.Serializable;

public class CustomerBasicInfo implements Serializable {

	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String address;
	
	private CustomerCategoryBasicInfo customerCategory;
	
	public CustomerBasicInfo() {
		// TODO Auto-generated constructor stub
		id = 0;
		firstName = "";
		username = "";
		address ="";
		lastName = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerBasicInfo(int id, String firstName, String lastName,
			String username, String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.address = address;
	}

	public CustomerCategoryBasicInfo getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(CustomerCategoryBasicInfo customerCategory) {
		this.customerCategory = customerCategory;
	}
	
	

}
