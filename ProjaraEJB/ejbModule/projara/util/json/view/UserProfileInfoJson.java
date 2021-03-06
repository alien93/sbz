package projara.util.json.view;

import java.io.Serializable;
import java.util.Date;

public class UserProfileInfoJson implements Serializable {

	private String username;
	private String password;
	private String address;
	private int id;
	private String firstName;
	private String lastName;
	private String userType;
	private Date registeredOn;
	private int points;
	private CustomerCategoryBasicInfo category;
	
	public UserProfileInfoJson() {
		username = "";
		password = "";
		address = "";
		firstName = "";
		lastName = "";
		userType = "";
		registeredOn = null;
		category = new CustomerCategoryBasicInfo();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public UserProfileInfoJson(String username, String password,
			String address, int id, String firstName, String lastName,
			String userType, Date registeredOn, int points) {
		super();
		this.username = username;
		this.password = password;
		this.address = address;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.registeredOn = registeredOn;
		this.points = points;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CustomerCategoryBasicInfo getCategory() {
		return category;
	}

	public void setCategory(CustomerCategoryBasicInfo category) {
		this.category = category;
	}
	
	

}
