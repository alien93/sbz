package projara.session.interfaces;

import javax.servlet.http.HttpSession;



import projara.model.users.User;
import projara.util.exception.UserException;

public interface AuthorizationLocal {

	public User checkIsLogged(HttpSession session) throws UserException;
	public User checkRole(String role,HttpSession session) throws UserException;
	
}
