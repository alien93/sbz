package projara.session.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.User;
import projara.session.interfaces.AuthorizationLocal;
import projara.util.exception.UserException;
import projara.util.exception.UserNotLoggedException;
import projara.util.exception.UserRoleException;

@Stateless
@Local(AuthorizationLocal.class)
public class AuthorizationBean implements AuthorizationLocal {

	@EJB
	private UserDaoLocal userDao;
	
	@Override
	public User checkIsLogged(HttpSession session) throws UserException {
		
		int userId = (int) session.getAttribute("userID");
		User u = null;
		try{
			u = userDao.findById(userId);
		}catch(Exception e){
			throw new UserNotLoggedException();
		}
		
		if(u == null)
			throw new UserNotLoggedException();
		
		return u;
	}

	@Override
	public User checkRole(String role, HttpSession session)
			throws UserException {
		
		User u = checkIsLogged(session);
		
		if(role.equals(u.getRole()))
			return u;
		
		throw new UserRoleException();
	}

}
