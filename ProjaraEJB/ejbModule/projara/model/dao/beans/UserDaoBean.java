package projara.model.dao.beans;

import javax.ejb.Local;
import javax.ejb.Stateless;

import projara.model.dao.interfaces.UserDaoLocal;
import projara.model.users.User;

@Stateless
@Local(UserDaoLocal.class)
public class UserDaoBean extends GenericDaoBean<User, Integer>
		implements
			UserDaoLocal {


}
