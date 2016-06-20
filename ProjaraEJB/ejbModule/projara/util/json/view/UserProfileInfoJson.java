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
	private UserType userType;
	private Date registeredOn;
	private int points;
	
	public UserProfileInfoJson() {
		username = "";
		password = "";
		address = "";
		firstName = "";
		lastName = "";
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserProfileInfoJson(String username, String password,
			String address, int id, String firstName, String lastName,
			UserType userType, Date registeredOn, int points) {
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
	
	

}
