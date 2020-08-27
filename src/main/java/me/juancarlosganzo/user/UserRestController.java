package me.juancarlosganzo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import me.juancarlosganzo.entity.UserEntity;

@RestController
public class UserRestController {

	@Autowired
	private IUserService userService;

	@GetMapping("/backend/user")
	public List<UserEntity> Listado() {
		return userService.GetUserList();
	}

	@GetMapping("/backend/user/{id}")
	public UserEntity UserById(@PathVariable("id") Integer id) {

		try {
			return userService.GetUserById(id);
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping("/backend/user/email/{email}")
	public UserEntity UserById(@PathVariable("email") String  email) {

		try {
			return userService.GetUserByEmail(email);
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping("/backend/user")
	public int Create(@RequestBody  UserEntity userEntity) {

		try {
			return userService.Cretate(userEntity);
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	@PutMapping("/backend/user")
	public int Edit(@RequestBody  UserEntity userEntity) {

		try {
			return userService.Edit(userEntity);
		} catch (UserException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

}
