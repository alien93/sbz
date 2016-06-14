package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.Customer;
import projara.model.users.Manager;
import projara.model.users.User;
import projara.model.users.Vendor;
import projara.session.interfaces.UserManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.UserAlreadyExistsException;
import projara.util.exception.UserException;
import projara.util.interceptors.CheckParametersInterceptor;

@Stateless
@Local(UserManagerLocal.class)
public class UserManagerBean implements UserManagerLocal {

	@EJB
	private UserDaoLocal userDao;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User registerUser(String username, String password, String role,
			String firstName, String lastName) throws UserException,
			BadArgumentsException {

		User u = userDao.findByUsername(username);

		if (u != null)
			throw new UserAlreadyExistsException("User with username: "
					+ username + " already exists");

		if (role.equalsIgnoreCase("C")) {
			u = new Customer(username, firstName, lastName, "", password);
		} else if (role.equalsIgnoreCase("M")) {
			u = new Manager(username, firstName, lastName, password);
		} else if (role.equalsIgnoreCase("V")) {
			u = new Vendor(username, firstName, lastName, password);
		} else {
			throw new UserException("Role is not valid");
		}

		try {
			u = userDao.persist(u);
		} catch (Exception e) {
			throw new UserException("Something got wrong, try again");
		}

		return u;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User login(String username, String password) throws UserException,
			BadArgumentsException {
		
		User u = userDao.findByUsernameAndPassword(username, password);
		
		return u;
	}

}
