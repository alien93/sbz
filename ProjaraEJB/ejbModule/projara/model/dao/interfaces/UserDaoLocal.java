package projara.model.dao.interfaces;

import java.util.List;

import projara.model.users.User;
import projara.util.exception.BadArgumentsException;

public interface UserDaoLocal extends GenericDaoLocal<User, Integer> {
	
	public User findByUsername(String username) throws  BadArgumentsException;
	public User findByUsernameAndPassword(String username,String password) throws BadArgumentsException;
	public List<User> singleFieldQuery(String text,String role);
	public List<User> getCustomers();
}
