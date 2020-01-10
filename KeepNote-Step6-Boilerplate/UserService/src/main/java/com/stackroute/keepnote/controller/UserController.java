package com.stackroute.keepnote.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	
	
	@PostMapping("/api/v1/user")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		
		try {
			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
		user.setUserAddedDate(date);	
		User status = userService.registerUser(user);
		return new ResponseEntity<User>(status,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("UserAlreadyExistException",HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping("/api/v1/user/{id}")
	public ResponseEntity<?>updateUser(@RequestBody User user,@PathVariable("id") String userId){
		
		try {
				user.setUserAddedDate(new Date());
				User updatedUser = userService.updateUser(userId, user);
				return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
			}catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
		}
		
	}
	@DeleteMapping("/api/v1/user/{id}")
	public ResponseEntity<?>deleteUser(@PathVariable("id") String userId){
		
		try {
				if(userService.deleteUser(userId)) {
					return new ResponseEntity<User>(HttpStatus.OK);
				}else {
					return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
				}
			}catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/api/v1/user/{id}")
	public ResponseEntity<?>getUserById(@PathVariable("id") String userId){
		try {
			User userfound = userService.getUserById(userId);
				if(userfound!=null) {
					return new ResponseEntity<User>(userfound,HttpStatus.OK);	
				}	else {
					return new ResponseEntity<String>("user not found",HttpStatus.NOT_FOUND);
				}				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
		}
		
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user
	 * 
	 * This handler method should map to the URL "/user" using HTTP POST method
	 */

	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the user updated successfully.
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP PUT method.
	 */

	/*
	 * Define a handler method which will delete a user from a database.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the user deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid userId without {}
	 */

	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the user found successfully. 
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found. 
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP GET method where "id" should be
	 * replaced by a valid userId without {}
	 */
}
