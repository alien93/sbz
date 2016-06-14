package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.User;
import projara.util.exception.BadArgumentsException;
import projara.util.exception.UserException;
import projara.util.interceptors.CheckParametersInterceptor;

@Stateless
@Local(UserDaoLocal.class)
public class UserDaoBean extends GenericDaoBean<User, Integer>
		implements
			UserDaoLocal {

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User findByUsername(String username) throws 
			BadArgumentsException {
		
		Query q = em.createNamedQuery("findByUsername");
		q.setParameter("username", username);
		
		User result = null;
		
		try{
			result = (User)q.getSingleResult();
		}catch(Exception e){}
		
		return result;
	}

	@Override
	@Interceptors({CheckParametersInterceptor.class})
	public User findByUsernameAndPassword(String username, String password)
			throws  BadArgumentsException {
		Query q = em.createNamedQuery("findByUsernameAndPassword");
		q.setParameter("username", username);
		q.setParameter("password", password);
		
		User result = null;
		
		try{
			result = (User)q.getSingleResult();
		}catch(Exception e){}
		
		return result;
	}

}
