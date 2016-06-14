package projara.session.interfaces;

import projara.model.users.User;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.UserException;

public interface UserManagerLocal {

	public User registerUser(String username, String password, String role,
			String firstName, String lastName)
			throws UserException, BadArgumentsException;

	public User login(String username, String password)
			throws UserException, BadArgumentsException;

}
