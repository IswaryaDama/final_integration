package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private UserRepository userRepo;
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	public User registerUser(User user) throws UserAlreadyExistsException {

		User user1 = userRepo.insert(user);
		if (null != user1) {
			return user1;
		} else {
			throw new UserAlreadyExistsException("User already Exist");
		}
	}

	/*
	 * This method should be used to update a existing user.Call the corresponding
	 * method of Respository interface.
	 */

	public User updateUser(String userId, User user) throws UserNotFoundException {

		User exists = getUserById(userId);;
		if (null!=exists) {
			userRepo.save(user);
			return exists;
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

	public boolean deleteUser(String userId) throws UserNotFoundException {

		User userObj = getUserById(userId);
		if (null != userObj) {
			userRepo.delete(userObj);
			return true;
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */

	public User getUserById(String userId) throws UserNotFoundException {
		try {
			User users = userRepo.findById(userId).get();
			if (null != users) {
				return users;
			} else {
				throw new UserNotFoundException("User not found");
			}
		} catch (Exception e) {

			throw new UserNotFoundException("User not found");
		}

	}
}
