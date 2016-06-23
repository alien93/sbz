package projara.session.interfaces;

import projara.model.users.Customer;
import projara.model.users.CustomerCategory;
import projara.model.users.User;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.CustomerCategoryException;
import projara.util.exception.UserException;
import projara.util.json.view.UserProfileInfoJson;

public interface UserManagerLocal {

	public User registerUser(String username, String password, String address,
			String firstName, String lastName) throws UserException,
			BadArgumentsException;

	public User login(String username, String password) throws UserException,
			BadArgumentsException;

	public Customer setCustomerCategory(Customer customer,
			CustomerCategory customerCategory)
			throws CustomerCategoryException, UserException,
			BadArgumentsException;

	public Customer setCustomerCategory(int userId, String customerCategoryCode)
			throws CustomerCategoryException, UserException,
			BadArgumentsException;

	public User updateUser(User user, String firstName, String lastName,
			String address, String password, String username)
			throws UserException, BadArgumentsException;
	
	public User updateUser(UserProfileInfoJson userProfile) throws UserException,BadArgumentsException;
	
	public UserProfileInfoJson transformToJson(User u) throws UserException,BadArgumentsException;

}
