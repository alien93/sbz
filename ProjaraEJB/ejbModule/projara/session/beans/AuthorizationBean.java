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

	public static final boolean TURN_OFF = true;
	
	@EJB
	private UserDaoLocal userDao;
	
	@Override
	public User checkIsLogged(HttpSession session) throws UserException {
		
		if(TURN_OFF)
			return null;
		
		Object obj =  session.getAttribute("userID");
		
		if(obj==null)
			throw new UserNotLoggedException();
		
		int userId = (int)obj;
		
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
		
		if(TURN_OFF)
			return null;
		
		User u = checkIsLogged(session);
		
		if(role.equals(u.getRole()))
			return u;
		
		throw new UserRoleException();
	}

}
