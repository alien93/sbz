package projara.model.users;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "M")
public class Manager extends User implements Serializable {

	public Manager() {
		// TODO Auto-generated constructor stub
	}

	public Manager(String username, String firstName, String lastName,String password) {
		super(username, firstName, lastName,password);
		// TODO Auto-generated constructor stub
	}

	
}
