package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import projara.model.dao.interfaces.CustomerCategoryDaoLocal;
import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.Manager;
import projara.model.users.User;
import projara.model.users.Vendor;
import projara.session.interfaces.UserManagerLocal;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.UserAlreadyExistsException;
import projara.util.exception.UserException;
import projara.util.exception.UserNotExistsException;
import projara.util.interceptors.CheckParametersInterceptor;
import projara.util.json.view.CustomerCategoryBasicInfo;
import projara.util.json.view.UserProfileInfoJson;

@Stateless
@Local(UserManagerLocal.class)
public class UserManagerBean implements UserManagerLocal {

	@EJB
	private UserDaoLocal userDao;

	@EJB
	private CustomerCategoryDaoLocal customerCategoryDao;

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User registerUser(String username, String password, String address,
			String firstName, String lastName) throws UserException,
			BadArgumentsException {

		User u = userDao.findByUsername(username);

		if (u != null)
			throw new UserAlreadyExistsException("User with username: "
					+ username + " already exists");

		u = new Customer(username, firstName, lastName, address, password);

		try {
			u = userDao.persist(u);
		} catch (Exception e) {
			throw new UserException("Something got wrong, try again");
		}

		return u;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User login(String username, String password, String role) throws UserException,
			BadArgumentsException {

		User u = userDao.findByUsernameAndPassword(username, password, role);

		if (u == null)
			throw new UserNotExistsException("Invalid username and password");

		return u;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Customer setCustomerCategory(Customer customer,
			CustomerCategory customerCategory)
			throws CustomerCategoryException, UserException,
			BadArgumentsException {

		try {
			customer = (Customer) userDao.merge(customer);

		} catch (Exception e) {
			throw new UserNotExistsException("User not exists");
		}

		try {
			customerCategory = customerCategoryDao.merge(customerCategory);

		} catch (Exception e) {
			throw new CustomerCategoryException("Customer category not exists");
		}

		if (customer.getCategory() != null
				&& customer.getCategory().equals(customerCategory)) {
			throw new UserException("Already has category: "
					+ customerCategory.getName());
		}

		try {
			customer.setCategory(customerCategory);
			customer = (Customer) userDao.persist(customer);
		} catch (Exception e) {
			throw new UserException("Could not set category, try again later");
		}

		return customer;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public Customer setCustomerCategory(int userId, String customerCategoryCode)
			throws CustomerCategoryException, UserException,
			BadArgumentsException {

		Customer cust = null;
		CustomerCategory custCat = null;

		try {
			cust = (Customer) userDao.findById(userId);
		} catch (Exception e) {
			throw new UserNotExistsException("Customer with id: " + userId
					+ " not exists");
		}
		if (cust == null)
			throw new UserNotExistsException("Customer with id: " + userId
					+ " not exists");

		try {
			custCat = customerCategoryDao.findById(customerCategoryCode);
		} catch (Exception e) {
			throw new CustomerCategoryException(
					"Customer category not exists with code: "
							+ customerCategoryCode);
		}
		if (custCat == null)
			throw new CustomerCategoryException(
					"Customer category not exists with code: "
							+ customerCategoryCode);

		return setCustomerCategory(cust, custCat);
	}

	@Override
	public User updateUser(User user, String firstName, String lastName,
			String address, String password, String username)
			throws UserException, BadArgumentsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User updateUser(UserProfileInfoJson userProfile)
			throws UserException, BadArgumentsException {

		User u = null;
		try {
			u = userDao.findById(userProfile.getId());
		} catch (Exception e) {
			throw new UserNotExistsException("User not exists");
		}
		if (u == null)
			throw new UserNotExistsException("User not exists");

		
		if(userProfile.getUserType().equals("C")){
			Customer c;
			try {
				c = (Customer) u;
			} catch (Exception e) {
				throw new UserNotExistsException("Its not customer");
			}

			return updateCustomer(c, userProfile);
		}else{
			return updatePerson(u, userProfile);
		}
		
	}

	@Interceptors({CheckParametersInterceptor.class})
	private User updatePerson(User u, UserProfileInfoJson userProfile)
			throws UserException, BadArgumentsException {

		try {
			u = userDao.merge(u);
		} catch (Exception e) {
			throw new UserNotExistsException("User not exists");
		}

		String username = userProfile.getUsername().trim();
		String password = userProfile.getPassword().trim();
		String firstName = userProfile.getFirstName().trim();
		String lastName = userProfile.getLastName().trim();

		if (!username.isEmpty() && !u.getUsername().equals(username)) {
			User u1 = userDao.findByUsername(username);
			if (u1 != null)
				throw new UserAlreadyExistsException(
						"Username is already taken");

			u.setUsername(username);
		}

		if (!password.isEmpty() && !u.getPassword().equals(password)) {
			u.setPassword(password);
		}

		if (!firstName.isEmpty() && !u.getFirstName().equals(firstName))
			u.setFirstName(firstName);

		if (!lastName.isEmpty() && !u.getLastName().equals(lastName))
			u.setLastName(lastName);

		try {
			u = userDao.persist(u);
		} catch (Exception e) {
			throw new UserException("Some problem occured, try again");
		}

		return u;
	}

	@Interceptors({CheckParametersInterceptor.class})
	private User updateCustomer(Customer c, UserProfileInfoJson userProfile)
			throws UserException, BadArgumentsException {

		c = (Customer) updatePerson(c, userProfile);

		String address = userProfile.getAddress().trim();

		if (!address.isEmpty()) {
			c.setAddress(address);

			try {
				c = (Customer) userDao.persist(c);
			} catch (Exception e) {
				throw new UserException("Problem occured");
			}
		}

		return c;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public UserProfileInfoJson transformToJson(User u) throws UserException,
			BadArgumentsException {
		try {
			u = userDao.merge(u);
		} catch (Exception e) {
			throw new UserException();
		}

		UserProfileInfoJson userProfile = new UserProfileInfoJson();
		userProfile.setId(u.getId());
		userProfile.setFirstName(u.getFirstName());
		userProfile.setLastName(u.getLastName());
		userProfile.setPassword(u.getPassword());
		userProfile.setUsername(u.getUsername());
		userProfile.setRegisteredOn(u.getRegisteredOn());
		userProfile.setUserType(u.getRole());

		if (u instanceof Customer) {
			Customer c = (Customer) u;
			userProfile.setPoints(c.getPoints());
			userProfile.setAddress(c.getAddress());
			if(c.getCategory()!=null)
				userProfile
						.setCategory(new CustomerCategoryBasicInfo(c.getCategory()
								.getCategoryCode(), c.getCategory().getName()));
			else{
				userProfile
						.setCategory(new CustomerCategoryBasicInfo(null, null));
			}
		}
		
		return userProfile;

	}

}
