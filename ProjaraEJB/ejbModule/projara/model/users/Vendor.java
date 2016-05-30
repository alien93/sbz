package projara.model.users;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "V")
public class Vendor extends User implements Serializable {

	public Vendor() {
		// TODO Auto-generated constructor stub
	}

	public Vendor(String username, String firstName, String lastName,String password) {
		super(username, firstName, lastName,password);
		// TODO Auto-generated constructor stub
	}
	
	

}
