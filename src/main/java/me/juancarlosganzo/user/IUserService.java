package me.juancarlosganzo.user;

import java.util.List;

import me.juancarlosganzo.entity.UserEntity;

public interface IUserService {
	
	public List<UserEntity> GetUserList();
	
	public UserEntity GetUserByEmail(String email) throws UserException;
	
	public UserEntity GetUserById(int id) throws UserException;

	int Cretate(UserEntity userEntity) throws UserException;

	int Edit(UserEntity userEntity) throws UserException;


}
